package de.felixbublitz.casestudy.service.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.felixbublitz.casestudy.service.ApplicationData;

@Configuration
public class DatabaseConfig {

	@Bean
	public Database database() {
		return new Database(ApplicationData.FILE_TRAINSTATION_LIST);
	}
}