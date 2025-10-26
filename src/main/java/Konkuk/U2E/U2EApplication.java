package Konkuk.U2E;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class U2EApplication {

	public static void main(String[] args) {
		SpringApplication.run(U2EApplication.class, args);
	}

}
