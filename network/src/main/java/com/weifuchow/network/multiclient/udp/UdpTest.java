package com.weifuchow.network.multiclient.udp;

/**
 *
 * @Desc UdpTest 构建基础测试
 * @Author zhouweifu
 * @Date 2022/2/14
 */
public interface UdpTest {

    public void initServer();

    public void initClient();

    public void run();

}
