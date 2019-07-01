package com.telerikacademy.beertag.configuration;

import com.telerikacademy.beertag.models.constants.UserRole;
import com.telerikacademy.beertag.security.JwtConfigurer;
import com.telerikacademy.beertag.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtProvider jwtProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/auth/signin").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users").permitAll()
                .antMatchers("/api/users/").hasRole(UserRole.Member.toString())
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtProvider));
    }
}