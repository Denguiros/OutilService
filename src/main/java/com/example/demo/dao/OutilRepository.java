package com.example.demo.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import com.example.demo.entities.Outil;

@RepositoryRestController
public interface OutilRepository extends JpaRepository<Outil, Long> {
	List<Outil> findBySource(String source);
	List<Outil> findAll();
	Outil findByDate(Date date);
}
