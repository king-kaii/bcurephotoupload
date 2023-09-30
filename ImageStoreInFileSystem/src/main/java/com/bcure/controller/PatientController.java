package com.bcure.controller;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bcure.entity.Patient;
import com.bcure.payload.FileResponse;
import com.bcure.repo.PatientRepository;
import com.bcure.service.IPatientService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/image")
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {

	@Autowired
	IPatientService patientService;
	@Autowired
	PatientRepository patientRepository;

	@Value(value = "${project.image}")
	private String path;

	@PostMapping(value = "/stores")
	public ResponseEntity<FileResponse> imageStore(@RequestPart("uploadPresciption") MultipartFile uploadPresciption,
			@RequestPart String name, @RequestPart String[] symptoms, @RequestPart Integer age,
			@RequestPart String gender, @RequestPart String[] disEase) {
		
		

		String fileName = null;
		try {
			fileName = patientService.imageStore(path, uploadPresciption);
			byte[] bytes = uploadPresciption.getBytes();
			String contentType = uploadPresciption.getContentType();
			System.out.println(contentType);
			
			

			Patient patient = new Patient();

			patient.setUploadPresciption(bytes);
			patient.setAge(age);
			patient.setSymptoms(symptoms);
			patient.setGender(gender);
			patient.setName(name);
			patient.setDisEase(disEase);
			patient.setRelativePath(uploadPresciption.getOriginalFilename()+"_"+LocalDate.now());

			Patient save = patientRepository.save(patient);

		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(new FileResponse(null, "Image is not stored.."),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(new FileResponse(fileName, "Image is successfully stored.."), HttpStatus.CREATED);

	}

	@GetMapping(value = "/view/{relativePath}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void viewImage(@PathVariable("relativePath") String relativePath, HttpServletResponse response)
			throws IOException {
		
		

		InputStream resource = patientService.getImage(path, relativePath);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);

		StreamUtils.copy(resource, response.getOutputStream());

	}
	
	 @GetMapping(value = "/views/{relativePath}", produces = MediaType.IMAGE_JPEG_VALUE)
	    public ResponseEntity<Resource> viewImage(@PathVariable("relativePath") String relativePath) {
	        InputStream imageStream = patientService.getImageByRelativePath(relativePath);

	        if (imageStream != null) {
	            // Create an InputStreamResource from the image data
	            Resource resource = new InputStreamResource(imageStream);

	            // Return the image as a ResponseEntity
	            return ResponseEntity.ok()
		                .contentType(MediaType.IMAGE_JPEG)
		                .body(resource);
	        } else {
	            // Handle the case where the image is not found
	            return ResponseEntity.notFound().build();
	        }
	    }
	
	






	@GetMapping("/getAll")
	public ResponseEntity<List<Patient>> getAllPatient() {
		
		List<Patient> allPatient = patientService.getAllPatient();
		
		return new ResponseEntity<List<Patient>>(allPatient, HttpStatus.OK);
		
	}

}
