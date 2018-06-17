package com.tonghu.app.api.socket;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liangyongjian
 * @desc Socket服务端示例
 * @create 2018-01-24 14:18
 * 参考自：http://coach.iteye.com/blog/2024444 有改动
 * 通信我用的是字符串，示例中用的是对象
 **/
public class SampleServer {

    public static void main(String[] args) {
        int port = 65432;
        SampleServer server = new SampleServer(port);
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

    private int port;
    private volatile boolean running = false;
    private long receiveTimeDelay = 3000;
    private static final ConcurrentHashMap<Socket, SocketAction> SOCKET_ACTION_MAPPING =
            new ConcurrentHashMap<Socket, SocketAction>();

    private Thread connWatchDog;

    public SampleServer(int port) {
        this.port = port;
    }

    public void start() {
        if(running)
            return; // 这是防并发吗？应该不是，这是初始化时调用的，仅仅调用一次
        running = true;
        connWatchDog = new Thread(new ConnWatchDog());
        connWatchDog.start();
        new Thread(new SocketCheckThread()).start();
    }

    @SuppressWarnings("deprecation")
    public void stop(){
        if(running)
            running = false;
        if(connWatchDog != null)
            // 调用的是 Thread 类的stop()方法
            connWatchDog.stop();
    }

    // 内部类
    class ConnWatchDog implements Runnable {
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
                ServerSocket ss = new ServerSocket(port,5);
                while(running) {
                    // 这里在监听 等待client的连接
                    final Socket socket = ss.accept();
                    SocketAction sa = new SocketAction(socket);
                    socket.getPort();
                    System.out.println("=====来了一个新的socket请求，" + socket.getInetAddress() + ":" + socket.getPort());
                    SOCKET_ACTION_MAPPING.put(socket, sa);
                    System.out.println("=====SOCKET_ACTION_MAPPING中的客户端socket数量，"
                            + SOCKET_ACTION_MAPPING.size());
                    new Thread(sa).start();
                }
            } catch (IOException e) {
                System.out.println("=====服务端监听新的socket请求处，出现异常，" + e.getMessage());
                e.printStackTrace();
                //调用外部类的方法
                System.out.println("=====服务端服务因异常停止");
                SampleServer.this.stop();
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
        String clientToken;
        public SocketAction(final Socket socket) {
            this.saSocket = socket;
        }

        public void run() {
            // 它会一直跑，即便客户端没有请求发过来
            while(running && run) {
                if(System.currentTimeMillis() - lastReceiveTime > receiveTimeDelay) {
                    // 当前时间减去上次连接的时间，如果大于超时时间，则拒绝此次连接
                    System.out.println("=====SocketAction发现socket连接超时，服务端要断开，决绝服务，"
                            + saSocket.getInetAddress());
                    overThis();
                } else {
                    try {
                        InputStream in = saSocket.getInputStream();
                        if(in.available() > 0) {
                            lastReceiveTime = System.currentTimeMillis();
                            BufferedReader br = new BufferedReader(new InputStreamReader(saSocket.getInputStream()));
                            clientToken = br.readLine();
                            System.out.println("=====SocketAction接收到客户端 "
                                    + saSocket.getInetAddress() + " 发来的消息：" + clientToken);
                            PrintWriter out = new PrintWriter(saSocket.getOutputStream());
                            out.println(clientToken);
                            out.flush();
                        } else {
                            // 因为上面的while循环，这个线程会一直跑，即便没有请求发送过来，
                            // 因此这个日志不要打印，否则都是会是这个日志
//                            System.out.println("=====SocketAction发现InputStream目前不可用，等待10毫秒"
//                                    + saSocket.getInetAddress());
                            Thread.sleep(10);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("=====SocketAction在读取客户端发来的消息时出现异常，服务端要断开，决绝服务，"
                                + saSocket.getInetAddress() + " " + e.getMessage());
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
                    System.out.println("=====发现socket连接超时，服务端断开连接，"
                            + saSocket.getInetAddress());
                    // 关闭 socket 资源
                    saSocket.close();
                    SOCKET_ACTION_MAPPING.remove(saSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("=====SocketAction关闭：" + saSocket.getRemoteSocketAddress());
        }
    }

    /**
     * 自动检测socket是否超时的线程
     */
    class SocketCheckThread implements Runnable {
        public void run() {
            while(running) {
                try {
                    System.out.println("=====SocketCheckThread 开启自动检测Socket是否超时，3秒一次");
                    if (MapUtils.isNotEmpty(SOCKET_ACTION_MAPPING)) {
                        for (Map.Entry<Socket, SocketAction> entry : SOCKET_ACTION_MAPPING.entrySet()) {
                            if(System.currentTimeMillis() - entry.getValue().lastReceiveTime > receiveTimeDelay) {
                                // 当前时间减去上次连接的时间，如果大于超时时间，则断开socket
                                entry.getValue().overThis();
                                System.out.println("=====SocketCheckThread 自动检测发现客户端超时，要断开此客户端："
                                        + entry.getKey().getRemoteSocketAddress());
                                SOCKET_ACTION_MAPPING.remove(entry.getKey());
                            }
                        }
                    }
                    Thread.sleep(3000);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean sendMessageToClientByToken(String clientToken, String message) {
        try {
            if (MapUtils.isNotEmpty(SOCKET_ACTION_MAPPING)) {
                for (Map.Entry<Socket, SocketAction> entry : SOCKET_ACTION_MAPPING.entrySet()) {
                    if (StringUtils.isNotBlank(entry.getValue().clientToken)
                            && entry.getValue().clientToken.equals(clientToken)) {
                        System.out.println("=====sendMessageToClientByToken 找到了客户端，向他发送消息：" + message);
                        PrintWriter out = new PrintWriter(entry.getKey().getOutputStream());
                        out.println(message);
                        out.flush();
                        return true;
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
