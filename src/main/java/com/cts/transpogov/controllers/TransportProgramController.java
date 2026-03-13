package com.cts.transpogov.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.transpogov.dtos.program.ProgramCreateRequest;
import com.cts.transpogov.service.ITransportProgramService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/programs")
@RequiredArgsConstructor
public class TransportProgramController {
	
	private final ITransportProgramService programService;
	
	@GetMapping("/")
	public ResponseEntity<?> getAllPrograms(){
		return ResponseEntity.ok("{data:[],status:'Success'}");
	}
	@GetMapping("/{programId}")
	public ResponseEntity<?> getProgram(@PathVariable Long programId){
		return ResponseEntity.ok("{data:[Program:{}],status:'Success'}");
	}
	@PostMapping("/")
	public ResponseEntity<?> addProgram(@RequestBody ProgramCreateRequest program){
		return ResponseEntity.status(HttpStatus.CREATED).body("{status:'Success',message:'Program Added success'}");
	}
	@PatchMapping("/{programId}/submit")
	public ResponseEntity<?> submitForApproval(@PathVariable Long programId){
		return ResponseEntity.ok("{message:'Submitted',status:'Success'}");
	}
	@PatchMapping("/{programId}/approve")
	public ResponseEntity<?> approveProgram(@PathVariable Long programId){
		return ResponseEntity.ok("{message:'Submitted',status:'Success'}");
	}
	@PatchMapping("/{programId}/status/{status}")
	public ResponseEntity<?> changeProgramStatus(@PathVariable Long programId, @PathVariable String status){
		return ResponseEntity.ok("{message:'Status changed!',status:'Success'}");
	}
	
}
