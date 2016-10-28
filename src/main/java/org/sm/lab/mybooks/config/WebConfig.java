package org.sm.lab.mybooks.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		registry.addViewController("/home").setViewName("home::content");
		registry.addViewController("/login").setViewName("login::content");
		registry.addViewController("/register").setViewName("register::content");
		registry.addViewController("/forgotten_password").setViewName("forgotten_password::content");
		registry.addViewController("/partialmodal").setViewName("partialmodal::content");
		registry.addViewController("/myModalContent").setViewName("myModalContent::content");
		registry.addViewController("/modal").setViewName("modal::content");
		
		registry.addViewController("/profiles/view").setViewName("profiles/view::content");
		registry.addViewController("/profiles/edit").setViewName("profiles/edit::content");
		
		registry.addViewController("/books/list").setViewName("books/list::content");
		registry.addViewController("/books/search").setViewName("books/search::content");
		registry.addViewController("/books/view").setViewName("books/view::content");
		registry.addViewController("/books/create").setViewName("books/create::content");
		registry.addViewController("/books/edit").setViewName("books/edit::content");

		registry.addViewController("/notes/view").setViewName("notes/view::content");
		registry.addViewController("/notes/edit").setViewName("notes/edit::content");

		registry.addViewController("/readers/list").setViewName("readers/list::content");
		registry.addViewController("/readers/search").setViewName("readers/search::content");
		registry.addViewController("/readers/view").setViewName("readers/view::content");
		registry.addViewController("/readers/edit").setViewName("readers/edit::content");

	}

}