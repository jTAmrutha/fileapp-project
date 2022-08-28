package com.brooklet.fileapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.brooklet.fileapp.service.FileService;
import com.brooklet.fileapp.service.FileServiceImpl;

@SpringBootApplication
public class FileappApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FileappApplication.class, args);
	}

	@Bean
	public FileService getFileService() {
		return new FileServiceImpl();
	}

	@Override
	public void run(String... args) throws Exception {
		FileService fileService = getFileService();
		fileService.saveFile();
		fileService.loadFile();
	}

}
