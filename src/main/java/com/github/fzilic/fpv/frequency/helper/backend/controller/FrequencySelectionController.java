package com.github.fzilic.fpv.frequency.helper.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.fzilic.fpv.frequency.helper.backend.Validations.SelectionRequest;
import com.github.fzilic.fpv.frequency.helper.backend.Views.ChannelView;
import com.github.fzilic.fpv.frequency.helper.backend.data.api.ApiResponse;
import com.github.fzilic.fpv.frequency.helper.backend.data.api.ApiResponseStatus;
import com.github.fzilic.fpv.frequency.helper.backend.data.common.Pilot;
import com.github.fzilic.fpv.frequency.helper.backend.data.request.FrequencySelectionRequest;
import com.github.fzilic.fpv.frequency.helper.backend.repository.ChannelRepository;
import com.github.fzilic.fpv.frequency.helper.backend.service.FrequencySelectionService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/frequency")
public class FrequencySelectionController {

  private final ChannelRepository channelRepository;

  private final List<FrequencySelectionService> services;

  @Autowired
  public FrequencySelectionController(final ChannelRepository channelRepository, final List<FrequencySelectionService> services) {
    this.channelRepository = channelRepository;
    this.services = services;
  }

  @PostMapping({"", "/"})
  @JsonView(ChannelView.class)
  public ApiResponse<List<List<Pilot>>> recommendFrequencies(@Validated({SelectionRequest.class}) @RequestBody final FrequencySelectionRequest request) {
    final long combinations = request.getPilots().stream()
        .mapToLong(pilot ->
            pilot.getAvailableChannels().size())
        .reduce(1, (left, right) -> left * right);

    request.getPilots()
        .forEach(pilot ->
            pilot.setAvailableChannels(pilot.getAvailableChannels().stream()
                .map(channel ->
                    channelRepository.getOne(channel.getId()))
                .collect(Collectors.toList())));

    log.info("Calculating frequencies for {} pilots with channels {} total combinations {}",
        request.getPilots().size(),
        request.getPilots().stream()
            .map(pilot ->
                pilot.getAvailableChannels().size())
            .collect(Collectors.toList()),
        combinations);


    return services.stream()
        .filter(service ->
            service.supportsNumberOfCombinations(combinations))
        .findAny()
        .map(service ->
            ApiResponse.success(service.recommendChannels(request.getLowerSpacing(), request.getUpperSpacing(), request.getPilots())))
        .orElse(ApiResponse.error(ApiResponseStatus.TOO_MANY_COMBINATIONS, "Unable to locate computational service that supports " + combinations + " combinations"));
  }
}
