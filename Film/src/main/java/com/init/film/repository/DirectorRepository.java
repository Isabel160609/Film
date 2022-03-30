package com.init.film.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.init.film.entity.Director;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Integer> {

	public List<Director> findByName(String name);
}
