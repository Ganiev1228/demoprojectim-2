package com.example.project2.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.project2.DTOclasses.expenseDto;
import com.example.project2.Repository.expenseRepo;
import com.example.project2.Repository.workersRepository;
import com.example.project2.dclass.pgforexpense;
import com.example.project2.entity.expense;
import com.example.project2.entity.workers;

@Service
public class expenseServ {
	private final workersRepository workrepo;
	private final expenseRepo exrepo;
	private final Logger log = LoggerFactory.getLogger(getClass());

	public expenseServ(expenseRepo exrepo, workersRepository workrepo) {
		this.workrepo = workrepo;
		this.exrepo = exrepo;
	}

	public ResponseEntity<?> createxpense(expenseDto exp) {
		expense ex1 = new expense();
		if (!workrepo.existsById(exp.getWorkerId())) {
			log.info("CREATE: Creating expense unsuccesful by workerId:" + exp.getWorkerId());
			return ResponseEntity.ok("Worker id is not found!");
		}
		Optional<workers> workerId = workrepo.findById(exp.getWorkerId());
		LocalDateTime lcd = LocalDateTime.now();
		Long adterm = exp.getAdTerm();
		LocalDateTime endt = LocalDateTime.now().plusDays(adterm);
		ex1.setId(exp.getId());
		ex1.setAdType(exp.getAdType());
		ex1.setAdCost(exp.getAdCost());
		ex1.setAdTerm(exp.getAdTerm());
		ex1.setActive(exp.isActive());
		ex1.setEndtime(endt);
		ex1.setStarttime(lcd);
		ex1.setWorker(workerId.get());
		log.info("CREATE: Expense created by workerId:" + exp.getWorkerId());
		return ResponseEntity.ok(exrepo.save(ex1));
	}

	public ResponseEntity update(expenseDto exd, long id) {
		expense ex = exrepo.findAllById(id);
		if (ex == null) {
			return ResponseEntity.ok(HttpStatus.NOT_FOUND);
		}
		ex.setAdType(exd.getAdType());
		ex.setAdCost(exd.getAdCost());
		ex.setAdTerm(exd.getAdTerm());
		ex.setActive(exd.isActive());
		expense ex1 = exrepo.save(ex);
		log.info("UPDATE: Updated expense with id:" + id);
		return ResponseEntity.ok(ex1);
	}

	public ResponseEntity deletbyid(long id) {
		Optional<expense> exp = exrepo.findById(id);
		if (!exp.isEmpty()) {
			log.info("DELETE: Expense with id:" + id);
			exrepo.deleteById(id);
			return ResponseEntity.ok("Expense is deleted with id:" + id);
		} else {
			log.info("DELETE: Expense not found with id:" + id);
			return ResponseEntity.ok("Expense is not found with id:" + id);
		}
	}

	public List<expense> getall(Long workerId) {
		List<expense> list = exrepo.findByWorkerId(workerId);
		if (workerId == null && list.isEmpty()) {
			log.info("GET: Not found any data");
			return null;
		}
		List<expense> ff = list.stream().sorted(Comparator.comparingLong(expense::getId)).collect(Collectors.toList());
		log.info("GET: Get all expense list");
		return ff;
	}

	public pgforexpense getbypage(int pageNo, int sizeNo) {
		Sort sort = Sort.by(Direction.ASC, "id");
		Pageable pageable = PageRequest.of(pageNo, sizeNo, sort);
		Page<expense> ex = exrepo.findAll(pageable);
		List<expense> list = ex.getContent();
		log.info("GET: Get expense list by pagination");
		pgforexpense pg = new pgforexpense();
		pg.setContent(list);
		pg.setPageSize(ex.getSize());
		pg.setPageNo(ex.getNumber());
		pg.setTotalElements(ex.getTotalElements());
		pg.setTotalPages(ex.getTotalPages());
		pg.setOhirgi(ex.isLast());
		return pg;
	}

	public Page<expense> getbypage2(@PageableDefault(page = 0, size = 10, sort = "anything") Pageable pageable) {
		Page<expense> ex = exrepo.findAll(pageable);
		log.info("GET: Get expense list by pagination2");
		return ex;
	}

	public ResponseEntity topad() {
		List<Object[]> dd = exrepo.findByadCost();
		for (Object[] expense1 : dd) {
			String type = (String) expense1[0];
			Long cost = (Long) expense1[1];
			log.info("GET: Top ad ");
			return ResponseEntity.ok("Eng yuqori xarajatli reklama turi: " + type + ", Summa: " + cost);
		}
		return null;
	}

	public ResponseEntity<?> workermostexpense() {
		List<Object[]> tt = exrepo.workerMostExpense();
		if (tt.isEmpty()) {
			log.info("GET:The worker most expenses not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found recorded expenses");
		}
		for (Object[] objects : tt) {
			Long vv = (Long) objects[0];
			Long ss = (Long) objects[1];
			String dd = vv.toString();
			String kk = ss.toString();
			log.info("GET:The worker with most expenses");
			return ResponseEntity.ok(dd + "- id dagi xodim harajatlari eng ko'p. Summa: " + kk);
		}
		return null;
	}

	public ResponseEntity forlastmonth() {
		LocalDateTime lastmonth = LocalDateTime.now().minusMonths(1);
		List<expense> forlast = exrepo.findAllForLastMonth(lastmonth);
		log.info("GET: Expense's list for last month");
		return ResponseEntity.ok("So'ngi oydagi reklamalar soni: " + forlast.size() + " ta");
	}

	public Long InactiveExpenses() {
		Long falses = exrepo.countInactiveExpenses();
		log.info("GET: Count inactive expenses");
		return falses;
	}

	public List<Map<Object[], String>> countbytype() {
		log.info("GET: Count expenses by type");
		return exrepo.countByType();

	}

	public ResponseEntity overdueads() {
		long numbers = exrepo.findOverdueADS();
		if (numbers == 0) {
			log.info("GET: Get number of expired expenses ==false");
			return ResponseEntity.ok("Bunday malumotlar mavjud emas");
		}
		log.info("GET: Get number of expired expenses==" + numbers);
		return ResponseEntity.ok("Expired expenses number:" + numbers);
	}

}
