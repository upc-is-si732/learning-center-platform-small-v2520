package pe.edu.upc.center.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * The main application class for the Learning Center Platform Mini application.
 */
@SpringBootApplication
@EnableJpaAuditing
public class LearningCenterPlatformMiniApplication {

  /**
   * The main method to run the Spring Boot application.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(LearningCenterPlatformMiniApplication.class, args);
  }
}
