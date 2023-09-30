package com.bcure.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bcure.entity.Patient;
import com.bcure.repo.PatientRepository;
import com.bcure.service.IPatientService;

@Service
public class PatientServiceImpl implements IPatientService {

	@Autowired
	PatientRepository patientRepository;
	
	
	@Override
	public String imageStore(String path, MultipartFile file) throws IOException {

		// File Name
		String name = file.getOriginalFilename();

		// FullPath
		String filePath = path + File.separator + name;

		// Create folder if not exist
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}

		// File Copy
		Files.copy(file.getInputStream(), Paths.get(filePath));
			

//		byte[] bytes = file.getBytes();
//		String contentType = file.getContentType();
//		System.out.println(contentType);
//		System.out.println(bytes(0));
		
//		Patient patient =new Patient();
//		
//		patient.setUploadPresciption(bytes);
		
		
//		Patient save = patientRepository.save(patient);
		

		return "file name "+name+"Patient id :"+ "saved successfully !!";
	}

	@Override
	public InputStream getImage(String path, String relativePath) throws FileNotFoundException {

		String fullPath = path + File.separator + relativePath;
		System.out.println(fullPath);
		InputStream is = new FileInputStream(fullPath);
		System.out.println(is);

		return is;
	}
	

	public InputStream getImageByRelativePath(String relativePath) {
	    try {
	        // Construct the absolute path based on the relative path, e.g., by appending it to a base directory.
	    	
	    	int lastIndexOf = relativePath.lastIndexOf("_");
	    	
	    	String filePath = relativePath;
	    	String path = filePath.substring(0, lastIndexOf);
	    	System.out.println(path); // This will output "fileName"

	    	
	        String absolutePath = "D:\\BCure Photos\\" + path;
	        
	        // Open the image file as an InputStream.
	        InputStream inputStream = new FileInputStream(absolutePath);
	        
	        return inputStream;
	    } catch (IOException e) {
	        // Handle any exceptions that may occur during file access.
	        e.printStackTrace();
	        return null; // Or throw an exception as needed.
	    }
	}


	@Override
	public List<Patient> getAllPatient() {

		List<Patient> allPatient = patientRepository.findAll();
		return allPatient;
	}

}
