package com.github.fzilic.fpv.frequency.helper.backend.repository;

import com.github.fzilic.fpv.frequency.helper.backend.domain.Band;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BandRepository extends JpaRepository<Band, Integer> {
}
