package agh.edu.pl.healthmonitoringsystem.domain.component;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RetryAspect {

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void serviceMethods() {}

    @Around("serviceMethods()")
    @Retryable(value = SQLGrammarException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000))
    public Object retryable(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }
}
