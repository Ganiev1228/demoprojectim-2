package com.example.project2.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project2.entity.Korxona;

public interface korxonaRepo extends JpaRepository<Korxona, Long> {

	Optional<Korxona> findByName(String name);

	boolean existsByName(String name);

}
