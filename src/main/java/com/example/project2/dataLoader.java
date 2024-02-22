package com.example.project2;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.project2.Enums.Role;
import com.example.project2.Repository.korxonaRepo;
import com.example.project2.Repository.workersRepository;
import com.example.project2.entity.Korxona;
import com.example.project2.entity.workers;

@Component
public class dataLoader implements ApplicationRunner{
    private final workersRepository workrepo;
    private final PasswordEncoder passencoder;
	private final korxonaRepo korxrep;
	 public dataLoader(korxonaRepo korxrepo, 
			           workersRepository workrepo,
			           PasswordEncoder passencoder) {
		this.workrepo = workrepo;
		this.passencoder = passencoder;
		this.korxrep=korxrepo;
	 }


	@Override
	public void run(ApplicationArguments args) throws Exception {
       Date now = new Date();
       SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm:ss  dd:MMM:yyyy");
		workers w = new workers();
		w.setId(1);
		w.setIsmi("direct");
		w.setPassword(passencoder.encode("123"));
		w.setUsername("direct");
		w.setFamiliyasi("w");
		w.setYoshi(33);
		w.setMaosh(100);
		w.setYashash_manzili("mars");
		w.setPassportNumber("no123");
		w.setJshshir(123);
		w.setMillati("uzb");
		w.setCreatedDateTime(dateformat.format(now));
		w.setRole(Role.DIRECTOR);
		Set<Korxona> korxona = new HashSet<Korxona>();
		Korxona korx = new Korxona();
		korx.setName("Director");
		korxrep.save(korx);
		korxona.add(korx);
		w.setKorxona(korx);
	workrepo.save(w);

	}

}
