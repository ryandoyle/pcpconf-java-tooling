package com.example.pcpconf18.pcpcoin.custom;

import io.pcp.parfait.spring.AdvisedAware;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AbstractAspectJAdvice;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;

public class MethodAdviceNotifier implements BeanPostProcessor {
    private AdvisedAware advisedAware;
    private String aspectName;

    public MethodAdviceNotifier(AdvisedAware advisedAware, String aspectName) {
        this.advisedAware = advisedAware;
        this.aspectName = aspectName;
    }

    /* Slightly different version of io.pcp.parfait.spring.AdviceNotifier, matching on AspectJPointcutAdvisor
     * is different in this version of Spring. This needs to be integrated back into Parfait and cleaned up a
     * fair bit
     **/
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Advised) {
            Advised advised = (Advised)bean;
            for (Advisor advisor : advised.getAdvisors()) {
                if (advisor.getAdvice() instanceof AbstractAspectJAdvice) {
                    AbstractAspectJAdvice pointcutAdvisor = (AbstractAspectJAdvice) advisor.getAdvice();
                    if (pointcutAdvisor.getAspectName().equals(aspectName)) {
                        findMethodsForProfiling(beanName, advised, pointcutAdvisor);
                    }
                }
            }
        }
        return bean;
    }

    private void findMethodsForProfiling(String beanName, Advised advised, AbstractAspectJAdvice pointcutAdvisor) {
        for (Method method : advised.getTargetSource().getTargetClass().getMethods()) {
            tryToRegisterForProfiling(beanName, advised, pointcutAdvisor, method);
        }
    }

    private boolean methodPointcutMatchesMethod(Advised advised, AbstractAspectJAdvice pointcutAdvisor, Method method) {
        return pointcutAdvisor.getPointcut().getMethodMatcher().matches(method, advised.getTargetSource().getTargetClass());
    }

    private void tryToRegisterForProfiling(String beanName, Advised advised, AbstractAspectJAdvice pointcutAdvisor, Method method) {
        if(methodPointcutMatchesMethod(advised, pointcutAdvisor, method)){
            registerForProfiling(beanName, advised);
        }
    }

    private void registerForProfiling(String beanName, Advised advised) {
        // advisedAware.addAdvised(pointcutAdvisor., beanName + "." + method.getName());
        try {
            advisedAware.addAdvised(advised.getTargetSource().getTarget(), beanName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
