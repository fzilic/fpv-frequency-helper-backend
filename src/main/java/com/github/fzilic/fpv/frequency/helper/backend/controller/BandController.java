package com.github.fzilic.fpv.frequency.helper.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.fzilic.fpv.frequency.helper.backend.Views.BandView;
import com.github.fzilic.fpv.frequency.helper.backend.data.api.ApiResponse;
import com.github.fzilic.fpv.frequency.helper.backend.domain.Band;
import com.github.fzilic.fpv.frequency.helper.backend.repository.BandRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/band")
public class BandController {

  private final BandRepository repository;

  @Autowired
  public BandController(final BandRepository repository) {
    this.repository = repository;
  }

  @GetMapping({"", "/"})
  @JsonView({BandView.class})
  public ApiResponse<List<Band>> bands() {
    return ApiResponse.success(repository.findAll());
  }

}
