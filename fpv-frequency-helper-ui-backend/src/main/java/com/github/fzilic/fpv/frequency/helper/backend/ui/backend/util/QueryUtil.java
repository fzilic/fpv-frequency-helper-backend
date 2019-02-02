package com.github.fzilic.fpv.frequency.helper.backend.ui.backend.util;

public abstract class QueryUtil {

  private static String QUERY_2 = "SELECT r " +
      "FROM Result r " +
      "WHERE r.numberOfChannels = :numberOfChannels " +
      "AND r.minimumSeparationChannel >= :minimumSeparationChannel " +
      "AND r.minimumSeparationImd >= :minimumSeparationImd " +
      "AND EXISTS (" +
      "  SELECT c0.id " +
      "  FROM Result r0 " +
      "  INNER JOIN r0.channels c0 " +
      "  WHERE c0.id IN :p0 " +
      "  AND r0.numberOfChannels = :numberOfChannels " +
      "  AND r0.id = r.id " +
      "  AND EXISTS ( " +
      "    SELECT c1.id " +
      "    FROM Result r1 " +
      "    INNER JOIN r1.channels c1 " +
      "    WHERE c1.id IN :p1 " +
      "    AND r1.numberOfChannels = :numberOfChannels " +
      "    AND c1.id NOT IN ( c0.id ) " +
      "    AND r1.id = r.id " +
      "  ) " +
      ") " +
      "ORDER BY r.minimumSeparationImd DESC," +
      "         r.minimumSeparationChannel DESC";

  private static String QUERY_3 = "SELECT r " +
      "FROM Result r " +
      "WHERE r.numberOfChannels = :numberOfChannels " +
      "AND r.minimumSeparationChannel >= :minimumSeparationChannel " +
      "AND r.minimumSeparationImd >= :minimumSeparationImd " +
      "AND EXISTS (" +
      "  SELECT c0.id " +
      "  FROM Result r0 " +
      "  INNER JOIN r0.channels c0 " +
      "  WHERE c0.id IN :p0 " +
      "  AND r0.numberOfChannels = :numberOfChannels " +
      "  AND r0.id = r.id " +
      "  AND EXISTS ( " +
      "    SELECT c1.id " +
      "    FROM Result r1 " +
      "    INNER JOIN r1.channels c1 " +
      "    WHERE c1.id IN :p1 " +
      "    AND r1.numberOfChannels = :numberOfChannels " +
      "    AND c1.id NOT IN ( c0.id ) " +
      "    AND r1.id = r.id " +
      "    AND EXISTS ( " +
      "      SELECT c2.id " +
      "      FROM Result r2 " +
      "      INNER JOIN r2.channels c2 " +
      "      WHERE c2.id IN :p2 " +
      "      AND r2.numberOfChannels = :numberOfChannels " +
      "      AND c2.id NOT IN ( c0.id ) " +
      "      AND c2.id NOT IN ( c1.id ) " +
      "      AND r2.id = r.id " +
      "    ) " +
      "  ) " +
      ") " +
      "ORDER BY r.minimumSeparationImd DESC," +
      "         r.minimumSeparationChannel DESC";

  private static String QUERY_4 = "SELECT r " +
      "FROM Result r " +
      "WHERE r.numberOfChannels = :numberOfChannels " +
      "AND r.minimumSeparationChannel >= :minimumSeparationChannel " +
      "AND r.minimumSeparationImd >= :minimumSeparationImd " +
      "AND EXISTS (" +
      "  SELECT c0.id " +
      "  FROM Result r0 " +
      "  INNER JOIN r0.channels c0 " +
      "  WHERE c0.id IN :p0 " +
      "  AND r0.numberOfChannels = :numberOfChannels " +
      "  AND r0.id = r.id " +
      "  AND EXISTS ( " +
      "    SELECT c1.id " +
      "    FROM Result r1 " +
      "    INNER JOIN r1.channels c1 " +
      "    WHERE c1.id IN :p1 " +
      "    AND r1.numberOfChannels = :numberOfChannels " +
      "    AND c1.id NOT IN ( c0.id ) " +
      "    AND r1.id = r.id " +
      "    AND EXISTS ( " +
      "      SELECT c2.id " +
      "      FROM Result r2 " +
      "      INNER JOIN r2.channels c2 " +
      "      WHERE c2.id IN :p2 " +
      "      AND r2.numberOfChannels = :numberOfChannels " +
      "      AND c2.id NOT IN ( c0.id ) " +
      "      AND c2.id NOT IN ( c1.id ) " +
      "      AND r2.id = r.id " +
      "      AND EXISTS ( " +
      "        SELECT c3.id " +
      "        FROM Result r3 " +
      "        INNER JOIN r3.channels c3 " +
      "        WHERE c3.id IN :p3 " +
      "        AND r3.numberOfChannels = :numberOfChannels " +
      "        AND c3.id NOT IN ( c0.id ) " +
      "        AND c3.id NOT IN ( c1.id ) " +
      "        AND c3.id NOT IN ( c2.id ) " +
      "        AND r3.id = r.id " +
      "      ) " +
      "    ) " +
      "  ) " +
      ") " +
      "ORDER BY r.minimumSeparationImd DESC," +
      "         r.minimumSeparationChannel DESC";

  private static String QUERY_5 = "SELECT r " +
      "FROM Result r " +
      "WHERE r.numberOfChannels = :numberOfChannels " +
      "AND r.minimumSeparationChannel >= :minimumSeparationChannel " +
      "AND r.minimumSeparationImd >= :minimumSeparationImd " +
      "AND EXISTS (" +
      "  SELECT c0.id " +
      "  FROM Result r0 " +
      "  INNER JOIN r0.channels c0 " +
      "  WHERE c0.id IN :p0 " +
      "  AND r0.numberOfChannels = :numberOfChannels " +
      "  AND r0.id = r.id " +
      "  AND EXISTS ( " +
      "    SELECT c1.id " +
      "    FROM Result r1 " +
      "    INNER JOIN r1.channels c1 " +
      "    WHERE c1.id IN :p1 " +
      "    AND c1.id NOT IN ( c0.id ) " +
      "    AND r1.numberOfChannels = :numberOfChannels " +
      "    AND r1.id = r.id " +
      "    AND EXISTS ( " +
      "      SELECT c2.id " +
      "      FROM Result r2 " +
      "      INNER JOIN r2.channels c2 " +
      "      WHERE c2.id IN :p2 " +
      "      AND c2.id NOT IN ( c0.id ) " +
      "      AND c2.id NOT IN ( c1.id ) " +
      "      AND r2.numberOfChannels = :numberOfChannels " +
      "      AND r2.id = r.id " +
      "      AND EXISTS ( " +
      "        SELECT c3.id " +
      "        FROM Result r3 " +
      "        INNER JOIN r3.channels c3 " +
      "        WHERE c3.id IN :p3 " +
      "        AND c3.id NOT IN ( c0.id ) " +
      "        AND c3.id NOT IN ( c1.id ) " +
      "        AND c3.id NOT IN ( c2.id ) " +
      "        AND r3.numberOfChannels = :numberOfChannels " +
      "        AND r3.id = r.id " +
      "        AND EXISTS ( " +
      "          SELECT c4.id " +
      "          FROM Result r4 " +
      "          INNER JOIN r4.channels c4 " +
      "          WHERE c4.id IN :p4 " +
      "          AND c4.id NOT IN ( c0.id ) " +
      "          AND c4.id NOT IN ( c1.id ) " +
      "          AND c4.id NOT IN ( c2.id ) " +
      "          AND c4.id NOT IN ( c3.id ) " +
      "          AND r4.numberOfChannels = :numberOfChannels " +
      "          AND r4.id = r.id " +
      "        ) " +
      "      ) " +
      "    ) " +
      "  ) " +
      ") " +
      "ORDER BY r.minimumSeparationImd DESC," +
      "         r.minimumSeparationChannel DESC";

  private static String QUERY_6 = "SELECT r " +
      "FROM Result r " +
      "WHERE r.numberOfChannels = :numberOfChannels " +
      "AND r.minimumSeparationChannel >= :minimumSeparationChannel " +
      "AND r.minimumSeparationImd >= :minimumSeparationImd " +
      "AND EXISTS (" +
      "  SELECT c0.id " +
      "  FROM Result r0 " +
      "  INNER JOIN r0.channels c0 " +
      "  WHERE c0.id IN :p0 " +
      "  AND r0.id = r.id " +
      "  AND r0.numberOfChannels = :numberOfChannels " +
      "  AND EXISTS ( " +
      "    SELECT c1.id " +
      "    FROM Result r1 " +
      "    INNER JOIN r1.channels c1 " +
      "    WHERE c1.id IN :p1 " +
      "    AND c1.id NOT IN ( c0.id ) " +
      "    AND r1.numberOfChannels = :numberOfChannels " +
      "    AND r1.id = r.id " +
      "    AND EXISTS ( " +
      "      SELECT c2.id " +
      "      FROM Result r2 " +
      "      INNER JOIN r2.channels c2 " +
      "      WHERE c2.id IN :p2 " +
      "      AND c2.id NOT IN ( c0.id ) " +
      "      AND c2.id NOT IN ( c1.id ) " +
      "      AND r2.numberOfChannels = :numberOfChannels " +
      "      AND r2.id = r.id " +
      "      AND EXISTS ( " +
      "        SELECT c3.id " +
      "        FROM Result r3 " +
      "        INNER JOIN r3.channels c3 " +
      "        WHERE c3.id IN :p3 " +
      "        AND c3.id NOT IN ( c0.id ) " +
      "        AND c3.id NOT IN ( c1.id ) " +
      "        AND c3.id NOT IN ( c2.id ) " +
      "        AND r3.numberOfChannels = :numberOfChannels " +
      "        AND r3.id = r.id " +
      "        AND EXISTS ( " +
      "          SELECT c4.id " +
      "          FROM Result r4 " +
      "          INNER JOIN r4.channels c4 " +
      "          WHERE c4.id IN :p4 " +
      "          AND c4.id NOT IN ( c0.id ) " +
      "          AND c4.id NOT IN ( c1.id ) " +
      "          AND c4.id NOT IN ( c2.id ) " +
      "          AND c4.id NOT IN ( c3.id ) " +
      "          AND r4.numberOfChannels = :numberOfChannels " +
      "          AND r4.id = r.id " +
      "          AND EXISTS ( " +
      "            SELECT c5.id " +
      "            FROM Result r5 " +
      "            INNER JOIN r5.channels c5 " +
      "            WHERE c5.id IN :p5 " +
      "            AND c5.id NOT IN ( c0.id ) " +
      "            AND c5.id NOT IN ( c1.id ) " +
      "            AND c5.id NOT IN ( c2.id ) " +
      "            AND c5.id NOT IN ( c3.id ) " +
      "            AND c5.id NOT IN ( c4.id ) " +
      "            AND r5.numberOfChannels = :numberOfChannels " +
      "            AND r5.id = r.id " +
      "          ) " +
      "        ) " +
      "      ) " +
      "    ) " +
      "  ) " +
      ") " +
      "ORDER BY r.minimumSeparationImd DESC," +
      "         r.minimumSeparationChannel DESC";

  public static String query(final Integer size) {
    switch (size) {
      case 2:
        return QUERY_2;
      case 3:
        return QUERY_3;
      case 4:
        return QUERY_4;
      case 5:
        return QUERY_5;
      case 6:
        return QUERY_6;
      default:
        throw new IllegalArgumentException();
    }
  }

  private QueryUtil() {
  }
}
