package com.example.project2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.project2.Enums.Role;
import com.example.project2.security.JwtFilter;


import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class securityconfig {





private final   JwtFilter jwtfilter;
private final AuthenticationProvider authenticationProvider;



	//	@Bean
//	public InMemoryUserDetailsManager userdetailsservice() {
//		UserDetails us = User.withUsername("user")
//				.password(encoder().encode("password"))
//				.roles("Userr")
//				.build();
//		UserDetails ad = User.withUsername("admin")
//				.password(encoder().encode("pass"))
//				.roles("adminn")
//				.build();
//		return new InMemoryUserDetailsManager(us,ad);
//	}
	@Bean
	public SecurityFilterChain filterchain (HttpSecurity http)throws Exception {

		return http
		.csrf(csrf->csrf.disable())
		    .authorizeHttpRequests(auth->{
//		    auth.requestMatchers(HttpMethod.GET,"/ipa/getworkerslist").hasRole("Userr");
//		    auth.requestMatchers("/ipa/**").hasRole("adminn");
		    	
		    auth.requestMatchers(
		    		"/api/authenticate",
		    		"/api/register",
		    		"/v3/api-docs/**",
		    		"/swagger-ui/**"
		    		).permitAll();
		    auth.requestMatchers(HttpMethod.GET,"/api/workers/**").hasAnyAuthority ("DIRECTOR","MANAGER");
		    auth.requestMatchers(HttpMethod.PUT,"/api/workers/**").hasAnyAuthority("DIRECTOR","MANAGER");
		    auth.requestMatchers(HttpMethod.POST,"/api/workers/**").hasAnyAuthority("DIRECTOR","MANAGER");     
		    auth.requestMatchers(HttpMethod.DELETE,"/api/workers/**").hasAuthority("DIRECTOR");
		    
		    auth.requestMatchers(HttpMethod.GET,"/api/clients/**").hasAnyAuthority("DIRECTOR","MANAGER","WORKER");
		    auth.requestMatchers(HttpMethod.PUT,"/api/clients/**").hasAnyAuthority("DIRECTOR","MANAGER","WORKER");
		    auth.requestMatchers(HttpMethod.POST,"/api/clients/**").hasAnyAuthority("DIRECTOR","MANAGER","WORKER");     
		    auth.requestMatchers(HttpMethod.DELETE,"/api/clients/**").hasAnyAuthority("DIRECTOR","MANAGER","WORKER");
		    
		    auth.requestMatchers(HttpMethod.GET,"/api/expense/**").hasAnyAuthority("DIRECTOR","MANAGER","WORKER");
		    auth.requestMatchers(HttpMethod.PUT,"/api/expense/**").hasAnyAuthority("DIRECTOR","MANAGER","WORKER");
		    auth.requestMatchers(HttpMethod.POST,"/api/expense/**").hasAnyAuthority("DIRECTOR","MANAGER","WORKER");     
		    auth.requestMatchers(HttpMethod.DELETE,"/api/expense/**").hasAnyAuthority("DIRECTOR","MANAGER","WORKER");
		    
		    
		    auth.anyRequest().authenticated();
		    })

		.sessionManagement(session->
		   session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authenticationProvider(authenticationProvider)
		.addFilterBefore(  jwtfilter, UsernamePasswordAuthenticationFilter.class)
		//.httpBasic(Customizer.withDefaults());
		.build();
		

	}



//	@Bean
//	public PasswordEncoder encoder() {
//		return new BCryptPasswordEncoder();
//	}

}
