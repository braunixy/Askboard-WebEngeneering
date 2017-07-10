package server.askboard.group.myserveraskboard.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;
import server.askboard.group.myserveraskboard.services.UserServiceImpl;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                
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
        http
                .csrf().csrfTokenRepository(csrfTokenRepository())
                .and()
                .addFilterAfter(csrfHeaderFilter(), SessionManagementFilter.class);
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication();
    }
    
    private Filter csrfHeaderFilter() {
        return new OncePerRequestFilter() {
            
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain) throws ServletException, IOException {
                
                CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
                if (csrf != null) {
                    Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
                    String token = csrf.getToken();
                    if (cookie == null || token != null
                                          && !token.equals(cookie.getValue())) {
                        
                        // Token is being added to the XSRF-TOKEN cookie.
                        cookie = new Cookie("XSRF-TOKEN", token);
                        cookie.setPath("/");
                        cookie.setHttpOnly(false);
                        response.addCookie(cookie);
                    }
                }
                filterChain.doFilter(request, response);
            }
        };
    }
    
    private CookieCsrfTokenRepository csrfTokenRepository() {
        CookieCsrfTokenRepository repository = new CookieCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        repository.setCookieHttpOnly(false);
        return repository;
    }
}
