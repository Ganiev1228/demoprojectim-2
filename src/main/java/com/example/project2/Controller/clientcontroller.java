package com.example.project2.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project2.DTOclasses.clientDto;
import com.example.project2.DTOclasses.errorresponse;
import com.example.project2.Service.clientservice;
import com.example.project2.entity.Clients;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/clients")
public class clientcontroller {

	private final clientservice cserv;

	public clientcontroller(clientservice cserv) {
		this.cserv = cserv;
	}

	@PostMapping("/creat")
	public ResponseEntity save(@RequestBody clientDto mijozm) {
		Clients mm = cserv.creat(mijozm);
		return ResponseEntity.ok(mm);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@RequestBody clientDto cld, @PathVariable("id") Long id) {
		return ResponseEntity.ok(cserv.update(cld, id));
	}

	@PatchMapping("/activeOrDeactive/{id}")
	public ResponseEntity edit(@PathVariable("id") Long id) {
		return ResponseEntity.ok(cserv.activate(id));
	}

	@DeleteMapping("/delete/{id}/{workerId}")
	public ResponseEntity deletel(@PathVariable("id") Long id, @PathVariable("workerId") Long workerId) {
		return ResponseEntity.ok(cserv.delete(id, workerId));
	}

	@GetMapping("/getByWorkerId/{workerId}")
	public ResponseEntity byworkerid(@PathVariable("workerId") Long workerId) {
		return ResponseEntity.ok(cserv.getByWorkerId(workerId));
	}

	@GetMapping("/get")
	public ResponseEntity getlist() {
		List<Clients> mm = cserv.getlistt();
		return ResponseEntity.ok(mm);
	}

	@GetMapping("/getbyname")
	public ResponseEntity getbyname(@RequestParam("ism") String ism) {
		List<Clients> mmn = cserv.getbyname(ism);
		return ResponseEntity.ok(mmn);
	}

	@GetMapping("/bydate")
	public ResponseEntity date(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		if (cserv.bytime(date).isEmpty()) {
			errorresponse err = new errorresponse("Not found registered clients this day");
			return ResponseEntity.ok(err);
		} else {
			return ResponseEntity.ok(cserv.bytime(date));
		}
	}

	@GetMapping("/top3workers")
	public ResponseEntity<?> count() {
		return ResponseEntity.ok(cserv.findtopworkers());
	}

	@GetMapping("/forlastmonth")
	public ResponseEntity<?> regClLastMonth() {
		ResponseEntity lasts = cserv.forlastmonth();
		return ResponseEntity.ok(lasts);
	}

	@GetMapping("/topdayofmonth")
	public ResponseEntity<?> topday() {
		return ResponseEntity.ok(cserv.topdayofmonth());
	}

}