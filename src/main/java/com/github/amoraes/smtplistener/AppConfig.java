package com.github.amoraes.smtplistener;

import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;

import com.github.amoraes.smtplistener.smtp.SMTPListener;

/**
 * App Configuration class to get Beans
 * @author Alessandro Moraes
 */
@Configuration
public class AppConfig {

	@Bean
	public SMTPListener smtpListener(){
		return new SMTPListener();
	}
	
	@Bean
    public HttpMessageConverters customConverters() {
        ByteArrayHttpMessageConverter arrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        return new HttpMessageConverters(arrayHttpMessageConverter);
    }
	
	
}
