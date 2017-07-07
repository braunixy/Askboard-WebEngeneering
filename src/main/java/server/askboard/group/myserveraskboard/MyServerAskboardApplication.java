package server.askboard.group.myserveraskboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class MyServerAskboardApplication extends SpringBootServletInitializer {

	//@Autowired
	//private PasswordEncoder passwordEncoder;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MyServerAskboardApplication.class, args);
	}

	/*@Autowired
	public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository repo) throws Exception {
        builder.userDetailsService(s -> new CustomUserDetails(repo.findByUsername(s))).passwordEncoder(passwordEncoder);
    }*/
}
