package org.sm.lab.mybooks3;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/books/partialsList").setViewName("books/partialsList::content");
		registry.addViewController("/books/partialsCreate").setViewName("books/partialsCreate::content");
		registry.addViewController("/books/partialsEdit").setViewName("books/partialsEdit::content");
		
		registry.addViewController("/books/home").setViewName("books/home::content");
		registry.addViewController("/books/login").setViewName("books/login::content");
	}

}