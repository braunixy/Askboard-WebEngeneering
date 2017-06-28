package server.askboard.group.myserveraskboard.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .anonymous()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/questions/all/**", "/questions/view/**").permitAll()
                .anyRequest().authenticated().antMatchers("/questions/new").authenticated()
                .antMatchers("/questions/answer/**").authenticated()
                .antMatchers("/questions/delete/**").authenticated();
    }
}
