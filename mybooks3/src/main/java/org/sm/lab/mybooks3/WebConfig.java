package org.sm.lab.mybooks3;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/books/list").setViewName("books/list::content");
		registry.addViewController("/books/create").setViewName("books/create::content");
		registry.addViewController("/books/edit").setViewName("books/edit::content");
		
		registry.addViewController("/home").setViewName("home::content");
		registry.addViewController("/login").setViewName("login::content");
		registry.addViewController("/register").setViewName("register::content");
	}

}