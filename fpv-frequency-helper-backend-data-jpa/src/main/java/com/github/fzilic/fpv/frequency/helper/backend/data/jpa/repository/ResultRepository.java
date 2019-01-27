package com.github.fzilic.fpv.frequency.helper.backend.data.jpa.repository;

import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Result;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ResultRepository extends JpaRepository<Result, Integer> {

  boolean existsByFrequencies(String frequencies);

  @Query("SELECT DISTINCT r.frequencies FROM Result r")
  Set<String> findDistinctFrequencies();

}
