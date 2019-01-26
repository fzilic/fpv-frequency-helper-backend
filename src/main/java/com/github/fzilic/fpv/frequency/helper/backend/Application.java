package com.github.fzilic.fpv.frequency.helper.backend;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

  public static void main(final String[] args) {
    final SpringApplication application = new SpringApplication(Application.class);

    application.setDefaultProperties(Arrays.stream(new String[][]{
        {"spring.config.name", "ffhb"},
        {"spring.config.additional-location", "file:./etc/"}
    }).collect(Collectors.toMap(data -> data[0], data -> data[1])));

    application.run(args);
  }

}
