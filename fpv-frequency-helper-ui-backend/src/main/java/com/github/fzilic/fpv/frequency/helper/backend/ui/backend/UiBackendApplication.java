package com.github.fzilic.fpv.frequency.helper.backend.ui.backend;

import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.DataConfiguration;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({DataConfiguration.class})
public class UiBackendApplication {

  public static void main(final String[] args) {
    final SpringApplication application = new SpringApplication(UiBackendApplication.class);

    application.setDefaultProperties(Arrays.stream(new String[][]{
        {"spring.config.name", "ffhb"},
        {"spring.config.additional-location", "file:./etc/"}
    }).collect(Collectors.toMap(data -> data[0], data -> data[1])));

    application.run(args);
  }

}
