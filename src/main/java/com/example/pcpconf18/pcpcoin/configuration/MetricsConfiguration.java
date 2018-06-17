package com.example.pcpconf18.pcpcoin.configuration;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import io.pcp.parfait.DynamicMonitoringView;
import io.pcp.parfait.MonitorableRegistry;
import io.pcp.parfait.dropwizard.DefaultMetricDescriptorLookup;
import io.pcp.parfait.dropwizard.MetricAdapterFactoryImpl;
import io.pcp.parfait.dropwizard.ParfaitReporter;
import io.pcp.parfait.dxm.IdentifierSourceSet;
import io.pcp.parfait.dxm.MmvVersion;
import io.pcp.parfait.dxm.PcpMmvWriter;
import io.pcp.parfait.pcp.PcpMonitorBridge;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@ConditionalOnProperty("metrics.enabled")
public class MetricsConfiguration implements ApplicationListener<ApplicationReadyEvent> {

    public static final String PREFIX = "pcpcoin";


    /* DynamicMonitoringView controls starting and stopping the PcpMmvWriter so when new metrics are added, they can be seen */
    @Bean
    public DynamicMonitoringView dynamicMonitoringView() {
        return new DynamicMonitoringView(new PcpMonitorBridge(new PcpMmvWriter(PREFIX, IdentifierSourceSet.DEFAULT_SET, MmvVersion.MMV_VERSION2)));
    }

    /* ParfaitReporter is for integration with dropwizard-metrics and controls funneling their counters/histograms/gauges etc...
    * into PCP land */
    @Bean
    public ParfaitReporter parfaitReporter() {
        return new ParfaitReporter(
                metricRegistry(),
                MonitorableRegistry.DEFAULT_REGISTRY,
                dynamicMonitoringView(),
                new MetricAdapterFactoryImpl(
                        new DefaultMetricDescriptorLookup()
                ),
                TimeUnit.MILLISECONDS, TimeUnit.MILLISECONDS, MetricFilter.ALL, PREFIX);

    }

    @Bean
    public MetricRegistry metricRegistry() {
        return new MetricRegistry();
    }

    /* We are using the ParfaitReporter which starts the DynamicMonitoringView. If we were not using dropwizard-metrics,
     * integration, we would start this ourselves */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        //dynamicMonitoringView().start();
        parfaitReporter().start(1, TimeUnit.SECONDS);

    }
}
