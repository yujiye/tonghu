package com.tonghu.app.api.socket;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liangyongjian
 * @desc Socket 服务端
 * @create 2018-01-23 21:30
 **/
public class SocketServer {

    private static final Logger LOGGER = LoggerFactory.getLogger("socketServerLog");

    // 服务端 端口
    private int port;
    // 初始值为false
    private volatile boolean running = false;
    // 与客户端的长连接的超时时间，超过此时间，服务端将会断开socket连接，要求客户端必须要有心跳机制
    private long receiveTimeDelay = 5000;
    // 校验客户端Socket是否有效的线程，每隔此值（毫秒）运行一次
    private long checkSocketIntervalTime = 5000;
    // 维护一个socket对象与socket 属性的Map，用于找到特定的客户端
    private static final ConcurrentHashMap<Socket, SocketAction>
            SOCKET_ACTION_MAPPING = new ConcurrentHashMap<>();

    // 所有的连接都由这个线程把持，如果此线程被关闭，服务端也就关闭了
    private Thread connWatchDog;

    private SocketServer.ConnWatchDog watchDog;

    public SocketServer(int port, long receiveTimeDelay, long checkSocketIntervalTime, boolean running) {
        this.port = port;
        this.receiveTimeDelay = receiveTimeDelay;
        this.checkSocketIntervalTime = checkSocketIntervalTime;
        this.running = running;
    }

    // 启动方法
    public void initSocketServer() {
        this.start();
    }

    public void start() {
        if(running)
            return; // 这是防并发吗？应该不是，这是初始化时调用的，仅仅调用一次
        LOGGER.info("******SocketServer 启动 port={} 超时时间={}......",
                port, receiveTimeDelay);
        running = true;
        watchDog = new SocketServer.ConnWatchDog();
        connWatchDog = new Thread(watchDog);
        connWatchDog.start();
        new Thread(new SocketServer.SocketCheckThread()).start();
    }

    @SuppressWarnings("deprecation")
    public void stopSocketServer(){
        LOGGER.info("******SocketServer 关闭......");
        if(running)
            running = false;
        if(connWatchDog != null) {

            // 调用的是 Thread 类的stop()方法
            try {
                if (watchDog != null && watchDog.ss != null) {
                    LOGGER.info("******关闭 服务端socket 资源");
                    watchDog.ss.close();
                }
            } catch(Exception e) {
                LOGGER.error("******关闭服务端socket资源时出现异常 {}", e);
            }
            LOGGER.info("******关闭 connWatchDog 资源");
            connWatchDog.stop();
        }


    }

    // 内部类
    class ConnWatchDog implements Runnable {
        ServerSocket ss = null;
        public void run() {
            try {
                /**
                 * 这里介绍下第二个参数backlog的含义：
                 * 服务端socket处理客户端socket连接（在服务端accept客户端连接之前）是需要一定时间的。
                 * ServerSocket有一个队列，存放还没有来得及处理的客户端Socket，这个队列的容量就是backlog的含义。
                 * 如果队列已经被客户端socket占满了，如果还有新的连接过来，那么ServerSocket会拒绝新的连接。
                 * 也就是说backlog提供了容量限制功能，避免太多的客户端socket占用太多服务器资源。
                 * 客户端每次创建一个Socket对象，服务端的队列长度就会增加1个。
                 * 服务端每次accept()，就会从队列中取出一个元素。
                 * 所以，这个参数不会影响服务端的长连接数，只是对客户端并发请求连接时的一种保护
                 */
                ss = new ServerSocket(port,5);
                while(running) {
                    // 这里在监听 等待client的连接
                    final Socket socket = ss.accept();
                    SocketServer.SocketAction sa = new SocketServer.SocketAction(socket);
                    LOGGER.info("++++++++++新的socket请求 socket={}", socket.getRemoteSocketAddress());
                    SOCKET_ACTION_MAPPING.put(socket, sa);
                    LOGGER.info("++++++++++服务端持有的客户端连接数量={}", SOCKET_ACTION_MAPPING.size());
                    new Thread(sa).start();
                }
            } catch (IOException e) {
                LOGGER.error("##########服务端监听新的socket请求处，出现异常={}", e);
                e.printStackTrace();
                //调用外部类的方法
                LOGGER.error("##########服务端因异常而中止服务，出现异常={}", e);
                SocketServer.this.stopSocketServer();
            }
        }
    }

    /**
     * 内部类
     * 一个socket占用一个线程，所以这个类中的属性都是socket私有的
     */
    class SocketAction implements Runnable {
        Socket saSocket;
        boolean run = true;
        // 这个时间戳用于记录最后一次访问的时间
        long lastReceiveTime = System.currentTimeMillis();
        // 客户端的标识 没有采用客户端的ip，而是使用了一个客户端发来的字符串作为标识，可能欠妥
        String clientId;
        public SocketAction(final Socket socket) {
            this.saSocket = socket;
        }

        public void run() {
            // 它会一直跑，即便客户端没有请求发过来
            while(running && run) {
                if(System.currentTimeMillis() - lastReceiveTime > receiveTimeDelay) {
                    // 当前时间减去上次连接的时间，如果大于超时时间，则拒绝此次连接
                    LOGGER.info("##########SocketAction发现客户端连接超时，需要断开此链接，socket={}",
                            saSocket.getRemoteSocketAddress());
                    overThis();
                } else {
                    try {
                        InputStream in = saSocket.getInputStream();
                        if(in.available() > 0) {
                            BufferedReader br = new BufferedReader(new InputStreamReader(saSocket.getInputStream()));
//                            clientId = br.readLine();
                            while((clientId = br.readLine()) != null){
                                lastReceiveTime = System.currentTimeMillis();
                                LOGGER.info("=====SocketAction接收到客户端的消息，socket={} message={}",
                                        saSocket.getRemoteSocketAddress(), clientId);
                                PrintWriter out = new PrintWriter(saSocket.getOutputStream());
                                out.println(clientId);
                                out.flush();
                                LOGGER.info("=====SocketAction将接收到的消息原值返回给客户端，socket={} message={}",
                                        saSocket.getRemoteSocketAddress(), clientId);
                            }
//                            LOGGER.info("=====SocketAction接收到客户端的消息，socket={} message={}",
//                                    saSocket.getRemoteSocketAddress(), clientId);
//                            PrintWriter out = new PrintWriter(saSocket.getOutputStream());
//                            out.println(clientId);
//                            out.flush();
//                            LOGGER.info("=====SocketAction将接收到的消息原值返回给客户端，socket={} message={}",
//                                    saSocket.getRemoteSocketAddress(), clientId);
                        } else {
                            // 因为上面的while循环，这个线程会一直跑，即便没有请求发送过来，
                            // 因此这个日志不要打印，否则都是会是这个日志
//                            System.out.println("=====SocketAction发现InputStream目前不可用，等待10毫秒"
//                                    + saSocket.getInetAddress());
                            Thread.sleep(10);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        LOGGER.error("##########SocketAction在读取客户端发来的消息时出现异常，服务端要断开连接，拒绝服务，" +
                                        "socket={} e={}", saSocket.getRemoteSocketAddress(), e);
                        overThis();
                    }
                }
            }
        }

        private void overThis() {
            if(run)
                run = false;
            if(saSocket != null) {
                try {
                    LOGGER.info("##########SocketAction因某种原因需要断开客户端连接，调用Socket.close()方法，" +
                                    "socket={}", saSocket.getRemoteSocketAddress());
                    // 关闭 socket 资源
                    saSocket.close();
                    SOCKET_ACTION_MAPPING.remove(saSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            LOGGER.info("##########SocketAction 已关闭客户端连接，socket={}",
                    saSocket.getRemoteSocketAddress());
        }
    }

    /**
     * 自动检测socket是否超时的线程
     */
    class SocketCheckThread implements Runnable {
        public void run() {
            while(running) {
                try {
                    LOGGER.info("=====SocketCheckThread 开启自动检测客户端Socket是否超时，{}毫秒一次",
                            checkSocketIntervalTime);
                    if (MapUtils.isNotEmpty(SOCKET_ACTION_MAPPING)) {
                        String socketInfo = "";
                        for (Map.Entry<Socket, SocketAction> entry : SOCKET_ACTION_MAPPING.entrySet()) {
                            if(System.currentTimeMillis() - entry.getValue().lastReceiveTime > receiveTimeDelay) {
                                // 当前时间减去上次连接的时间，如果大于超时时间，则断开socket
                                entry.getValue().overThis();
                                LOGGER.info("##########SocketCheckThread 自动检测发现客户端超时，需断开此连接，socket={}",
                                        entry.getKey().getRemoteSocketAddress());
                                SOCKET_ACTION_MAPPING.remove(entry.getKey());
                            } else {
                                socketInfo += entry.getKey().getRemoteSocketAddress() + "; ";
                            }
                        }
                        LOGGER.info("=====SocketCheckThread 经过检测后的依然有效的客户端连接为 {}", socketInfo);
                    }
                    Thread.sleep(checkSocketIntervalTime);
                } catch(Exception e) {
                    LOGGER.error("##########SocketCheckThread 开启自动检测客户端Socket是否超时 出现异常 e={}", e);
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 给外部服务提供的服务 用于推送消息给客户端
     * @param clientId
     * @param message
     * @return
     */
    public boolean sendMessageToClientByToken(String clientId, String message) {
        try {
            if (MapUtils.isNotEmpty(SOCKET_ACTION_MAPPING)) {
                for (Map.Entry<Socket, SocketAction> entry : SOCKET_ACTION_MAPPING.entrySet()) {
                    if (StringUtils.isNotBlank(entry.getValue().clientId)
                            && entry.getValue().clientId.equals(clientId)) {
                        LOGGER.info("@@@@@@@@@@sendMessageToClientByToken 找到了需要推送消息的客户端 " +
                                "clientId={} socket={}，向它发送消息 message={}",
                                clientId, entry.getKey().getRemoteSocketAddress(), message);
                        PrintWriter out = new PrintWriter(entry.getKey().getOutputStream());
                        out.println(message);
                        out.flush();
                        return true;
                    }
                }
            }
        } catch(Exception e) {
            LOGGER.error("##########sendMessageToClientByToken 出现异常 clientId={} " +
                    "message={} e={}", clientId, message, e);
            e.printStackTrace();
        }
        LOGGER.error("@@@@@@@@@@sendMessageToClientByToken 没有找到需要推送消息的客户端 " +
                        "clientId={} message={}", clientId, message);
        return false;
    }

    public static void main(String[] args) {
        int port = 65432;
        SocketServer server = new SocketServer(port, 3000, 5000, false);
        server.start();

        try {
            boolean isSuccess1 = false, isSuccess2 = false;
            while(true) {
                if (!isSuccess1) {
                    String clientToken1 = "1001";
                    String message1 = "你好1001";
                    System.out.println("寻找客户端：" + clientToken1 + "，以期向它发送消息：" + message1);
                    isSuccess1 = server.sendMessageToClientByToken(clientToken1, message1);
                    if (isSuccess1) {
                        System.out.println("找到了客户端：" + clientToken1 + "，并向它发送了消息：" + message1);
                    }
                }

                if (!isSuccess2) {
                    String clientToken2 = "1002";
                    String message2 = "你好1002";
                    System.out.println("寻找客户端：" + clientToken2 + "，以期向它发送消息：" + message2);
                    isSuccess2 = server.sendMessageToClientByToken(clientToken2, message2);
                    if (isSuccess2) {
                        System.out.println("找到了客户端：" + clientToken2 + "，并向它发送了消息：" + message2);
                    }
                }

                if (isSuccess1 && isSuccess2) {
                    break;
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
