package com.edutech.progressive.config;

import com.edutech.progressive.jwt.JwtRequestFilter;
import com.edutech.progressive.service.impl.UserLoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserLoginServiceImpl userLoginServiceImpl;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userLoginServiceImpl).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()

                // Public endpoints
                .antMatchers("/login", "/register", "/userlogin/**").permitAll()

                // Student APIs
                .antMatchers(HttpMethod.GET, "/student/**")
                    .access("hasAnyAuthority('STUDENT','ROLE_STUDENT','TEACHER','ROLE_TEACHER')")
                .antMatchers(HttpMethod.POST, "/student/**")
                    .access("hasAnyAuthority('STUDENT','ROLE_STUDENT','TEACHER','ROLE_TEACHER')")
                .antMatchers(HttpMethod.PUT, "/student/**")
                    .access("hasAnyAuthority('STUDENT','ROLE_STUDENT','TEACHER','ROLE_TEACHER')")
                .antMatchers(HttpMethod.DELETE, "/student/**")
                    .access("hasAnyAuthority('STUDENT','ROLE_STUDENT','TEACHER','ROLE_TEACHER')")

                // Teacher APIs
                .antMatchers(HttpMethod.GET, "/teacher/**")
                    .access("hasAnyAuthority('STUDENT','ROLE_STUDENT','TEACHER','ROLE_TEACHER')")
                .antMatchers(HttpMethod.POST, "/teacher/**")
                    .access("hasAnyAuthority('TEACHER','ROLE_TEACHER')")
                .antMatchers(HttpMethod.PUT, "/teacher/**")
                    .access("hasAnyAuthority('TEACHER','ROLE_TEACHER')")
                .antMatchers(HttpMethod.DELETE, "/teacher/**")
                    .access("hasAnyAuthority('TEACHER','ROLE_TEACHER')")

                // Course APIs
                .antMatchers(HttpMethod.GET, "/course/**")
                    .access("hasAnyAuthority('STUDENT','ROLE_STUDENT','TEACHER','ROLE_TEACHER')")
                .antMatchers(HttpMethod.POST, "/course/**")
                    .access("hasAnyAuthority('TEACHER','ROLE_TEACHER')")
                .antMatchers(HttpMethod.PUT, "/course/**")
                    .access("hasAnyAuthority('TEACHER','ROLE_TEACHER')")
                .antMatchers(HttpMethod.DELETE, "/course/**")
                    .access("hasAnyAuthority('TEACHER','ROLE_TEACHER')")

                // Enrollment APIs
                .antMatchers("/enrollment/**")
                    .access("hasAnyAuthority('STUDENT','ROLE_STUDENT','TEACHER','ROLE_TEACHER')")

                // Attendance APIs
                .antMatchers("/attendance/**")
                    .access("hasAnyAuthority('STUDENT','ROLE_STUDENT','TEACHER','ROLE_TEACHER')")

                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
