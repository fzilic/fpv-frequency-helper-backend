package com.github.fzilic.fpv.frequency.helper.backend.data.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain")
@EnableJpaRepositories("com.github.fzilic.fpv.frequency.helper.backend.data.jpa.repository")
public class DataConfiguration {
}
