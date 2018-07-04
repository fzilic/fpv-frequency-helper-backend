package com.github.fzilic.fpv.frequency.helper.backend.controller;

import com.github.fzilic.fpv.frequency.helper.backend.Validations.SelectionRequest;
import com.github.fzilic.fpv.frequency.helper.backend.data.api.ApiResponse;
import com.github.fzilic.fpv.frequency.helper.backend.data.common.Pilot;
import com.github.fzilic.fpv.frequency.helper.backend.data.request.FrequencySelectionRequest;
import com.github.fzilic.fpv.frequency.helper.backend.service.FrequencySelectionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/frequency")
public class FrequencySelectionController {

  private final FrequencySelectionService service;

  @Autowired
  public FrequencySelectionController(final FrequencySelectionService service) {
    this.service = service;
  }

  @PostMapping({"", "/"})
  public ApiResponse<List<List<Pilot>>> recommendFrequencies(@Validated({SelectionRequest.class}) @RequestBody final FrequencySelectionRequest request) {
    return ApiResponse.success(service.recommendChannels(request.getLowerSpacing(), request.getUpperSpacing(), request.getPilots()));
  }
}
