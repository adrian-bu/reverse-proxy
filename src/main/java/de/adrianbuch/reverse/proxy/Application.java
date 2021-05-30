package de.adrianbuch.reverse.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.adrianbuch.reverse.proxy.domain.request.HTTPRequest;
import de.adrianbuch.reverse.proxy.repository.HTTPRequestRepository;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private final HTTPRequestRepository httpRequestRepository;

	@Autowired
	public Application(HTTPRequestRepository httpRequestRepository) {
		this.httpRequestRepository = httpRequestRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		/*
		Generates some sample data
		*/
		for (var i = 0; i < 7; i++) {
			HTTPRequest httpRequest = new HTTPRequest();
			httpRequest.setUsername("Username: " + i);
			httpRequest.setIsMalicious(Boolean.TRUE);
			httpRequestRepository.save(httpRequest);
		}
	}
}
