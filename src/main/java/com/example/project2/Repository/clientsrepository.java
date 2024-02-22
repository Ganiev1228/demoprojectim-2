package com.example.project2.Repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.project2.entity.Clients;

public interface clientsrepository extends JpaRepository<Clients, Long> {
	Clients findAllById(Long id);

	List<Clients> findAllByIsmLike(String ism);

	List<Clients> findAllByCreatedDate(LocalDate createdDate);

	List<Clients> findByWorkerId(Long workerId);

	long countByWorkerId(long workerId);

	@Query("SELECT c.worker, COUNT(c)  FROM Clients c JOIN c.worker  GROUP BY c.worker ORDER BY COUNT(c) DESC")
	List<Object[]> findTop3WorkersByClientCount();

	@Query("SELECT  COUNT(c) FROM Clients c WHERE c.createdDate >?1  ")
	Long forlastmonth(LocalDate lastmonth);

	@Query("SELECT c.createdDate as vaqt,COUNT(c) as soni FROM Clients c WHERE c.createdDate>?1  GROUP BY c.createdDate ORDER BY COUNT(c) DESC LIMIT 1")
	List<Object[]> findTopDayOfMonth(LocalDate lastmonth);


}
