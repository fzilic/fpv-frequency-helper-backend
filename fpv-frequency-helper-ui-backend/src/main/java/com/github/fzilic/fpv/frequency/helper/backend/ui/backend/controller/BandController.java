package com.github.fzilic.fpv.frequency.helper.backend.ui.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.Views.BandView;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Band;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.repository.BandRepository;
import com.github.fzilic.fpv.frequency.helper.backend.ui.backend.data.api.ApiResponse;
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
