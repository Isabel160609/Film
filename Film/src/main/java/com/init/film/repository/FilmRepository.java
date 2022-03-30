package com.init.film.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.init.film.entity.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {

	public List<Film> findByName(String name);

	public Page<Film> findByName(String name, Pageable pageable);

	public Page<Film> findByYear(int year, Pageable pageable);

	public Page<Film> findByGenreName(String name, Pageable pageable);

	public Page<Film> findByDirectorName(String name, Pageable pageable);

//	select p.name from films.film p inner join films_actors pa on p.film_id=pa.film_id Inner join actors a on a.actor_id=pa.actor_id where a.name like "ana%""
	@Query(value = "select p.name from films.film p inner join films_actors pa on p.film_id=pa.film_id Inner join actors a on a.actor_id=pa.actor_id where a.name = :name ", countQuery = "SELECT count(*) FROM films", nativeQuery = true)
	public Page<String> filmsByActor(String name, Pageable pageable);
}
