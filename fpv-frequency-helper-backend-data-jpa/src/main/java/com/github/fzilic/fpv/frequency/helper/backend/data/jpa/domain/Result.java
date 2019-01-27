package com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.Validations.Common;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.Views.Basic;
import java.util.List;
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Entity
@Table(name = "result")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
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
  private Integer numberOfChannels;

  @Column(name = "frequencies")
  private String frequencies;

  @Column(name = "min_separation_channel")
  private Integer minimumSeparationChannel;

  @Column(name = "avg_separation_channel")
  private Double averageSeparationChannel;

  @Column(name = "min_separation_imd")
  private Integer minimumSeparationImd;

  @Column(name = "avg_separation_imd")
  private Double averageSeparationImd;

  @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinTable(name = "result_channel",
      joinColumns = @JoinColumn(name = "result_id"),
      foreignKey = @ForeignKey(name = "result_channel_result_fk"),
      inverseJoinColumns = @JoinColumn(name = "channel_id"),
      inverseForeignKey = @ForeignKey(name = "result_channel_channel_fk"))
  private List<Channel> channels;

}
