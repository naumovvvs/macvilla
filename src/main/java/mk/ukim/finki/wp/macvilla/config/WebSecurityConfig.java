package mk.ukim.finki.wp.macvilla.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationProvider customAuthenticationProvider;

    public WebSecurityConfig(CustomAuthenticationProvider customAuthenticationProvider) {
        this.customAuthenticationProvider = customAuthenticationProvider;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/", "/home", "/about-us", "/contact", "/register", "/city/**", "/place/**", "/assets/**",
                        "/css/**", "/img/**", "/video/**", "/api/**", "/svg/**", "/js/**", "https://**").permitAll()
                .antMatchers("/dashboard/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login").permitAll()
                    .failureUrl("/login?error=BadCredentials")
                    .defaultSuccessUrl("/home", true)
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("macvilla-cookie")
                .logoutSuccessUrl("/home")
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // In Memory authentication for testing purposes
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password(passwordEncoder.encode("user"))
//                .authorities("ROLE_USER")
//                .and()
//                .withUser("admin")
//                .password(passwordEncoder.encode("admin"))
//                .authorities("ROLE_ADMIN");

        // TODO: Authentication with provider, persistence in postgresql
        auth.authenticationProvider(customAuthenticationProvider);
    }


}
