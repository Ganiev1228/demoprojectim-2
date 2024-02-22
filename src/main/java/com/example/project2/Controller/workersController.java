package com.example.project2.Controller;

import java.util.List;

import org.springframework.data.domain.Page;
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

import com.example.project2.DTOclasses.workerDTO;
import com.example.project2.Service.workersService;
import com.example.project2.dclass.pgresponse;
import com.example.project2.entity.workers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/workers")
public class workersController {
	private final workersService workserv;

	@PostMapping("/create")
	public ResponseEntity creat(@RequestBody workerDTO worker) {
		ResponseEntity kk = workserv.creat(worker);
		return ResponseEntity.ok(kk);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity update(@RequestBody workerDTO work, @PathVariable("id") Long id) {
		return ResponseEntity.ok(workserv.update(work, id));
	}

	@GetMapping("/getlist")
	public ResponseEntity getworkerlist() {
		List<workers> worklist = workserv.getall();
		return ResponseEntity.ok(worklist);
	}

	@GetMapping("/getbyname")
	public ResponseEntity byworkersname(@RequestParam("ismi") String ismi) {
		List<workers> byname = workserv.findbyname(ismi);
		return ResponseEntity.ok(byname);
	}

	@DeleteMapping("/deletebyid/{id}")
	public ResponseEntity deleteworkbyid(@PathVariable("id") long id) {
		workserv.deleteworkerbyid(id);
		return ResponseEntity.ok("Ushbu " + id + "-id dagi malumot o'chirildi");
	}

	@GetMapping("/countworkers")
	public ResponseEntity count() {
		long work = workserv.count();
		return ResponseEntity.ok(work + " ta ishchi mavjud " + workserv.ishchilarfoizi() + "% ishchi joylari band");
	}

	@GetMapping("/yoshishchilar")
	public ResponseEntity byyosh() {
		return ResponseEntity.ok(workserv.yoshworkers());
	}

	@GetMapping("/oldworkers")
	public ResponseEntity oldworkers() {
		return ResponseEntity.ok(workserv.oldworkers());
	}

	@GetMapping("/pgetworkers")
	public ResponseEntity getpage(Pageable pageable) {
		Page<workers> pageget = workserv.pagebilan(pageable);
		return ResponseEntity.ok(pageget);// .stream().map(d->workserv.workdto(d)).collect(Collectors.toList()));
	}

	@GetMapping("/pageNoAndSize")
	public ResponseEntity nosize(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam("pageSize") int pageSize) {
		pgresponse paging = workserv.workersbypage(pageNo, pageSize);
		return ResponseEntity.ok(paging);
	}

	@GetMapping("/totalsalary")
	public ResponseEntity totalsalary() {
		return new ResponseEntity<>("Ishchilar maoshining mumiy summasi= " + workserv.getallsum() + "$", HttpStatus.OK);
	}

	@GetMapping("/sortbyage")
	public List<workers> sorting() {
		return workserv.sortByAge();

	}

}
