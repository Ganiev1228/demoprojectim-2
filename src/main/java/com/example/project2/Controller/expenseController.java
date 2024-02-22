package com.example.project2.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project2.DTOclasses.expenseDto;
import com.example.project2.Service.expenseServ;
import com.example.project2.dclass.pgforexpense;
import com.example.project2.entity.expense;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/expense")
public class expenseController {
	private final expenseServ exserv;

	public expenseController(expenseServ exServ) {
		this.exserv = exServ;
	}

	@PostMapping("/create")
	public ResponseEntity create(@RequestBody expenseDto rekharj) {
		return ResponseEntity.ok(exserv.createxpense(rekharj));
	}

	@PutMapping("/update/{id}")
	public ResponseEntity update(@RequestBody expenseDto ex, @PathVariable("id") long id) {
		return ResponseEntity.ok(exserv.update(ex, id));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity deletbyid(@PathVariable("id") long id) {
		return ResponseEntity.status(HttpStatus.OK).body(exserv.deletbyid(id));
	}

	@GetMapping("/getall/{workerId}")
	public ResponseEntity getall(@PathVariable("workerId") Long workerId) {
		List<expense> list = exserv.getall(workerId);
		return ResponseEntity.ok(list);
	}

	@GetMapping("/get")
	public ResponseEntity getrek(@RequestParam("pageNo") int pageNo, @RequestParam("sizeNo") int sizeNo) {
		pgforexpense rek = exserv.getbypage(pageNo, sizeNo);
		return ResponseEntity.ok(rek);
	}

	@GetMapping("/getbypage2")
	public ResponseEntity bypage(Pageable pageable) {
		return ResponseEntity.ok(exserv.getbypage2(pageable));
	}

	@GetMapping("/expensivead")
	public ResponseEntity expAdd() {
		return ResponseEntity.ok(exserv.topad());
	}

	@GetMapping("/workermostexpense")
	public ResponseEntity mostexpworker() {
		return ResponseEntity.ok(exserv.workermostexpense());
	}

	@GetMapping("/forlastmonth")
	public ResponseEntity forlastmonth() {
		return ResponseEntity.ok(exserv.forlastmonth());
	}

	@GetMapping("/inactivelastmonth")
	public ResponseEntity noactive() {
		Long ff = exserv.InactiveExpenses();
		return ResponseEntity.ok("Inactive expenses number: " + ff);
	}

	@GetMapping("/numcostfortype")
	public ResponseEntity fortype() {
		List<Map<Object[], String>> dd = exserv.countbytype();
		return ResponseEntity.ok(dd);
	}

	@GetMapping("/overdueads")
	public ResponseEntity<?> overdue() {
		return new ResponseEntity<>(exserv.overdueads(), HttpStatus.OK);
	}

}
