package com.cts.transpogov.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cts.transpogov.dtos.citizen.CitizenDocumentCreateRequest;
import com.cts.transpogov.dtos.citizen.CitizenDocumentResponse;
import com.cts.transpogov.dtos.citizen.CitizenDocumentVerifyRequest;
import com.cts.transpogov.service.ICitizenDocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/citizen-documents")
@RequiredArgsConstructor
public class CitizenDocumentController {

	private final ICitizenDocumentService citizenDocumentService;

		@PostMapping(value = "/upload/{citizenId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
		public CitizenDocumentResponse uploadDocument(@PathVariable Long citizenId,
				@RequestParam("file") MultipartFile file, @RequestParam("docType") String docType,
				@RequestParam(value = "verificationStatus", required = false, defaultValue = "PENDING") String verificationStatus)
				throws IOException {
	
			// 1️⃣ Save file locally
			String uploadDir = "uploads";
			File dir = new File(uploadDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
	
			String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
			Path filePath = Paths.get(uploadDir, fileName);
			Files.write(filePath, file.getBytes());
	
			// 2️⃣ Prepare your DTO (same idea as friend)
			CitizenDocumentCreateRequest request = new CitizenDocumentCreateRequest();
			request.setCitizenId(citizenId);
			request.setDocType(docType);
	
			// 3️⃣ Call service
			return citizenDocumentService.uploadDocument(request, filePath.toString(), verificationStatus);
		}

	@GetMapping("/{documentId}")
	public CitizenDocumentResponse getDocument(@PathVariable Long documentId) {
		return citizenDocumentService.getDocument(documentId);
	}

	@GetMapping("/citizen/{citizenId}")
	public List<CitizenDocumentResponse> getDocumentsByCitizen(@PathVariable Long citizenId) {
		return citizenDocumentService.getDocumentsByCitizen(citizenId);
	}

	@PutMapping("/verify/{documentId}")
	public CitizenDocumentResponse verifyDocument(@PathVariable Long documentId,
			@RequestBody CitizenDocumentVerifyRequest request) {
		return citizenDocumentService.verifyDocument(documentId, request);
	}
}