package de.felixbublitz.casestudy.service.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

	@Bean
	public Database database() {
		Database db;
		
		try {
			db = new Database("D_Bahnhof_2016_01_alle.csv");
			return db;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}