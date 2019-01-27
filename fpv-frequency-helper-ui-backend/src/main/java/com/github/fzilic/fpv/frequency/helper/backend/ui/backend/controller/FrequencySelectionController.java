package com.github.fzilic.fpv.frequency.helper.backend.ui.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.Validations.SelectionRequest;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.Views.ChannelView;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.repository.ChannelRepository;
import com.github.fzilic.fpv.frequency.helper.backend.ui.backend.data.api.ApiResponse;
import com.github.fzilic.fpv.frequency.helper.backend.ui.backend.data.common.RecommendationResult;
import com.github.fzilic.fpv.frequency.helper.backend.ui.backend.data.request.FrequencySelectionRequest;
import com.github.fzilic.fpv.frequency.helper.backend.ui.backend.service.FrequencySelectionService;
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

  private final FrequencySelectionService service;

  @Autowired
  public FrequencySelectionController(final ChannelRepository channelRepository, final FrequencySelectionService service) {
    this.channelRepository = channelRepository;
    this.service = service;
  }

  @PostMapping({"", "/"})
  @JsonView(ChannelView.class)
  public ApiResponse<List<RecommendationResult>> recommendFrequencies(@Validated({SelectionRequest.class}) @RequestBody final FrequencySelectionRequest request) {
    request.getPilots()
        .forEach(pilot ->
            pilot.setAvailableChannels(pilot.getAvailableChannels().stream()
                .map(channel ->
                    channelRepository.getOne(channel.getId()))
                .collect(Collectors.toList())));

    return ApiResponse.success(service.recommendChannels(request.getPilots(), request.getMinimumSeparation()));
  }
}
