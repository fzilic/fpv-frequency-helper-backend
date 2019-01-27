package com.github.fzilic.fpv.frequency.helper.backend.generator.service;

import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Channel;
import com.github.fzilic.fpv.frequency.helper.backend.generator.data.QualityResult;
import java.util.List;
import org.springframework.util.concurrent.ListenableFuture;

public interface QualityCalculationComponent {

  ListenableFuture<QualityResult> calculate(List<Channel> channels);

}
