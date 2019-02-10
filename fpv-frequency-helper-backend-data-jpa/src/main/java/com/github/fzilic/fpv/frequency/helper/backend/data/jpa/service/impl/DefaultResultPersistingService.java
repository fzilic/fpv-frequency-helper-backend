package com.github.fzilic.fpv.frequency.helper.backend.data.jpa.service.impl;

import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Channel;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Result;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.repository.ChannelRepository;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.repository.ResultRepository;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.service.ResultPersistingService;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultResultPersistingService implements ResultPersistingService {

  private final ChannelRepository channelRepository;

  private final ResultRepository resultRepository;

  @Autowired
  public DefaultResultPersistingService(final ChannelRepository channelRepository, final ResultRepository resultRepository) {
    this.channelRepository = channelRepository;
    this.resultRepository = resultRepository;
  }


  @Override
  public boolean existsByFrequencies(final List<Channel> frequencies) {
    return resultRepository.existsByFrequencies(frequencies.stream()
        .sorted(Comparator.comparing(Channel::getFrequency))
        .map(Channel::getFrequency)
        .map(Objects::toString)
        .collect(Collectors.joining(",")));
  }

  @Override
  @Transactional
  public void saveAll(final List<Result> results) {
    final List<Channel> channels = channelRepository.findAllById(results.stream()
        .map(Result::getChannels)
        .flatMap(Collection::stream)
        .map(Channel::getId)
        .distinct()
        .collect(Collectors.toList()));

    results.forEach(result ->
        result.setChannels(result.getChannels().stream()
            .map(channel ->
                channels.stream()
                    .filter(loaded ->
                        loaded.getId().equals(channel.getId()))
                    .findAny().
                    orElseThrow(IllegalStateException::new))
            .collect(Collectors.toList())));

    resultRepository.saveAll(results);
  }

  @Override
  public Set<String> findDistinctFrequencies() {
    return resultRepository.findDistinctFrequencies();
  }
}
