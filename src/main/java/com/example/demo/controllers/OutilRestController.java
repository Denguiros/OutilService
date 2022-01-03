package com.example.demo.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Outil;
import com.example.demo.service.IOutilService;



@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE,
		RequestMethod.PUT }, allowedHeaders = "*")
public class OutilRestController {
	@Autowired
	IOutilService outilService;

	@RequestMapping(value = "/outils", method = RequestMethod.GET)
	public List<Outil> findMembres() {

		List<Outil> outils = outilService.findAll();
		return outils;
	}
	
	@GetMapping(value = "/outil/{id}")
	public Outil findOneOutilById(@PathVariable Long id) {
		Outil outil = outilService.findOutil(id);
		
		return outil;
	}
	@PostMapping(value="/outil")
	public Outil addOutil(@RequestBody Outil outil)
	{
		return outilService.addOutil(outil);
	}
	@GetMapping(value = "/outil/search/date")
	public Outil findOneMemberByDate(@RequestParam Date date) {
		return (Outil) outilService.findByDate(date);
	}

	@GetMapping(value = "/outil/search/source")
	public Outil findOneMemberBySource(@RequestParam String source) {
		return outilService.findBySource(source);
	}

	@DeleteMapping(value = "/membres/{id}")
	public void deleteMembre(@PathVariable Long id) {
		outilService.deleteOutil(id);
	}
}