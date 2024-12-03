package com.esliceu.SegonaPracticaObligatoria;

import com.esliceu.SegonaPracticaObligatoria.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SegonaPracticaObligatoriaApplication implements WebMvcConfigurer {
	@Autowired
	LoginInterceptor loginInterceptor;
	public static void main(String[] args) {
		SpringApplication.run(SegonaPracticaObligatoriaApplication.class, args);

	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor).addPathPatterns("/start")
				.addPathPatterns("/nav")
				.addPathPatterns("/getCoin")
				.addPathPatterns("/getKey")
				.addPathPatterns("/open")
				.addPathPatterns("/endForm")
				.addPathPatterns("/reset");
	}
}
