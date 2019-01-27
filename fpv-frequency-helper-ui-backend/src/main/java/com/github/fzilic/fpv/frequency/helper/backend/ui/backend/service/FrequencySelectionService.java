package com.github.fzilic.fpv.frequency.helper.backend.ui.backend.service;

import com.github.fzilic.fpv.frequency.helper.backend.ui.backend.data.common.Pilot;
import java.util.List;

public interface FrequencySelectionService {

  List<List<Pilot>> recommendChannels(Integer lowerSpacing, Integer upperSpacing, List<Pilot> pilots);

  boolean supportsNumberOfCombinations(Long numberOfCombinations);

}
