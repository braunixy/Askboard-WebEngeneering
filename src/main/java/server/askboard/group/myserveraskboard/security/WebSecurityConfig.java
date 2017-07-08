package server.askboard.group.myserveraskboard.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import server.askboard.group.myserveraskboard.services.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PasswordEncoder encoder;
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().disable()
                .and().authorizeRequests().mvcMatchers("/", "/registration",
                                                       "/questions/all/unanswered", "/questions/all/noanswer",
                                                       "/questions/all", "/questions/view/**").permitAll()
                .anyRequest().fullyAuthenticated()
                .mvcMatchers("/questions/new", "/questions/answer/**", "/questions/all/ownanswered",
                             "/questions/delete/**").fullyAuthenticated()
                .and().logout().logoutUrl("/logout").logoutSuccessHandler(new OwnLogoutSuccessHandler())
                .and().formLogin().successHandler(new OwnLoginSuccessHandler())
                .failureHandler(new OwnLoginFailureHandler())
                .and().authenticationProvider(new CustomAuthenticationProvider(userService, encoder));
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
    }
}
