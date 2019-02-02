package com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.Validations.Common;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.Views.Basic;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.Views.ChannelView;
import java.util.List;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ListIndexBase;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Entity
@Table(name = "result")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "Result_L2")
public class Result {

  @Id
  @SequenceGenerator(name = "result_seq", sequenceName = "result_seq", allocationSize = 500)
  @GeneratedValue(generator = "result_seq", strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  @NotNull(groups = {Common.class})
  @JsonView({Basic.class})
  private Integer id;

  @Version
  @Column(name = "version")
  @JsonView({Basic.class})
  private Integer version;

  @Column(name = "channels")
  @JsonView({Basic.class})
  private Integer numberOfChannels;

  @Column(name = "frequencies")
  @JsonView({Basic.class})
  private String frequencies;

  @Column(name = "min_separation_channel")
  @JsonView({Basic.class})
  private Integer minimumSeparationChannel;

  @Column(name = "avg_separation_channel")
  @JsonView({Basic.class})
  private Double averageSeparationChannel;

  @Column(name = "min_separation_imd")
  @JsonView({Basic.class})
  private Integer minimumSeparationImd;

  @Column(name = "avg_separation_imd")
  @JsonView({Basic.class})
  private Double averageSeparationImd;

  @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinTable(name = "result_channel",
      joinColumns = @JoinColumn(name = "result_id"),
      foreignKey = @ForeignKey(name = "result_channel_result_fk"),
      inverseJoinColumns = @JoinColumn(name = "channel_id"),
      inverseForeignKey = @ForeignKey(name = "result_channel_channel_fk"))
  @JsonView({ChannelView.class})
  @OrderColumn(name = "index", nullable = false)
  @ListIndexBase(1)
  @Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "Result_L2.channels")
  private List<Channel> channels;

}
