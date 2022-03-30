package com.init.film.dto.service;

import org.springframework.stereotype.Service;

import com.init.film.dto.FilmDto;
import com.init.film.entity.Film;

@Service
public class FilmDtoService {
	/**
	 * This method convert Film to FilmDto
	 * 
	 * @Param film
	 * @return FilmDto
	 */
	public FilmDto ChangeFilmToDto(Film film) {

		FilmDto filmDto = new FilmDto();
		filmDto.setName(film.getName());
		filmDto.setYear(film.getYear());
		filmDto.setOverview(film.getOverview());
		filmDto.setDirector(film.getDirector());
		filmDto.setGenre(film.getGenre());
		filmDto.setActors(film.getActors());

		return filmDto;
	}

	/**
	 * This method convert FilmDto to Film
	 * 
	 * @Param filmDto
	 * @return Film
	 */
	public Film ChangeDtoToFilm(FilmDto filmDto) {

		Film film = new Film();
		film.setName(filmDto.getName());
		film.setYear(filmDto.getYear());
		film.setOverview(filmDto.getOverview());
		film.setDirector(filmDto.getDirector());
		film.setGenre(filmDto.getGenre());
		film.setActors(filmDto.getActors());

		return film;
	}
}
