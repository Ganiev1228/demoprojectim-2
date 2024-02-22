package com.example.project2.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponse;

import com.example.project2.DTOclasses.errorresponse;
import com.example.project2.DTOclasses.workerDTO;
import com.example.project2.Repository.korxonaRepo;
import com.example.project2.Repository.workersRepository;
import com.example.project2.dclass.pgresponse;
import com.example.project2.entity.Korxona;
import com.example.project2.entity.workers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class workersService {
	private final workersRepository workersrepo;
	private final korxonaRepo korxrep;
	private final PasswordEncoder encoder;

	public ResponseEntity<?> creat(workerDTO worker) {
		Korxona kor;
		workers w1 = new workers();
		if (worker.getKorxona() != null && korxrep.existsByName(worker.getKorxona().getName())) {
			kor = korxrep.findByName(worker.getKorxona().getName()).get();
		} else {
			errorresponse err = new errorresponse("not found korxona");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
		}
		Date hozir = new Date();
		SimpleDateFormat datef = new SimpleDateFormat("HH:mm:ss dd/MMM/yyyy");
		w1.setId(worker.getId());
		w1.setIsmi(worker.getIsmi());
		w1.setPassword(encoder.encode(worker.getPassword()));
		w1.setUsername(worker.getUsername());
		w1.setFamiliyasi(worker.getFamiliyasi());
		w1.setYoshi(worker.getYoshi());
		w1.setMaosh(worker.getMaosh());
		w1.setYashash_manzili(worker.getYashash_manzili());
		w1.setPassportNumber(worker.getPassportNumber());
		w1.setJshshir(worker.getJshshir());
		w1.setMillati(worker.getMillati());
		w1.setRole(worker.getRole());
		w1.setKorxona(kor);
		w1.setCreatedDateTime(datef.format(hozir));
		workersrepo.save(w1);
		log.info("CREATE: New worker registered with id:" + worker.getId());
		return ResponseEntity.ok(worker);
	}

	public ResponseEntity<?> update(workerDTO work, Long id) {
		workers w1 = workersrepo.findAllById(id);
		if (w1 == null) {
			return ResponseEntity.ok("Worker with id:" + id + " not found");
		}

		w1.setIsmi(work.getIsmi());
		w1.setPassword(encoder.encode(work.getPassword()));
		w1.setUsername(work.getUsername());
		w1.setFamiliyasi(work.getFamiliyasi());
		w1.setYoshi(work.getYoshi());
		w1.setMaosh(work.getMaosh());
		w1.setYashash_manzili(work.getYashash_manzili());
		w1.setPassportNumber(work.getPassportNumber());
		w1.setJshshir(work.getJshshir());
		w1.setMillati(work.getMillati());
		w1.setKorxona(work.getKorxona());
		w1.setRole(work.getRole());
		workers ww = workersrepo.save(w1);
		log.info("UPDATE: Updated worker with id:" + id);
		return ResponseEntity.ok(ww);

	}

	public List<workers> getall() {
		List<workers> wlist = workersrepo.findAll();
		log.info("GET: Got worker's list ");
		return wlist.stream().sorted(Comparator.comparingLong(workers::getId)).collect(Collectors.toList());
	}

	public List<workers> findbyname(String ismi) {
		List<workers> byismi = workersrepo.findByIsmi(ismi);
		log.info("GET: Request find by worker name: " + ismi);
		return byismi;
	}

	public void deleteworkerbyid(Long id) {
		log.info("DELETE: Deleted id: " + id);
		workersrepo.deleteById(id);
	}

	public long count() {
		log.info("GET: Count workers");
		return workersrepo.count();
	}

	public float ishchilarfoizi() {
		float percentage;
		int totalScore = 100;
		percentage = (workersrepo.count() * 100) / totalScore;
		log.info("GET: Workers number with percents: " + percentage + "%");
		return percentage;
	}

	public ResponseEntity yoshworkers() {
		List<workers> worker = workersrepo.findAll();
		List<workers> youngworkers = new ArrayList<>();
		for (workers worke : worker) {
			if (worke.getYoshi() <= 35) {
				youngworkers.add(worke);
			}
		}
		if (youngworkers.isEmpty()) {
			return ResponseEntity.ok("35 yoshdan kichik ishchilar mavjud emas");
		}
		log.info("GET: Get young worker's list ");
		return ResponseEntity.ok(youngworkers);

	}

	public ResponseEntity<?> oldworkers() {
		List<workers> oldworkers = workersrepo.findAll();
		List<workers> oldworker = new ArrayList<>();
		for (workers olds : oldworkers) {
			if (olds.getYoshi() > 35 && olds.getYoshi() <= 55) {
				oldworker.add(olds);
			}
		}
		if (oldworker.isEmpty()) {
			return ResponseEntity.ok("35 yoshdan katta yoshdagi ishchilar mavjud emas");
		}
		log.info("GET: Old worker's list ");
		return ResponseEntity.ok(oldworker);
	}

	public Page<workers> pagebilan(Pageable pageable) {
		return workersrepo.findAll(pageable);
	}

	public pgresponse workersbypage(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<workers> workpage = workersrepo.findAll(pageable);
		List<workers> rr = workpage.getContent();

		pgresponse presp = new pgresponse();
		presp.setContent(rr);
		presp.setPageNo(workpage.getNumber());
		presp.setPageSize(workpage.getSize());
		presp.setTotalElements(workpage.getTotalElements());
		presp.setTotalPages(workpage.getTotalPages());
		presp.setOhirgi(workpage.isLast());
		log.info("GET: Worker's list by pagination");
		return presp;
	}

	public double getallsum() {
		List<workers> work = workersrepo.findAll();
		double totalsalary = 0;
		for (workers worker : work) {
			totalsalary += worker.getMaosh();
		}
		log.info("GET: Total salary of workers");
		return totalsalary;
	}

	public List<workers> sortByAge() {
		List<workers> dd = workersrepo.findAll();
		List<workers> jj = dd.stream().sorted((b1, b2) -> {
			if (b1.getYoshi() == b2.getYoshi() && b1.getIsmi() != null)
				return b1.getIsmi().compareTo(b2.getIsmi());
			else if (b1.getYoshi() < b2.getYoshi())
				return 1;
			else
				return -1;
		}).collect(Collectors.toList());
		log.info("GET: Sorted worker's list by worker's age");
		return jj;
	}

//	   List<workers> ss = dd.stream()
//			   .sorted(Comparator.comparingInt(workers::getYoshi).reversed())
//			   .collect(Collectors.toList());
//	   return ss;
//   }

}