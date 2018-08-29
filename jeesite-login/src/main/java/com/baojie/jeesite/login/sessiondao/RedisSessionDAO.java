package com.baojie.jeesite.login.sessiondao;

import com.baojie.jeesite.util.redis.RedisUtil;
import com.baojie.jeesite.util.spring.SpringContextHolder;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ：冀保杰
 * @date：2018-08-24
 * @desc：
 */
public class RedisSessionDAO extends AbstractSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);

    @Autowired
    private RedisDao redisManager;// = SpringContextHolder.getBean(RedisDao.class);

    @Autowired
    private RedisUtil redisUtil;// = SpringContextHolder.getBean(RedisUtil.class);

    /**
     * The Redis key prefix for the sessions
     */
    private static final String KEY_PREFIX = "shiro_redis_session:";

    @Override
    public void update(Session session) throws UnknownSessionException {
        logger.info("更新session:{}", session.getId());
        this.saveSession(session);
    }

    @Override
    public void delete(Session session) {
        logger.info("删除session:{}", session.getId());
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }
//        redisManager.delete(KEY_PREFIX + session.getId());
        redisUtil.remove(KEY_PREFIX + session.getId());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        logger.info("获取激活的sessionsession:{}");
        Set<Session> sessions = new HashSet<Session>();
        Set<String> keys = redisManager.keys(KEY_PREFIX + "*");
        if(keys != null && keys.size()>0){
            for(String key : keys){
                Session s = redisManager.get(key, Session.class);
                sessions.add(s);
            }
        }
        return sessions;
    }

    @Override
    protected Serializable doCreate(Session session) {

        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        logger.info("创建session:{}", session.getId());
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        SimpleSession session = null;
        if(sessionId == null){
            logger.error("session id is null");
            return null;
        }
        logger.info("获取session:{}", sessionId);
//        Session session = redisUtil.get(KEY_PREFIX + sessionId);
        String result = redisUtil.get(KEY_PREFIX + sessionId);
        if (redisUtil.get(KEY_PREFIX + sessionId) != null){
            byte[] bytes = result.getBytes();
            session = (SimpleSession) SerializerUtil.deserialize(bytes);
        }
        return session;
    }

    private void saveSession(Session session) throws UnknownSessionException{
        logger.info("保存session:{}", session.getId());
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }
        //设置过期时间
        long expireTime = 1800000L;
        session.setTimeout(expireTime);
        byte[] value = SerializerUtil.serialize(session);
        session.setTimeout(expireTime);
//        redisManager.add(KEY_PREFIX + session.getId(), expireTime, session);
        redisUtil.set(KEY_PREFIX + session.getId(), value, expireTime);
    }

    /**
     * 获得byte[]型的key
     * @param key
     * @return
     */
    private byte[] getByteKey(Serializable sessionId){
        String preKey = KEY_PREFIX + sessionId;
        return preKey.getBytes();
    }



}
