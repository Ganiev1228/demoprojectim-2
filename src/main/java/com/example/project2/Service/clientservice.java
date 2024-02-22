package com.example.project2.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import com.example.project2.Demoprojectim2Application;
import com.example.project2.DTOclasses.clientDto;
import com.example.project2.DTOclasses.errorresponse;
import com.example.project2.Repository.clientsrepository;
import com.example.project2.Repository.workersRepository;
import com.example.project2.entity.Clients;
import com.example.project2.entity.workers;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class clientservice {
	private static final Logger log = LoggerFactory.getLogger(Demoprojectim2Application.class);
	private final clientsrepository clientrepo;
    private final workersRepository workrepo;
	
    public Clients creat(clientDto client) {
		Clients cl = new Clients();
		Optional<workers> workerId = workrepo.findById(client.getWorkerId());
		if(workerId.isEmpty()) {
			return null;
		}
		Date date = new Date();
		SimpleDateFormat form = new SimpleDateFormat("HH:mm:ss");
		cl.setId(client.getId());
		cl.setIsm(client.getIsm());
		cl.setFamiliya(client.getFamiliya());
		cl.setPassport_raqam(client.getPassport_raqam());
		cl.setJshshir(client.getJshshir());
		cl.setYashash_manzili(client.getYashash_manzili());
		cl.setActive(client.isActive());
		
		cl.setWorker(workerId.get());
		cl.setCreatedDate(LocalDate.now());
		cl.setCreatedTime(form.format(date));
		clientrepo.save(cl);
		log.info("Yangi mijoz saqlandi id: " + cl.getId() + " Saqlagan xodim id raqami: "
				+ workerId.get().getId());
		return cl;
	}

	public ResponseEntity<?> update(clientDto cldto, Long id) {
		Clients cl = clientrepo.findAllById(id);
		if (cl == null) {
			return ResponseEntity.ok("Not found client with id:" + id);
		}

		cl.setIsm(cldto.getIsm());
		cl.setFamiliya(cldto.getFamiliya());
		cl.setPassport_raqam(cldto.getPassport_raqam());
		cl.setJshshir(cldto.getJshshir());
		cl.setYashash_manzili(cldto.getYashash_manzili());
		cl.setActive(cldto.isActive());
		Clients cl1 = clientrepo.save(cl);
		log.info("UPDATE: Updated Client with id:" + id);
		return ResponseEntity.ok(cl1);
	}

	public ResponseEntity<String> activate(Long id) {
		Clients client = clientrepo.findAllById(id);
		if (client.isActive()) {
			client.setActive(false);
			clientrepo.save(client);
			log.info(client.getId() + "-id dagi mijoz faollashtirildi.");
			return ResponseEntity.ok(" Client with " + id + "-id is deactivated");
		} else {
			client.setActive(true);
			clientrepo.save(client);
			log.info(client.getId() + "-id dagi mijoz faolsizlantirildi.");
			return ResponseEntity.ok("Client with " + id + "-id is activated");
		}
	}

	public ResponseEntity delete(long id, long workerId) {
		Optional<Clients> client1 = clientrepo.findById(id);
		if (client1.get().getWorker().getId() == workerId) {
			clientrepo.deleteById(id);
			log.info(id + "-id dagi mijoz o'chirildi" + " By: " + client1.get().getWorker().getId());
			return ResponseEntity.ok("Mijoz o'chirildi");
		} else {
			log.warn("DELETE: Ushbu mijoz " + workerId + " -id orqali ro'yhatdan o'tmagan");
			return ResponseEntity.ok("Worker's id not suitable");
		}
	}

	public ResponseEntity getByWorkerId(Long id) {
		List<Clients> list = clientrepo.findByWorkerId(id);
		if (!list.isEmpty()) {
			log.info("GET:Id bo'yicha qidiruv: id-" + id + "; Requesting by id: " + id);
			return ResponseEntity.ok(list);
		} else {
			log.info("GET: Id bo'yicha qidiruv muvoffaqiyatsiz");
			return ResponseEntity.ok("Clients with this " + id + "-id are'nt found");
		}
	}

	public List<Clients> getlistt() {
		List<Clients> cl = clientrepo.findAll();
		if(cl.isEmpty()) {
			return null;
		}
		log.info("GET ALL: Mijozlar ro'yhati; Requesting by Director,Manager");
		return cl;
	}

	public List<Clients> getbyname(String ism) {
		List<Clients> mmnn = clientrepo.findAllByIsmLike(ism);
		if(mmnn.isEmpty()) {
			return null;
		}
		log.info("GET BY NAME: Ism bo'yicha qidiruv; Requesting by Director,Manager");
		return mmnn;
	}

	public List<Clients> bytime(LocalDate date) {
		List<Clients> cl = clientrepo.findAllByCreatedDate(date);
		if(cl.isEmpty()) {
			return null;
		}
		log.info("GET BY DAY: Tanlangan kun bo'yicha mijozlar ro'yhati");
		return cl;
	}

	public List<Object[]> findtopworkers() {
		List<Object[]> top3 = clientrepo.findTop3WorkersByClientCount();
		if (top3.isEmpty()) {
			return null;
		}
		List<Object[]> list = new ArrayList<>();
		for (Object[] tops : top3.subList(0, 3)) {
			list.add(tops);
		}
		log.info("GET: Eng faol 3 xodim");
		return list;
	}

	public ResponseEntity forlastmonth() {
		LocalDate lastmonth = LocalDate.now().minusMonths(1);
		Long lasts = clientrepo.forlastmonth(lastmonth);
		if (lasts != null) {
			log.info("GET: So'ngi oyda ro'yhatdan o'tgan mijozlar ro'yhati");
			return ResponseEntity.ok("So'nggi oyda ro'yhatdan o'tgan mijozlar soni " + lasts + " ta.");
		} else {
			errorresponse err = new errorresponse("So'ngi oyda ro'yhatdan o'tganlar mavjud emas.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
		}
	}

	public ResponseEntity topdayofmonth() {
		LocalDate lastmonth = LocalDate.now().minusMonths(1);
		List<Object[]> vv = clientrepo.findTopDayOfMonth(lastmonth);
		if (vv.isEmpty()) {
			return ResponseEntity.ok("GET: Not found any data");
		}
		for (Object[] topday : vv) {
			LocalDate kun = (LocalDate) topday[0];
			Long soni = (Long) topday[1];
			String kun1 = kun.toString();
			String soni1 = soni.toString();
			log.info("GET: The day which most clients registered in the last month");
			return ResponseEntity.ok("Top day of last month: " + kun1 + " Clients number: " + soni1);
		}
		return null;

	}
}
