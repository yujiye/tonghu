package com.tonghu.app.api.socket;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author liangyongjian
 * @desc Socket客户端例子
 * @create 2018-01-24 14:17
 * 参考自：http://coach.iteye.com/blog/2024444 有改动
 * 通信我用的是字符串，示例中用的是对象
 **/
public class SampleClient {

    public static void main(String[] args) throws Exception {
//        String serverIp = "127.0.0.1";
        String serverIp = "123.57.132.183";
        SampleClient client1 = new SampleClient(serverIp, 8990, "1001");
        client1.start();

        Thread.sleep(100);
        SampleClient client2 = new SampleClient(serverIp, 8990, "1002");
        client2.start();
//
//        Thread.sleep(100);
//        SampleClient client3 = new SampleClient(serverIp, 8990, "1003");
//        client1.start();
//
//        Thread.sleep(100);
//        SampleClient client4 = new SampleClient(serverIp, 8990, "1004");
//        client2.start();
//
//        Thread.sleep(100);
//        SampleClient client5 = new SampleClient(serverIp, 8990, "1005");
//        client1.start();
//
//        Thread.sleep(100);
//        SampleClient client6 = new SampleClient(serverIp, 8990, "1006");
//        client2.start();
    }

    private String serverIp;
    private int port;
    private String clientId;
    private Socket socket;
    private boolean running = false;
    private long lastSendTime;

    public SampleClient(String serverIp, int port, String clientId) {
        this.serverIp = serverIp;
        this.port = port;
        this.clientId = clientId;
    }

    public void start() throws UnknownHostException, IOException {
        if(running)
            return;
        socket = new Socket(serverIp, port);
        socket.setSoTimeout(1000);
        System.out.println(clientId + "本地端口：" + socket.getLocalPort());
        lastSendTime = System.currentTimeMillis();
        running = true;
        sendMessage(clientId);
        new Thread(new KeepAliveWatchDog()).start();
        new Thread(new ReceiveWatchDog()).start();
    }

    public void stop(){
        if(running)running=false;
    }


    public void sendMessage(String message) throws IOException {
        if (!isConnected(socket)) {
            System.out.println(clientId + "连接已经断开，重新连接");
            socket = new Socket(serverIp, port);
        }
        System.out.println(clientId + "发送消息：" + message);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println(message);
        out.flush();
    }

    class KeepAliveWatchDog implements Runnable{
        long checkDelay = 10;
        long keepAliveDelay = 3000;
        public void run() {
            while(running){
                if(System.currentTimeMillis() - lastSendTime > keepAliveDelay){
                    try {
                        SampleClient.this.sendMessage(clientId);

                    } catch (IOException e) {
                        e.printStackTrace();
                        SampleClient.this.stop();
                    }
                    lastSendTime = System.currentTimeMillis();
                }else{
                    try {
                        Thread.sleep(checkDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        SampleClient.this.stop();
                    }
                }
            }
        }
    }

    class ReceiveWatchDog implements Runnable{
        public void run() {
            while(running){
                try {
                    InputStream in = socket.getInputStream();
                    if(in.available() > 0){
                        BufferedReader br = new BufferedReader(new InputStreamReader(in));
                        System.out.println(clientId + "接收：" + br.readLine());
                    }else{
                        Thread.sleep(10);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    SampleClient.this.stop();
                }
            }
        }
    }


    /**
     * 检测与服务端的连接是否已经断开
     * @param socket
     * @return
     */
    public boolean isConnected(Socket socket){
        try{
            socket.sendUrgentData(0xFF);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
