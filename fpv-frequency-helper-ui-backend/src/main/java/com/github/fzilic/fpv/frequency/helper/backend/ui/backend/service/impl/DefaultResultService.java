package com.github.fzilic.fpv.frequency.helper.backend.ui.backend.service.impl;

import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Channel;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Channel_;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Result;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.ResultChannel;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.ResultChannel_;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Result_;
import com.github.fzilic.fpv.frequency.helper.backend.ui.backend.data.common.Pilot;
import com.github.fzilic.fpv.frequency.helper.backend.ui.backend.service.ResultService;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultResultService implements ResultService {

  private final EntityManager entityManager;

  @Autowired
  public DefaultResultService(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<Result> query(final Integer minimumSeparation, final Integer maxResults, final List<Pilot> pilots) {

    final CriteriaBuilder builder = entityManager.getCriteriaBuilder();

    CriteriaQuery<Result> query = builder.createQuery(Result.class);
    final Root<Result> from = query.from(Result.class);
    query.select(from);


    query.where(builder.equal(from.get(Result_.numberOfChannels), pilots.size()),
        builder.ge(from.get(Result_.minimumSeparationChannel), minimumSeparation),
        builder.ge(from.get(Result_.minimumSeparationImd), minimumSeparation),
        builder.exists(buildSubQuery(builder, query, from, null, null, pilots.size(), new LinkedList<>(pilots))));

    if (maxResults != null) {
      query.orderBy(builder.desc(from.get(Result_.minimumSeparationImd)),
          builder.desc(from.get(Result_.minimumSeparationChannel)));
    }

    if (maxResults == null) {
      return entityManager.createQuery(query)
          .setHint("org.hibernate.cacheable", true)
          .getResultList();

    }
    else {
      return entityManager.createQuery(query)
          .setMaxResults(maxResults)
          .setHint("org.hibernate.cacheable", true)
          .getResultList();
    }
  }


  private Subquery<Integer> buildSubQuery(final CriteriaBuilder builder, final CriteriaQuery<Result> query, final Root<Result> queryRoot, final Subquery<Integer> previous,
      final Path<Integer> previousPath, final Integer numberOfChannels, final Queue<Pilot> pilots) {

    if (pilots.isEmpty()) {
      return null;
    }

    final Subquery<Integer> current;
    if (previous == null) {
      current = query.subquery(Integer.class);
    }
    else {
      current = previous.subquery(Integer.class);
    }

    final Root<Result> root = current.from(Result.class);
    final ListJoin<Result, ResultChannel> join = root.join(Result_.channels);
    final Join<ResultChannel, Channel> joinChannel = join.join(ResultChannel_.channel);
    final Path<Integer> integerPath = joinChannel.get(Channel_.id);
    current.select(joinChannel.get(Channel_.id));

    final Pilot pilot = pilots.poll();

    if (pilot == null) {
      return null;
    }

    final Subquery<Integer> next = buildSubQuery(builder, query, queryRoot, current, integerPath, numberOfChannels, pilots);

    Predicate predicate = builder.and(
        builder.equal(root.get(Result_.numberOfChannels), numberOfChannels),
        joinChannel.in(pilot.getAvailableChannels()),
        builder.equal(queryRoot.get(Result_.id), root.get(Result_.id)));

    if (previous != null) {
      predicate = builder.and(predicate,
          builder.not(integerPath.in(previousPath)));
    }

    if (next != null) {
      predicate = builder.and(predicate, builder.exists(next));
    }

    return current.where(predicate);
  }

}
