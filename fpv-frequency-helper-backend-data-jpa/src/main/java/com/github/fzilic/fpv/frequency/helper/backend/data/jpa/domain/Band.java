package com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.Validations.Common;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.Views.BandView;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.Views.Basic;
import java.util.List;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"channels"})
@ToString(includeFieldNames = false, exclude = {"channels", "id", "version", "description", "preselected"})
@Builder
@Entity
@Table(name = "band")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "Band_L2")
public class Band {

  @Id
  @SequenceGenerator(name = "band_seq", sequenceName = "band_seq", allocationSize = 1)
  @GeneratedValue(generator = "band_seq", strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  @NotNull(groups = {Common.class})
  @JsonView({Basic.class})
  private Integer id;

  @Version
  @Column(name = "version")
  @JsonView({Basic.class})
  private Integer version;

  @Column(name = "name", length = 16, nullable = false, unique = true)
  @NotNull(groups = {Common.class})
  @Length(min = 1, groups = {Common.class})
  @JsonView({Basic.class})
  private String name;

  @Column(name = "description", length = 64)
  @JsonView({Basic.class})
  private String description;

  @Column(name = "preselected", columnDefinition = "CHAR(1)", nullable = false)
  @Type(type = "yes_no")
  @JsonView({Basic.class})
  private Boolean preselected;

  @OneToMany(mappedBy = "band", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
  @OrderBy("number")
  @JsonView({BandView.class})
  @Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "Band_L2.channels")
  private List<Channel> channels;

}
