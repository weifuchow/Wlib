package com.weifuchow.network.multiclient.udp.tester;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

/**
 *
 * @Desc UdpClient
 * @Author zhouweifu
 * @Date 2022/2/14
 */
public class UdpClient {

    private static Logger log = LoggerFactory.getLogger(UdpServer.class);

    private DatagramSocket socket;


    public UdpClient() throws Exception {
        socket = new DatagramSocket();
    }

    public static void main(String[] args) throws Exception {
        UdpClient client = new UdpClient();
        InetAddress address = InetAddress.getByName("255.255.255.255");
        int i = 0;
        while (true){
            String data = "hello --> " + i ;
            byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length,address,9000);
            client.socket.send(packet);
            log.info("send success => {}",packet);
            Thread.sleep(1000);
        }
    }


}
