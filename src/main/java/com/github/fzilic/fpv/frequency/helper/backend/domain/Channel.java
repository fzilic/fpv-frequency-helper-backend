package com.github.fzilic.fpv.frequency.helper.backend.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.fzilic.fpv.frequency.helper.backend.Validations.Common;
import com.github.fzilic.fpv.frequency.helper.backend.Views.Basic;
import com.github.fzilic.fpv.frequency.helper.backend.Views.ChannelView;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ColumnDefault;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "channel")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
// @JsonIdentityInfo(generator = JSOGGenerator.class)
public class Channel {

  @Id
  @SequenceGenerator(name = "channel_seq", sequenceName = "channel_seq", allocationSize = 1)
  @GeneratedValue(generator = "channel_seq", strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  @NotNull(groups = {Common.class})
  @JsonView({Basic.class})
  private Integer id;

  @Version
  @Column(name = "version")
  @ColumnDefault("0")
  @JsonView({Basic.class})
  private Integer version;

  @Column(name = "number", nullable = false)
  @NotNull(groups = {Common.class})
  @Min(value = 1, groups = {Common.class})
  @JsonView({Basic.class})
  private Integer number;

  @Column(name = "frequency", nullable = false)
  @NotNull(groups = {Common.class})
  @Min(value = 1, groups = {Common.class})
  @JsonView({Basic.class})
  private Integer frequency;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "band_id", foreignKey = @ForeignKey(name = "channel_band_fk"))
  // @NotNull(groups = {Common.class})
  @JsonView({ChannelView.class})
  private Band band;

}
