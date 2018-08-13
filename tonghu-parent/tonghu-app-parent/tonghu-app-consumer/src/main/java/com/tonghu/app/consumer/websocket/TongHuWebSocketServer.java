package com.tonghu.app.consumer.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author liangyongjian
 * @desc WebSocket
 * @create 2018-08-09 19:43
 **/
@ServerEndpoint("/webSocketServer/{clientName}")
public class TongHuWebSocketServer {


    private static final Logger LOGGER = LoggerFactory.getLogger("webSocketLog");

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    private static CopyOnWriteArraySet<TongHuWebSocketServer> webSocketClientSet =
            new CopyOnWriteArraySet<TongHuWebSocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private String clientName;

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam("clientName") String clientName, Session session){
        this.session = session;
        this.clientName = clientName;
        webSocketClientSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        LOGGER.info("有新连接加入！当前在线人数 {}", getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        webSocketClientSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        LOGGER.info("{} 连接关闭！当前在线人数为 {}", this.clientName, getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        LOGGER.info("来自客户端 {} 的消息 {}", this.clientName, message);
        //群发消息
        broadCastMessage(message);
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        LOGGER.error("{} 发生错误", this.clientName);
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        LOGGER.info("发送到客户端 {} 的消息 {}", this.clientName, message);
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 将数据传回指定客户端
     * @param message
     */
    public static void sendClientMessage(String clientName, String message) {
        for(TongHuWebSocketServer webSocket : webSocketClientSet) {
            try {
                if (webSocket.clientName.equals(clientName)) {}
                webSocket.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 将数据传回客户端
     * @param message
     */
    public static void broadCastMessage(String message) {
        for(TongHuWebSocketServer webSocket : webSocketClientSet) {
            try {
                webSocket.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        TongHuWebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        TongHuWebSocketServer.onlineCount--;
    }

}
