/**
 * @author Amith Gopal
 */
package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * This class is used to give permissions for requests on the Application server by the UI server both running in the same machine but on different ports
 */

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
 
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}
