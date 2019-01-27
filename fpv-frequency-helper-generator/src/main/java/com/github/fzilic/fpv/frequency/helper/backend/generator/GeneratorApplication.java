package com.github.fzilic.fpv.frequency.helper.backend.generator;

import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.DataConfiguration;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@Import({DataConfiguration.class})
public class GeneratorApplication {

  public static void main(String[] args) {
    final SpringApplication application = new SpringApplication(GeneratorApplication.class);

    application.setDefaultProperties(Arrays.stream(new String[][]{
        {"spring.config.name", "generator"},
        {"spring.config.additional-location", "file:./etc/"}
    }).collect(Collectors.toMap(data -> data[0], data -> data[1])));

    application.run(args);
  }

}
