package com.github.fzilic.fpv.frequency.helper;

import com.github.fzilic.fpv.frequency.helper.backend.Application;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = false,
    properties = {
        "logging.level.org.springframework=WARN",
        "logging.level.liquibase=WARN",
        "spring.liquibase.change-log=classpath:/liquibase.xml",
        "spring.datasource.url=jdbc:h2:mem:",
        "spring.datasource.username=sa",
        "spring.datasource.password=sa",
        "spring.jpa.hibernate.ddl-auto=none"
    })
@ContextConfiguration(classes = Application.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class LiquibaseTest {

  @Autowired
  private EntityManager entityManager;

  @Test
  void shouldInjectEntityManager() {
    assertThat(entityManager).isNotNull();
  }

}
