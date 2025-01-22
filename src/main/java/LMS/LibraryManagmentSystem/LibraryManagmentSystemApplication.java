package LMS.LibraryManagmentSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LibraryManagmentSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagmentSystemApplication.class, args);
	}

}
