package com.icube.bobChat.Server;

import java.net.*;

public class EchoClient {
    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buf;

    public EchoClient(String ipAddress) throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName(ipAddress);
    }

    public void sendEcho(String msg) {
        msg = transformMessage(msg);
        try {
            buf = new byte[msg.length()];
            buf = msg.getBytes();
            System.out.println("msg.getBytes() = "+msg.getBytes());
            DatagramPacket packet
                    = new DatagramPacket(buf, buf.length, address, 1418);
            socket.send(packet);
            System.out.println("packet.getData() = "+ packet.getData());
        } catch (Exception e){
            e.printStackTrace();
        }
        msg = "";
    }

    public void close() {
        socket.close();
    }
    private String transformMessage(String msg){
        int i = msg.length();
        int i2 = 1;
        if (i<=1000){
            i2 = 4;
        }
        if (i<=100){
            i2 = 3;
        }
        if (i<=10){
            i2 = 2;
        }
        if (i<=1){
            i2 = 1;
        }
        switch (i2){
            case 1:
                msg = msg.length()+"-----"+msg;
                System.out.println("case is 1");
                break;
            case 2:
                msg = msg.length()+"----"+msg;
                System.out.println("case is 2");
                break;
            case 3:
                msg = msg.length()+"---"+msg;
                System.out.println("case is 3");
                break;
            case 4:
                msg = msg.length()+"--"+msg;
                System.out.println("case is 4");
                break;
        }
        System.out.println(msg);
        return msg;
    }
}