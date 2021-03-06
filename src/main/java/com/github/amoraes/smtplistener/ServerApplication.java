package com.github.amoraes.smtplistener;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.github.amoraes.smtplistener.smtp.SMTPListener;

/**
 * Main class to start the SMTP listener plus the "webmail" app
 * @author Alessandro Moraes
 */
@SpringBootApplication
@EnableScheduling
public class ServerApplication extends SpringBootServletInitializer {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SMTPListener smtpListener;
	
	public ServerApplication(){
	}
	
	@PostConstruct
	public void init(){
		try {
			smtpListener.start();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
	
	private static Class<ServerApplication> applicationClass = ServerApplication.class;
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }
}
