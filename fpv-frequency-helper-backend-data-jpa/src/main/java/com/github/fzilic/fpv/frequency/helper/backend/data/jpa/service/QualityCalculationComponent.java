package com.github.fzilic.fpv.frequency.helper.backend.data.jpa.service;


import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.data.QualityResult;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Channel;
import java.util.List;
import org.springframework.util.concurrent.ListenableFuture;

public interface QualityCalculationComponent {

  ListenableFuture<QualityResult> calculate(List<Channel> channels);

}
