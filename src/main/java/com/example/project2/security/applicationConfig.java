package com.example.project2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.project2.Repository.workersRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class applicationConfig {
   private final workersRepository workersrep;

   @Bean
	public UserDetailsService userdetailsservice() {
		return username-> workersrep.findByUsername(username)
			.orElseThrow(()->new UsernameNotFoundException("Worker with this name not found || Bunday ism bilan ishchi topilmadi"));
	}
   @Bean
   public AuthenticationProvider authenticationProvider() {
	   DaoAuthenticationProvider authprovider = new DaoAuthenticationProvider();
	   authprovider.setUserDetailsService(userdetailsservice());
	   authprovider.setPasswordEncoder(passwordencode());
	   return authprovider;
   }
   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	   return config.getAuthenticationManager();
   }


   @Bean
   public PasswordEncoder passwordencode() {
	  return new BCryptPasswordEncoder();
   }

}
