package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.dao.OutilRepository;
import com.example.demo.entities.Outil;

@SpringBootApplication
public class OutilService implements CommandLineRunner {

	@Autowired
	OutilRepository outilRepository;

	public static void main(String[] args) {
		SpringApplication.run(OutilService.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Outil outil = new Outil();
		outilRepository.save(outil);
	}
	

}
