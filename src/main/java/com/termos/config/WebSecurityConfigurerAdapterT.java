package com.termos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigurerAdapterT extends WebSecurityConfigurerAdapter {

    @Autowired
    public DataSource dataSource;

    @Autowired
    public WebSecurityConfigurerAdapterT(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jdbcUserDetailsManager()).passwordEncoder(passwordEncoder());
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .withDefaultSchema()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder().encode("admin")).roles("USER", "ADMIN");
    }


    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager() {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);
        if (!jdbcUserDetailsManager.userExists("admin")) {
            jdbcUserDetailsManager.createUser(
                    User.withUsername("admin")
                            .password(passwordEncoder()
                                    .encode("admin1"))
                            .roles("USER", "ADMIN")
                            .build());

        }


        return jdbcUserDetailsManager;

    }
    @Bean
    public JdbcUserDetailsManager userDetailsManager() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
        manager.setDataSource(dataSource);
        manager.setUsersByUsernameQuery(
                "select username,password,enabled from users where username=?");
        manager.setAuthoritiesByUsernameQuery(
                "select username, authority from authorities where username=?");
        manager.setRolePrefix("ROLE_");
        return manager;
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/users/**").hasRole("ADMIN")
                .antMatchers("/books/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/orders/**").hasRole("ADMIN");
    }

    @Bean
    public AuthEntryPoint getBasicAuthEntryPoint() {
        return new AuthEntryPoint();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }}


