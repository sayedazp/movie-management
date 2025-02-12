package com.fawry.sayed;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	

    @Value("${security.user.admin.mail}")
    private String adminMail;
   
    @Value("${security.user.admin.password}")
    private String adminpass;
   
    @Value("${security.user.client.mail}")
    private String userMail;
    
    @Value("${security.user.client.password}")
    private String userPass;
   
	
	public static void main(String[] args) {
		SpringApplication.run(FawryTaskApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		User user1 = new User();
		user1.setEmail(adminMail);
		user1.setPassword(encoder.encode(adminpass));
		Authority auth =  new Authority();
		auth.setName("ROLE_ADMIN");
		user1.setRole(Set.of(auth));

		User user2 = new User();
		user2.setEmail(userMail);
		user2.setPassword(encoder.encode(userPass));
		Authority auth2 =  new Authority();
		auth2.setName("ROLE_USER");
		user2.setRole(Set.of(auth2));

		userRepository.saveAll(List.of(user1, user2) );

	}

}
