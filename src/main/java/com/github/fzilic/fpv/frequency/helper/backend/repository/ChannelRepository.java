package com.github.fzilic.fpv.frequency.helper.backend.repository;

import com.github.fzilic.fpv.frequency.helper.backend.domain.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Integer> {
}
