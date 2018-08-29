package com.baojie.jeesite.login.shiro.redissession;

import com.baojie.jeesite.util.constants.GlobalConfig;
import com.baojie.jeesite.util.redis.RedisUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.*;

/**
 * @author Administrator
 */
public class RedisSessionDAO extends AbstractSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);

    private static final long DEFAULT_SESSION_IN_MEMORY_TIMEOUT = 1000L;
    /**
     * doReadSession be called about 10 times when login.
     * Save Session in ThreadLocal to resolve this problem. sessionInMemoryTimeout is expiration of Session in ThreadLocal.
     * The default value is 1000 milliseconds (1s).
     * Most of time, you don't need to change it.
     */
    private long sessionInMemoryTimeout = DEFAULT_SESSION_IN_MEMORY_TIMEOUT;

    /**
     * expire time in seconds
     */
    private static final Long DEFAULT_EXPIRE = -2L;
    private static final Long NO_EXPIRE = -1L;

    /**
     * Please make sure expire is longer than sesion.getTimeout()
     */
    private Long expire = DEFAULT_EXPIRE;

    private static final int MILLISECONDS_IN_A_SECOND = 1000;

    @Autowired
    private RedisUtil redisUtil;

    private static ThreadLocal sessionsInThread = new ThreadLocal();

    @Override
    public void update(Session session) throws UnknownSessionException {
        logger.info("更新session：{}", session.getId());
        this.saveSession(session);
    }

    /**
     * save session
     * @param session
     * @throws UnknownSessionException
     */
    private void saveSession(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            throw new UnknownSessionException("session or session id is null");
        }
        if (expire.equals(DEFAULT_EXPIRE) ) {
            redisUtil.setValueJdkSerializer(GlobalConfig.DEFAULT_SESSION_KEY_PREFIX + session.getId(), session,  (session.getTimeout() / MILLISECONDS_IN_A_SECOND));
            return;
        }
        if (!expire.equals(NO_EXPIRE)  && expire * MILLISECONDS_IN_A_SECOND < session.getTimeout()) {
            logger.warn("Redis session expire time: "
                    + (expire * MILLISECONDS_IN_A_SECOND)
                    + " is less than Session timeout: "
                    + session.getTimeout()
                    + " . It may cause some problems.");
        }
        redisUtil.setValueJdkSerializer(GlobalConfig.DEFAULT_SESSION_KEY_PREFIX + session.getId(), session, expire);
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }
        try {
            redisUtil.remove(GlobalConfig.DEFAULT_SESSION_KEY_PREFIX + session.getId());
        } catch (Exception e) {
            logger.error("delete session error. session id=" + session.getId());
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<Session>();
        try {
            Set<String> keys = redisUtil.getValueJdkSerializer(GlobalConfig.DEFAULT_SESSION_KEY_PREFIX + "*");
            if (keys != null && keys.size() > 0) {
                for (String key:keys) {
                    Session s = (Session) redisUtil.getValueJdkSerializer(key);
                    sessions.add(s);
                }
            }
        } catch (Exception e) {
            logger.error("get active sessions error.");
        }
        return sessions;
    }

    @Override
    protected Serializable doCreate(Session session) {
        if (session == null) {
            logger.error("session is null");
            throw new UnknownSessionException("session is null");
        }
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            logger.warn("session id is null");
            return null;
        }
        //从缓存中获取session，防止从redis中获取session频率太过频繁
        Session s = getSessionFromThreadLocal(sessionId);
        if (s != null) {
            return s;
        }
        logger.debug("read session from redis");
        try {
            s = (SimpleSession) redisUtil.getValueJdkSerializer(GlobalConfig.DEFAULT_SESSION_KEY_PREFIX + sessionId);
            setSessionToThreadLocal(sessionId, s);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("read session error. settionId=" + sessionId);
        }
        return s;
    }

    private void setSessionToThreadLocal(Serializable sessionId, Session s) {
        Map<Serializable, SessionInMemory> sessionMap = (Map<Serializable, SessionInMemory>) sessionsInThread.get();
        if (sessionMap == null) {
            sessionMap = new HashMap<Serializable, SessionInMemory>();
            sessionsInThread.set(sessionMap);
        }
        SessionInMemory sessionInMemory = new SessionInMemory();
        sessionInMemory.setCreateTime(new Date());
        sessionInMemory.setSession(s);
        sessionMap.put(sessionId, sessionInMemory);
    }

    private Session getSessionFromThreadLocal(Serializable sessionId) {
        Session s = null;
        if (sessionsInThread.get() == null) {
            return null;
        }
        Map<Serializable, SessionInMemory> sessionMap = (Map<Serializable, SessionInMemory>) sessionsInThread.get();
        SessionInMemory sessionInMemory = sessionMap.get(sessionId);
        if (sessionInMemory == null) {
            return null;
        }
        Date now = new Date();
        long duration = now.getTime() - sessionInMemory.getCreateTime().getTime();
        if (duration < sessionInMemoryTimeout) {
            s = sessionInMemory.getSession();
            logger.debug("read session from memory");
        } else {
            sessionMap.remove(sessionId);
        }
        return s;
    }

    public long getSessionInMemoryTimeout() {
        return sessionInMemoryTimeout;
    }

    public void setSessionInMemoryTimeout(long sessionInMemoryTimeout) {
        this.sessionInMemoryTimeout = sessionInMemoryTimeout;
    }

}
