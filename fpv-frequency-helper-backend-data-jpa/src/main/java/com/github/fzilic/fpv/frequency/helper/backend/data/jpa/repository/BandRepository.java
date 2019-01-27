package com.github.fzilic.fpv.frequency.helper.backend.data.jpa.repository;

import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Band;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BandRepository extends JpaRepository<Band, Integer> {
}
