package ru.develop.config;

import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);

        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).
                withUser("admin").password(passwordEncoder().encode("admin")).roles("admin");
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
