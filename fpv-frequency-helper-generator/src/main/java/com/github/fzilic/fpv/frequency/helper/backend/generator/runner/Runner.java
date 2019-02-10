package com.github.fzilic.fpv.frequency.helper.backend.generator.runner;

import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.service.GeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Runner implements CommandLineRunner {

  private final GeneratorService generatorService;

  private final Integer pilotCount;

  private final Integer maxPilotCount;

  private final Integer minFrequencySeparation;

  private final Integer batch;

  @Autowired
  public Runner(final GeneratorService generatorService,
      @Value("${app.count:2}") final Integer pilotCount,
      @Value("${app.max-count:8}") final Integer maxPilotCount,
      @Value("${app.min-freq-sep:15}") final Integer minFrequencySeparation,
      @Value("${app.batch:5000}") final Integer batch) {
    this.generatorService = generatorService;
    this.pilotCount = pilotCount;
    this.maxPilotCount = maxPilotCount;
    this.minFrequencySeparation = minFrequencySeparation;
    this.batch = batch;
  }

  @Override
  public void run(final String... args) throws Exception {
    generatorService.generate(pilotCount, maxPilotCount, minFrequencySeparation, batch);
  }


}
