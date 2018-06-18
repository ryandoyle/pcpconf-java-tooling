package com.example.pcpconf18.pcpcoin.configuration;

import com.example.pcpconf18.pcpcoin.custom.MethodAdviceNotifier;
import io.pcp.parfait.MonitorableRegistry;
import io.pcp.parfait.spring.MonitoringAspect;
import io.pcp.parfait.timing.EventTimer;
import io.pcp.parfait.timing.ThreadMetricSuite;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
@ConditionalOnProperty("metrics.enabled")
public class TimedConfiguration {

    @Bean
    public EventTimer eventTimer() {
        return new EventTimer("pcpcoin.profiled", MonitorableRegistry.DEFAULT_REGISTRY, ThreadMetricSuite.withDefaultMetrics(), true, true);
    }

    @Bean
    public MethodAdviceNotifier adviceNotifier() {
        return new MethodAdviceNotifier(monitoringAspect(), "timedConfiguration");
    }

    @Bean
    public MonitoringAspect monitoringAspect() {
        return new MonitoringAspect(eventTimer());
    }

    @Around("@annotation(io.pcp.parfait.spring.Profiled)")
    public Object profileMethod(ProceedingJoinPoint pjp) throws Throwable {
        return monitoringAspect().profileMethod(pjp);
    }

}
