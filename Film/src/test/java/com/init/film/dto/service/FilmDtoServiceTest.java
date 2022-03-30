package com.init.film.dto.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.init.film.dto.FilmDto;
import com.init.film.entity.Film;

@SpringBootTest(classes = FilmDtoService.class)
class FilmDtoServiceTest {

	@Autowired
	public FilmDtoService filmDtoService;

	@Test
	void ChangeFilmToDtoTest() {
		Film film = new Film(1, "Titanic", 1997, "jhvghklk");
		FilmDto filmDto = new FilmDto("Titanic", 1997, "jhvghklk");
		FilmDto expected = filmDtoService.ChangeFilmToDto(film);

		assertEquals(filmDto.getName(), expected.getName());
	}

	@Test
	void ChangeDtoToFilmTest() {
		Film film = new Film(1, "Titanic", 1997, "jhvghklk");
		FilmDto filmDto = new FilmDto("Titanic", 1997, "jhvghklk");
		Film expected = filmDtoService.ChangeDtoToFilm(filmDto);

		assertEquals(film.getName(), expected.getName());
	}
}
