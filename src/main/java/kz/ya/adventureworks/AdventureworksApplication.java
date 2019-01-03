/**
 * Main class
 */
package kz.ya.adventureworks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AdventureworksApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdventureworksApplication.class, args);
    }
}
