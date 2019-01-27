package com.github.fzilic.fpv.frequency.helper.backend.data.jpa.repository;

import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Channel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Integer> {

  List<Channel> findAllByBandNameIn(List<String> bands);

  List<Channel> findAllByBand_PreselectedIsTrue();

}
