package com.weifuchow.consistent;

import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.IdUtil;
import com.weifuchow.consistent.hash.ConsistentHashRouter;
import com.weifuchow.consistent.hash.HashFunction;
import com.weifuchow.metric.WeifuchowMetric;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @Desc hash
 * @Author zhouweifu
 * @Date 2022/3/24
 */
@Slf4j
public class Tester {

    private ConsistentHashRouter<TestNode> consistentHashRouter = new ConsistentHashRouter(TestNode.buildNos(128), 2048);
    private WeifuchowMetric metric = new WeifuchowMetric(5000);

    @Test
    public void testConsistentHash() {

        List<String> keys = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            keys.add(IdUtil.fastUUID());
        }

        consistentHashRouter.setHashFunction((key) -> HashUtil.dekHash(key));

        for (int i = 0; i < keys.size(); i++) {
            long now = System.currentTimeMillis();
            TestNode node = consistentHashRouter.routeNode(keys.get(i));
            metric.addTimerForName("hash-use-time", System.currentTimeMillis() - now);
            metric.addMeterForName("queue-" + node.getKey(), 1);
        }

        metric.reportMetric();
        Assert.assertTrue(true);
    }





}
