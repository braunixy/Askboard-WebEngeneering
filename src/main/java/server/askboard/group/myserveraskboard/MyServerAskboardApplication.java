package server.askboard.group.myserveraskboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import server.askboard.group.myserveraskboard.repositoties.UserRepository;
import server.askboard.group.myserveraskboard.security.UserCustomDetails;

@SpringBootApplication
public class MyServerAskboardApplication extends SpringBootServletInitializer {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MyServerAskboardApplication.class, args);
	}

	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository repo) throws Exception {
        builder.userDetailsService(s -> new UserCustomDetails(repo.findByUsername(s))).passwordEncoder(passwordEncoder);
    }
}