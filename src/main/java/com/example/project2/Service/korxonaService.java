package com.example.project2.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.project2.Repository.korxonaRepo;
import com.example.project2.entity.Korxona;

@Service
public class korxonaService {
	private final korxonaRepo korxonarepo;
	private final Logger log = LoggerFactory.getLogger(getClass());

	public korxonaService(korxonaRepo korxonarepo) {
		this.korxonarepo = korxonarepo;
	}

	public Korxona savebolim(Korxona korxona) {
		korxonarepo.save(korxona);
		log.info("CREATE: Korxona is created by name:" + korxona.getName());
		return korxona;
	}

	public void del(long id) {
		log.info("DELETE: Korxona is deleted with id:"+id);
		korxonarepo.deleteById(id);
	}

	public List<Korxona> getall() {
		List<Korxona> ddd = korxonarepo.findAll();
		if(ddd.isEmpty()) {
			return null;
		}
		log.info("GET: Get list of Korxona");
		return ddd;
	}
}
