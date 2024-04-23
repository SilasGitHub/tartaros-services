package tartaros.googleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GoogleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoogleServiceApplication.class, args);
	}

	@GetMapping("/")
	public void healthCheck() { }

}
