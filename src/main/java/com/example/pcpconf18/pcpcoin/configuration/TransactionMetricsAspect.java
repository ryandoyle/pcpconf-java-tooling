package com.example.pcpconf18.pcpcoin.configuration;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import io.pcp.parfait.Counter;
import io.pcp.parfait.MonitoredConstant;
import io.pcp.parfait.MonitoredCounter;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import static com.example.pcpconf18.pcpcoin.configuration.MetricsConfiguration.PREFIX;

@Aspect
@Configuration
@ConditionalOnProperty("metrics.enabled")
public class TransactionMetricsAspect {

    private static final double EXCHANGE_FEE = 0.02;
    /* Parfait counters */
    private final Counter total = new MonitoredCounter(PREFIX + "[total].transaction.count", "Transactions through the system");
    private final Counter success = new MonitoredCounter(PREFIX + "[success].transaction.count", "Successful transactions");
    private final Counter failure = new MonitoredCounter(PREFIX + "[failure].transaction.count", "Failed transactions");
    private final MonitoredConstant exchangeFee = new MonitoredConstant<>(PREFIX + ".transaction.fee", "Transaction fee", EXCHANGE_FEE);
    /* Codahale metrics Histogram */
    private final Histogram histogram;

    @Autowired
    public TransactionMetricsAspect(MetricRegistry metricRegistry) {
        histogram = metricRegistry.histogram("transaction.amount");
    }

    @Before("execution(* newTransaction(..))")
    private void monitorAttemptedTransaction() {
        total.inc();
    }

    @AfterReturning("execution(* newTransaction(..))  && args(from,to,amount)")
    private void monitorSuccessfulTransaction(Integer from, Integer to, Long amount) {
        success.inc();
        histogram.update(amount);
    }

    @AfterThrowing(pointcut = "execution(* newTransaction(..))", throwing = "e")
    private void monitorFailedTransaction(Throwable e) throws Throwable {
        failure.inc();
    }

}
