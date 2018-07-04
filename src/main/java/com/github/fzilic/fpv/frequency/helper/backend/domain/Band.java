package com.github.fzilic.fpv.frequency.helper.backend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.github.fzilic.fpv.frequency.helper.backend.Validations.Common;
import com.github.fzilic.fpv.frequency.helper.backend.Views.BandView;
import com.github.fzilic.fpv.frequency.helper.backend.Views.Basic;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.ListIndexBase;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "band")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@ToString(exclude = {"channels"})
@EqualsAndHashCode(exclude = {"channels"})
// @JsonIdentityInfo(generator = UUIDGenerator.class)
public class Band {

  @Id
  @SequenceGenerator(name = "band_seq", sequenceName = "band_seq", allocationSize = 1)
  @GeneratedValue(generator = "band_seq", strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  @NotNull(groups = {Common.class})
  @JsonProperty("id")
  @JsonView({Basic.class})
  private Integer id;

  @Version
  @Column(name = "version")
  @ColumnDefault("0")
  @JsonProperty("version")
  @JsonView({Basic.class})
  private Integer version;

  @Column(name = "name", length = 16, nullable = false, unique = true)
  @NotNull(groups = {Common.class})
  @Length(min = 1, groups = {Common.class})
  @JsonProperty("name")
  @JsonView({Basic.class})
  private String name;

  @Column(name = "description", length = 64)
  @JsonProperty("description")
  @JsonView({Basic.class})
  private String description;

  @OneToMany(mappedBy = "band", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
  @OrderColumn(name = "ordinal")
  @ListIndexBase(1)
  @JsonProperty("channels")
  @JsonView({BandView.class})
  private List<Channel> channels;

}
