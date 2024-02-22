package com.example.project2.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
		   contact = @Contact(		
				name = "don't contact",
				email = "anonim@gamil.com",
				url = "http//nothing.com"
				),
		   description = "Documentation for Spring project",
		   title = "The Project",
		   version = "X",
		   license = @License(
				   name = "Not any license",
				   url = "http//nothing.com"
				   ),
		   termsOfService = "No Terms of service"
	   ),
		servers = {
				@Server(
						description = "The project",
						url = "http://localhost:8080"
						)
				
		}
 )
@SecurityScheme(
		 name = "bearerAuth",
		 description = "JWT auth description",
		 scheme = "bearer",
		 type = SecuritySchemeType.HTTP,
		 bearerFormat = "JWT",
		 in = SecuritySchemeIn.HEADER)
public class SwaggerConfig {

}
