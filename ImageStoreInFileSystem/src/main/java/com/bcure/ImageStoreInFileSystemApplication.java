package com.bcure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImageStoreInFileSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageStoreInFileSystemApplication.class, args);
		System.out.println("Hello...");
	}

}
