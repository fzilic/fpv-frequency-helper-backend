package com.github.fzilic.fpv.frequency.helper.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.fzilic.fpv.frequency.helper.backend.Views.ChannelView;
import com.github.fzilic.fpv.frequency.helper.backend.data.api.ApiResponse;
import com.github.fzilic.fpv.frequency.helper.backend.domain.Channel;
import com.github.fzilic.fpv.frequency.helper.backend.repository.ChannelRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/channel")
public class ChannelController {

  private final ChannelRepository repository;

  @Autowired
  public ChannelController(final ChannelRepository repository) {
    this.repository = repository;
  }

  @GetMapping({"", "/"})
  @JsonView({ChannelView.class})
  public ApiResponse<List<Channel>> channels() {
    return ApiResponse.success(repository.findAll());
  }

}
