package com.example.pcpconf18.pcpcoin.configuration;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Configuration
@ConditionalOnProperty(name = "metrics.enabled", havingValue = "false")
@ManagedResource("pcpcoin:name=CustomerMetrics")
public class CustomerJMXAspect {

    private final AtomicInteger newCustomers = new AtomicInteger();

    @After("execution(* com.example.pcpconf18.pcpcoin.service.CustomerService.createNewCustomer(String)))")
    public void monitorNewCustomer() {
        newCustomers.incrementAndGet();
    }

    @ManagedAttribute
    public AtomicInteger getNewCustomers() {
        return newCustomers;
    }
}
