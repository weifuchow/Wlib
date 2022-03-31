package com.weifuchow.network.multiclient.udp.tester;

import groovy.util.logging.Log;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * @Desc UdpServer
 * @Author zhouweifu
 * @Date 2022/2/14
 */


public class UdpServer {

    private static Logger log = LoggerFactory.getLogger(UdpServer.class);


    private DatagramSocket socket;
    private byte[] buf = new byte[256];

    public UdpServer() throws SocketException {
        this.socket = new DatagramSocket(9000);
        log.info("udp server init success");
    }

    public void getMessage() throws IOException {
        DatagramPacket packet
                = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        log.info("address = > {} ,data => {}",packet.getAddress() ,new String(packet.getData()));
    }

    public static void main(String[] args) throws Exception {
        UdpServer udpServer = new UdpServer();
        while (true){
            udpServer.getMessage();
        }

    }
}
