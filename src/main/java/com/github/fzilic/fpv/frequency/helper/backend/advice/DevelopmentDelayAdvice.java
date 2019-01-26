package com.github.fzilic.fpv.frequency.helper.backend.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Aspect
@Profile({"delay"})
@Component
public class DevelopmentDelayAdvice {

  private final Long delayMillis;

  @Autowired
  public DevelopmentDelayAdvice(@Value("${dev.delay:500}") final Long delayMillis) {
    this.delayMillis = delayMillis;
  }

  @Around("execution(* com.github.fzilic.fpv.frequency.helper.backend..*(..))")
  public Object delay(final ProceedingJoinPoint joinPoint) throws Throwable {
    if (delayMillis > 0) {
      Thread.sleep(delayMillis);
    }
    return joinPoint.proceed();
  }

}
