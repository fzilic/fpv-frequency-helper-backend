package com.github.fzilic.fpv.frequency.helper.backend.ui.backend.service;

import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Result;
import com.github.fzilic.fpv.frequency.helper.backend.ui.backend.data.common.Pilot;
import java.util.List;

public interface ResultService {

  List<Result> query(Integer minimumSeparation, Integer maxResults, List<Pilot> pilots);

}
