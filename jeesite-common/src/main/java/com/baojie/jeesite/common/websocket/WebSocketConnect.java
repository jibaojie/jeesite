package com.baojie.jeesite.common.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author ：冀保杰
 * @date：2018-08-23
 * @desc：
 */
@ServerEndpoint(value = "/websocket/{uuid}")
@Component
public class WebSocketConnect {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConnect.class);

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;

    /**
     * concurrent包的线程安全map，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static ConcurrentHashMap<String, WebSocketConnect> webSocketMap = new ConcurrentHashMap<String, WebSocketConnect>();

    /**
     * concurrent包的线程安全set，存放客户端对应的uuid。
     */
    private static CopyOnWriteArraySet<String> uuidSet = new CopyOnWriteArraySet<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    private HttpSession httpSession;

    /**
     * 每个连接对应一个uuid
     */
    private String uuid;

    /**
     * 连接建立成功调用的方法
     * session为连接的客户端session
     *
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("uuid") String uuid, Session session, EndpointConfig config) {
        String sessionId = session.getId();
        this.session = session;
        this.uuid = uuid;
        //加入map中 将userId + sessionId当作key存入, 区分同一用户不同客户端
        webSocketMap.put(uuid + "_" + sessionId, this);
        uuidSet.add(uuid + "_" + sessionId);
        addOnlineCount();
        logger.info("有新连接加入:" + uuid + "_" + sessionId + "！当前连接数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        if (null != this.uuid && getOnlineCount() > 0) {
            logger.info("关闭时的用户信息为:" + this.uuid + "_" + session.getId());
            //从map中删除
            webSocketMap.remove(this.uuid + "_" + session.getId());
            uuidSet.remove(this.uuid + "_" + session.getId());
            //在线数减1
            subOnlineCount();
            logger.info("有一连接关闭！当前连接数为" + getOnlineCount());
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("来自客户端的消息:", message);
        try {
            sendMessage(session, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        WebSocketConnect myWebSocket = null ;
        for(Map.Entry<String , WebSocketConnect> entry : webSocketMap.entrySet()) {
            String sessionId = entry.getKey().split("_")[1];
            if(uuid != null && session.getId().equals(sessionId)) {
                webSocketMap.remove(entry);
            }
        }
        logger.info("发生错误, 移除webSocket");
        error.printStackTrace();
    }


    public synchronized void sendMessage(Session session, String message) throws IOException {
        if (session.isOpen()) {
            session.getBasicRemote().sendText(message);
//            session.getAsyncRemote().sendText(message);
        }
    }

    /**
     * 发送到相应的uuid的websocket
     *
     * @param uuid
     * @param message
     * @return
     */
    public boolean sendMessageToUUID(String uuid, String message) {
        try {
            WebSocketConnect myWebSocket = null;
            for (Map.Entry<String, WebSocketConnect> entry : webSocketMap.entrySet()) {
                String userId = entry.getKey().split("_")[0];
                if (uuid != null && uuid.equals(userId)) {
                    myWebSocket = entry.getValue();
                    myWebSocket.sendMessage(myWebSocket.session, message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断uuid 的长连接是否还存在
     *
     * @param uuid
     * @return
     */
    public boolean isUuidSave(String uuid) {
        if (uuidSet.size() > 0 && uuidSet.contains(uuid + "_" + this.session)) {
            return true;
        }
        return false;
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketConnect.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketConnect.onlineCount--;
    }

    public static ConcurrentHashMap<String, WebSocketConnect> getWebSocketMap() {
        return webSocketMap;
    }

    public static void setOnlineCount(int onlineCount) {
        WebSocketConnect.onlineCount = onlineCount;
    }


}
