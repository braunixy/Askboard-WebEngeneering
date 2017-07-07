package server.askboard.group.myserveraskboard.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests().mvcMatchers("/", "/registration",
                                                                                      "/questions/all/unanswered",
                                                                                      "/questions/all/noanswer",
                                                                                      "/questions/all",
                                                                                      "/questions/view/**").permitAll()
                .anyRequest().fullyAuthenticated()
                .mvcMatchers("/questions/new").fullyAuthenticated()
                .mvcMatchers("/questions/answer/**").fullyAuthenticated()
                .mvcMatchers("/questions/all/ownanswered").fullyAuthenticated()
                .mvcMatchers("/questions/delete/**").fullyAuthenticated()
                .and().logout().logoutUrl("/logout").logoutSuccessHandler(new OwnLogoutSuccessHandler())
                .and().formLogin().successHandler(new OwnSuccessHandler()).failureHandler(new OwnFailureHandler())
                .and().authenticationProvider(new CustomAuthenticationProvider());
        http.csrf().disable();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
    }
}
