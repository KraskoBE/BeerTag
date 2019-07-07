package com.telerikacademy.beertag.configuration;

import com.telerikacademy.beertag.models.constants.UserRole;
import com.telerikacademy.beertag.security.JwtConfigurer;
import com.telerikacademy.beertag.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtProvider jwtProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //.antMatchers("/api/**").permitAll()
                /*.antMatchers(HttpMethod.GET,"/api/images/*").permitAll()
                .antMatchers(HttpMethod.GET,"/api/beers").permitAll()
                .antMatchers("/api/beers").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/users/").hasRole(UserRole.Member.toString())
                */.anyRequest().permitAll()
                .and()
                .apply(new JwtConfigurer(jwtProvider));
    }


}