package com.fawry.sayed;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fawry.sayed.entities.Authority;
import com.fawry.sayed.entities.Movie;
import com.fawry.sayed.entities.Type;
import com.fawry.sayed.entities.User;
import com.fawry.sayed.repositories.MovieRepository;
import com.fawry.sayed.repositories.UserRepository;

@SpringBootApplication
public class FawryTaskApplication implements ApplicationRunner{

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private PasswordEncoder encoder;
	
	public static void main(String[] args) {
		SpringApplication.run(FawryTaskApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		User user1 = new User();
		user1.setEmail("sayed@gmail.com");
		user1.setPassword(encoder.encode("123456"));
		Authority auth =  new Authority();
		auth.setName("ROLE_ADMIN");
		user1.setRole(Set.of(auth));

		User user2 = new User();
		user2.setEmail("sayed@sayed.com");
		user2.setPassword(encoder.encode("123456"));
		Authority auth2 =  new Authority();
		auth2.setName("ROLE_USER");
		user2.setRole(Set.of(auth2));

		userRepository.saveAll(List.of(user1, user2) );
		
		Movie m1 = new Movie();
		m1.setImdbId("dasdsad");
		m1.setTitle("Matrix");
		m1.setType(Type.EPISODE);

		Movie m2 = new Movie();
		m2.setImdbId("dasdsad2");
		m2.setTitle("Matrix2");
		m2.setType(Type.MOVIE);
		movieRepository.saveAll(List.of(m1, m2));
	}

}
