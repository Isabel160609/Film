package com.init.film.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.init.film.entity.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer>{

}
