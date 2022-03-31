package com.weifuchow.metric;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.codahale.metrics.Slf4jReporter;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 *
 * @Desc MetricUtils
 * @Author zhouweifu
 * @Date 2022/3/24
 */
@Slf4j
public class WeifuchowMetric {

    public MetricRegistry metricRegistry;
    public ScheduledReporter reporter;

    public WeifuchowMetric(int periodMs){
        this.metricRegistry = new MetricRegistry();
        this.reporter = Slf4jReporter.forRegistry(metricRegistry)
                .outputTo(log)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(periodMs,TimeUnit.MILLISECONDS);
    }


    public void addTimerForName(String name, Duration timeout){
        metricRegistry.timer(name).update(timeout);
    }

    public void addTimerForName(String name, Long timeoutMs){
        metricRegistry.timer(name).update(timeoutMs, TimeUnit.MILLISECONDS);
    }

    public void addHistogramsForName(String name,long value){
        metricRegistry.histogram(name).update(value);
    }

    public void addHistogramsForName(String name,int value){
        metricRegistry.histogram(name).update(value);
    }


    public void addCounterForName(String name,int size){
        metricRegistry.counter(name).inc(size);
    }


    public void addMeterForName(String name,long meter){
        metricRegistry.meter(name).mark(meter);
    }

    public void addGaugeForName(String name, Supplier<Integer> supplier){
        metricRegistry.register(name, (Gauge<Integer>) () -> supplier.get());
    }


    public void reportMetric(){
        reporter.report();
    }



}
