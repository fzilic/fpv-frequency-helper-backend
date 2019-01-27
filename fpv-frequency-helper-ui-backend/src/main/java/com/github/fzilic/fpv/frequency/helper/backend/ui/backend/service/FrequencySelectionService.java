package com.github.fzilic.fpv.frequency.helper.backend.ui.backend.service;

import com.github.fzilic.fpv.frequency.helper.backend.ui.backend.data.common.Pilot;
import com.github.fzilic.fpv.frequency.helper.backend.ui.backend.data.common.RecommendationResult;
import java.util.List;

public interface FrequencySelectionService {

  List<RecommendationResult> recommendChannels(List<Pilot> pilots, Integer minimumSeparation);

}
