package com.damtab.my_bachelor_motors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MyBachelorMotorsApplication implements CommandLineRunner {

	public final static Logger logger = LoggerFactory.getLogger(MyBachelorMotorsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MyBachelorMotorsApplication.class, args);
	}


	@Override
	public void run(String ...args) throws Exception {
		logger.trace("Message de niveau TRACE");
		logger.debug("Message de niveau DEBUG");
		logger.info("Message de niveau INFO");
		logger.warn("Message de niveau WARN");
		logger.error("Message de niveau ERROR");
	}

}
