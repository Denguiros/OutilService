package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import com.example.demo.dao.OutilRepository;
import com.example.demo.entities.Outil;

@SpringBootApplication
public class OutilService implements CommandLineRunner {

	@Autowired
	OutilRepository outilRepository;
	@Autowired
	RepositoryRestConfiguration configuration;

	public static void main(String[] args) {
		SpringApplication.run(OutilService.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		configuration.exposeIdsFor(Outil.class);
		Outil outil = new Outil();
		outilRepository.save(outil);
	}
	

}
