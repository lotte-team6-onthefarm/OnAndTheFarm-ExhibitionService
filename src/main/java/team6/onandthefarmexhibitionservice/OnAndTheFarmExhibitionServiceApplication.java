package team6.onandthefarmexhibitionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OnAndTheFarmExhibitionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnAndTheFarmExhibitionServiceApplication.class, args);
	}

}
