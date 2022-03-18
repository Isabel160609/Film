package com.init.film.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.init.film.entity.Film;
import com.init.film.repository.CharacterRepository;
import com.init.film.repository.DirectorRepository;
import com.init.film.repository.FilmRepository;
import com.init.film.repository.GenreRepository;

@Service
public class FilmService {

	@Autowired
	private CharacterRepository characterRepository ;
	
	@Autowired
	private DirectorRepository directorRepository;
	
	@Autowired
	private FilmRepository filmRepository;
	
	@Autowired
	private GenreRepository genreRepository;
	
	public List<Film> listFilms(){
		
		List<Film> films=filmRepository.findAll();
		return films;
	}
	
	public Film createFilm(Film film) {
		Film newFilm=
	}
}
