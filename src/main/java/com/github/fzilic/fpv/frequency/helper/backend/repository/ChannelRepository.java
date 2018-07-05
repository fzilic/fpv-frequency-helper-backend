package com.github.fzilic.fpv.frequency.helper.backend.repository;

import com.github.fzilic.fpv.frequency.helper.backend.domain.Channel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Integer> {

  List<Channel> findAllByBandNameIn(List<String> bands);

}
