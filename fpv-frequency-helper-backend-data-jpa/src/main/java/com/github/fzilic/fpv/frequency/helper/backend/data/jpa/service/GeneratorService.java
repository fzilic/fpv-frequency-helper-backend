package com.github.fzilic.fpv.frequency.helper.backend.data.jpa.service;

public interface GeneratorService {

  void generate(Integer pilotCount, Integer maxPilotCount, Integer minFrequencySeparation, Integer batchSize);

}
