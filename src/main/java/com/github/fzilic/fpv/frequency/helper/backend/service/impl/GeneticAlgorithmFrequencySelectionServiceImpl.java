package com.github.fzilic.fpv.frequency.helper.backend.service.impl;

import com.github.fzilic.fpv.frequency.helper.backend.data.common.Pilot;
import com.github.fzilic.fpv.frequency.helper.backend.service.FrequencySelectionService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GeneticAlgorithmFrequencySelectionServiceImpl implements FrequencySelectionService {

  @Override
  public List<List<Pilot>> recommendChannels(final Integer lowerSpacing, final Integer upperSpacing, final List<Pilot> pilots) {
    throw new NotImplementedException("GeneticAlgorithmFrequencySelectionServiceImpl not yet implemented, reduce number of combinations to less then " + Math.round(Math.pow(32, 4)));
  }

  @Override
  public boolean supportsNumberOfCombinations(final Long numberOfCombinations) {
    return numberOfCombinations != null && numberOfCombinations > Math.round(Math.pow(32, 4));
  }

}
