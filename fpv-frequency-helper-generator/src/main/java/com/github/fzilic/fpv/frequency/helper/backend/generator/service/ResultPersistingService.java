package com.github.fzilic.fpv.frequency.helper.backend.generator.service;

import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Channel;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Result;
import java.util.List;
import java.util.Set;

public interface ResultPersistingService {

  boolean existsByFrequencies(List<Channel> frequencies);

  Set<String> findDistinctFrequencies();

  void saveAll(List<Result> results);
}
