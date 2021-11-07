package ru.develop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import ru.develop.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = Logger.getLogger(WebSecurityConfig.class.getName());

    @Value("${storage.user}")
    private String userName;
    @Value("${storage.password}")
    private String password;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.log(Level.INFO,userName);
        log.log(Level.INFO,password);

        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).
                withUser(userName).password(passwordEncoder().encode(password)).roles("admin");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<String> atr1 = new ArrayList<>();
        atr1.add("Authorization");atr1.add("Cache-Control");atr1.add("Content-Type");
        List<String> atr2 = new ArrayList<>();
        atr2.add("*");
        List<String> atr3 = new ArrayList<>();
        atr3.add("DELETE");
        List<String> atr4 = new ArrayList<>();
        atr4.add("Authorization");

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(atr1);
        corsConfiguration.setAllowedOrigins(atr2);
        corsConfiguration.setAllowedMethods(atr3);
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(atr4);

        // You can customize the following part based on your project, it's only a sample
        http.authorizeRequests().antMatchers("/**").hasRole("admin").and().formLogin().permitAll()
                .and().csrf().disable().cors().configurationSource(request -> corsConfiguration);

     /*   http.authorizeRequests().antMatchers("/**").permitAll().anyRequest()
                .authenticated().and().csrf().disable().cors().configurationSource(request -> corsConfiguration);*/

    }

}
