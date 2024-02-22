package com.example.project2.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project2.Repository.korxonaRepo;
import com.example.project2.Service.korxonaService;
import com.example.project2.entity.Korxona;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/department")
public class korxonaController {
	private final korxonaRepo korxrep;
	private final korxonaService korxonaserv;

	public korxonaController(korxonaService korxonaserv, korxonaRepo korxrep) {
		this.korxrep = korxrep;
		this.korxonaserv = korxonaserv;
	}

	@PostMapping("/create")
	public ResponseEntity creatbolim(@RequestBody Korxona korxona) {
		List<Korxona> existkorxona = korxonaserv.getall();
		for (Korxona korxona2 : existkorxona) {
			if (korxona2.getName().equals(korxona.getName())) {
				return ResponseEntity.ok("Ushbu nomdagi bo'lim mavjud");
			}
		}
		korxonaserv.savebolim(korxona);
		return ResponseEntity.ok(korxona);
	}

	@DeleteMapping("/deletebyid/{id}")
	public ResponseEntity dddd(@PathVariable("id") long id) {
		if (korxrep.existsById(id)) {
			korxonaserv.del(id);
			return ResponseEntity.ok(id + "-id dagi bo'lim o'chirildi");
		} else {
			return ResponseEntity.ok("Ushbu " + id + " id mavjud emas");
		}
	}

	@GetMapping("/getall")
	public ResponseEntity getall() {
		return ResponseEntity.ok(korxonaserv.getall());
	}
}
