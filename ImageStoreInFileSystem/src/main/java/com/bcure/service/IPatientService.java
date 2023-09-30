package com.bcure.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bcure.entity.Patient;


public interface IPatientService {
	
	public String imageStore(String path, MultipartFile file) throws IOException;
	
	InputStream getImage(String path, String relativePath) throws FileNotFoundException;
	
	InputStream getImageByRelativePath(String relativePath);
	
	public List<Patient> getAllPatient();
	
	

}
