package com.example.demo.service;

import java.util.Date;
import java.util.List;


import com.example.demo.entities.Outil;

public interface IOutilService {
	// Crud sur les Outils
	Outil addOutil(Outil m);

	void deleteOutil(Long id);

	Outil updateOutil(Outil p);
	Outil findOutil(Long id);

	List<Outil> findAll();

	// Filtrage par propriété
	Outil findBySource(String source);

	Outil findByDate(Date date);

}
