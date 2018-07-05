package com.github.fzilic.fpv.frequency.helper.backend.configuration;

import net.jperf.slf4j.aop.TimingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JPerfConfiguration {

  @Bean
  public TimingAspect timingAspect() {
    return new TimingAspect();
  }
}
