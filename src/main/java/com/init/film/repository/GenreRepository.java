package com.init.film.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.init.film.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer>{

}
