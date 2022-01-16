package com.example.demo.controllers;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Outil;
import com.example.demo.service.IOutilService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;



@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE,
		RequestMethod.PUT }, allowedHeaders = "*")
public class OutilRestController {
	@Autowired
	IOutilService outilService;

	@RequestMapping(value = "/outils", method = RequestMethod.GET)
	public List<Outil> findOutils() {

		List<Outil> outils = outilService.findByOpen(true);
		return outils;
	}
	
	@GetMapping(value = "/outils/{id}")
	public Outil findOutilById(@PathVariable Long id) {
		Outil outil = outilService.findOutil(id);
		
		return outil;
	}
	@GetMapping(value = "/get-file")
	@ResponseBody
	public FileSystemResource getFile(@RequestParam(name = "path") String path) {
		String decodedPath = URLDecoder.decode(path);
		System.out.println(decodedPath);
		File f = new File(decodedPath);
		return new FileSystemResource(f.getAbsolutePath());
	}
	@PostMapping(value = "/outils", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public Outil addOutil(@RequestParam(name = "outil") String outil,
			@RequestParam(name = "codeSource", required = true) MultipartFile codeSource) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		objectMapper.setVisibility(
				VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
		Outil m = null;
		try {
			m = objectMapper.readValue(outil, Outil.class);
			if (codeSource != null) {
				String path = writeFile(m.getNom(), codeSource);
				String encodedPath = "";
				try {
					encodedPath = URLEncoder.encode(path, StandardCharsets.UTF_8.toString());
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				m.setCodeSource(encodedPath);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return outilService.addOutil(m);
	}
	@PutMapping(value = "/outils/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public Outil updateOutil(@PathVariable Long id, @RequestParam(name = "outil") String outil,
			@RequestParam(name = "codeSource", required = false) MultipartFile codeSource) {
		Outil outilInDb = outilService.findOutil(id);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		objectMapper.setVisibility(
				VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
		Outil m = null;
		try {
			m = objectMapper.readValue(outil, Outil.class);
			if (codeSource != null) {
				String path = writeFile(m.getNom(), codeSource);
				String encodedPath = "";
				try {
					encodedPath = URLEncoder.encode(path, StandardCharsets.UTF_8.toString());
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				m.setCodeSource(encodedPath);
			}
			
			if (m.getCodeSource() == "" || m.getCodeSource() == null) {
				m.setCodeSource(outilInDb.getCodeSource());
			}

			m.setId(id);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return outilService.updateOutil(m);
	}
	public String writeFile(String name, MultipartFile file) {

		String uploadsDir = "uploads/" + name + "/";
		File f = new File(uploadsDir);
		String absolutePath = f.getAbsolutePath();
		if (!f.exists()) {
			System.out.println("Creating new directory");
			f.mkdirs();
		}
		String orgName = file.getOriginalFilename();
		String filePath = absolutePath + "/" + orgName;
		File dest = new File(filePath);
		try {
			file.transferTo(dest);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return uploadsDir + "/" + orgName;
	}
	@GetMapping(value = "/outil/search/date")
	public Outil findOneMemberByDate(@RequestParam Date date) {
		return (Outil) outilService.findByDate(date);
	}

	@DeleteMapping(value = "/Outils/{id}")
	public void deleteOutil(@PathVariable Long id) {
		outilService.deleteOutil(id);
	}
}