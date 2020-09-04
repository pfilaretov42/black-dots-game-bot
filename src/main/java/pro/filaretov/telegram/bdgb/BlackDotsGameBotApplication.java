package pro.filaretov.telegram.bdgb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class BlackDotsGameBotApplication {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(BlackDotsGameBotApplication.class, args);
	}

}
