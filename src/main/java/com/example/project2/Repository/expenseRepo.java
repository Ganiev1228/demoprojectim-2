package com.example.project2.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.project2.entity.expense;
import com.example.project2.entity.workers;

import lombok.Value;

public interface expenseRepo extends JpaRepository<expense, Long> {
//	Eng ko’p reklama xarajatlari qaysi reklama turiga mansub?
	@Query("SELECT e.adType, SUM(e.adCost) FROM expense e  GROUP BY e.adType  HAVING SUM(e.adCost) IS NOT NULL ORDER BY SUM(e.adCost)  DESC LIMIT 1")
	List<Object[]> findByadCost();

//	Qaysi xodim eng ko’p reklama xarajatlarini kiritgan ?
	@Query("SELECT e.worker.id as workerid, SUM(e.adCost) as cost FROM expense e GROUP BY e.worker ORDER BY SUM(e.adCost) DESC limit 1")
	List<Object[]> workerMostExpense();

//	Oxirgi 1 oy ichida nechta reklama yo’lga qo’yilgan ?
	@Query("Select e From expense e Where e.starttime >?1 Group by e Order by e.starttime Desc")
	List<expense> findAllForLastMonth(LocalDateTime lasmonth);

	@Query("Select COUNT(e) From expense e Where e.active = false")
	Long countInactiveExpenses();

//    Har bir reklama turiga nechtadan reklama xarajatlari to’g’ri keladi ?
	@Query("Select e.adType as adType , Count(e.id) as adNumber From expense e Group by e.adType ")
	List<Map<Object[], String>> countByType();

//    4. Oxirgi 1 oy ichida nechta reklama to’xtagan ?
	@Query(value = "SELECT COUNT(*)  FROM expense   WHERE endtime >= date_trunc('month',CURRENT_DATE-INTERVAL '1 MONTH') AND endtime < date_trunc('month',CURRENT_DATE )", nativeQuery = true)
	Long findOverdueADS();

	expense findAllById(long id);

	List<expense> findByWorkerId(Long workerId);

}
