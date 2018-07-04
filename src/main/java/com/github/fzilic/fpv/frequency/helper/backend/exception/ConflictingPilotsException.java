package com.github.fzilic.fpv.frequency.helper.backend.exception;

import com.github.fzilic.fpv.frequency.helper.backend.data.common.Pilot;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.tuple.Pair;

@Getter
@ToString
public class ConflictingPilotsException extends RuntimeException {

  private final List<Pair<Pilot, Pilot>> conflictingPilots;

  public ConflictingPilotsException(final List<Pair<Pilot, Pilot>> conflictingPilots) {
    this.conflictingPilots = conflictingPilots;
    }

}
