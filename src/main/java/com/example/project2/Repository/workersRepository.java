package com.example.project2.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project2.entity.workers;

@Repository
public interface workersRepository extends JpaRepository<workers, Long> {

	List<workers> findByIsmi(String ismi);

	Optional<workers> findByUsername(String username);

	workers findAllById(Long id);
}
