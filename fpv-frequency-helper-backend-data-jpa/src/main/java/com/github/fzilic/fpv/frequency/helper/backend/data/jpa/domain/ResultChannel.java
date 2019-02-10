package com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.Views.Basic;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.Views.ChannelView;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
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
@Table(name = "result_channel")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "ResultChannel_L2")
public class ResultChannel {

  @Id
  @SequenceGenerator(name = "result_channel_seq", sequenceName = "result_channel_seq", allocationSize = 500)
  @GeneratedValue(generator = "result_channel_seq", strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  @JsonView({Basic.class})
  private Integer id;

  @Version
  @Column(name = "version")
  @JsonView({Basic.class})
  private Integer version;


  @ManyToOne(optional = false)
  @JoinColumn(name = "result_id", foreignKey = @ForeignKey(name = "result_channel_result_fk"))
  @JsonView({Basic.class})
  private Result result;

  @ManyToOne(optional = false)
  @JoinColumn(name = "channel_id", foreignKey = @ForeignKey(name = "result_channel_channel_fk"))
  @JsonView({ChannelView.class})
  private Channel channel;

  @Column(name = "avg_separation_imd")
  @JsonView({Basic.class})
  private Integer minSeparationOtherChannels;

}
