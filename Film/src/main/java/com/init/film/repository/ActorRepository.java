package com.init.film.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.init.film.entity.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	public List<Actor> findByName(String name);

}
