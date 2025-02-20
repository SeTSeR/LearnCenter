package com.setser.learningcenter.config;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService accountService;

    public WebSecurityConfig(UserDetailsService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void configure(@NotNull HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/", "/courses", "/course/search",
                            "/register").permitAll()
                    .regexMatchers("/course/show\\?id=\\d+").permitAll()
                    .regexMatchers("/user/show").access("hasAnyRole('ROLE_PUPIL', 'ROLE_ADMIN', 'ROLE_TEACHER')")
                    .antMatchers("/pupil/edit").access("hasRole('ROLE_PUPIL')")
                    .antMatchers("/teacher/edit").access("hasRole('ROLE_TEACHER')")
                    .regexMatchers("/user/timetable.*").permitAll()
                    .regexMatchers("/user/show\\?id=\\d+&isTeacher=(true|false)").permitAll()
                    .regexMatchers("/course/(edit|create).*").access("hasRole('ROLE_ADMIN')")
                    .regexMatchers("/course/register.*").access("hasRole('ROLE_PUPIL')")
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();
    }

    @Override
    protected void configure(@NotNull final AuthenticationManagerBuilder auth) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(accountService);
        provider.setPasswordEncoder(passwordEncoder());
        auth.authenticationProvider(provider);
    }

    @NotNull
    @Contract(" -> new")
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
