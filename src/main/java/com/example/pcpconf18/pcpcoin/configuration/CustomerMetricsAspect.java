package com.example.pcpconf18.pcpcoin.configuration;

import io.pcp.parfait.Counter;
import io.pcp.parfait.MonitoredCounter;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import static com.example.pcpconf18.pcpcoin.configuration.MetricsConfiguration.PREFIX;

@Aspect
@Configuration
@ConditionalOnProperty("metrics.enabled")
public class CustomerMetricsAspect {

    private final Counter count = new MonitoredCounter(PREFIX + ".customer.new", "New customer signups");

    @After("execution(* createNewCustomer(String)))")
    public void monitorNewCustomer() {
        count.inc();
    }

}
