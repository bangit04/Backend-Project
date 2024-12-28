package vn.bangit.Backend_Service_Project;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendServiceProjectApplication {

	@Value("${jwt.secretKey}")
	private String jwtKey;

	public static void main(String[] args) {
		SpringApplication.run(BackendServiceProjectApplication.class, args);
	}

	@PostConstruct
	public void Test(){
		System.out.println("jwtKey: " + jwtKey);
	}
}
