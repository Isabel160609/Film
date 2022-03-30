package com.init.film.dto.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.init.film.dto.GenreDto;
import com.init.film.entity.Genre;

@SpringBootTest(classes = GenreDtoService.class)
class GenreDtoServiceTest {

	@Autowired
	public GenreDtoService genreDtoService;

	@Test
	void ChangeGenreToDtoTest() {
		Genre genre = new Genre(1, "romantic");
		GenreDto genreDto = new GenreDto("romantic");
		GenreDto expected = genreDtoService.ChangeGenreToDto(genre);

		assertEquals(genreDto.getName(), expected.getName());
	}

	@Test
	void ChangeDtoToGenreTest() {
		Genre genre = new Genre(1, "romantic");
		GenreDto genreDto = new GenreDto("romantic");
		Genre expected = genreDtoService.ChangeDtoToGenre(genreDto);

		assertEquals(genre.getName(), expected.getName());
	}

}
