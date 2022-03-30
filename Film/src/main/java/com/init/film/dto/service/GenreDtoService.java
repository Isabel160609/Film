package com.init.film.dto.service;

import org.springframework.stereotype.Service;

import com.init.film.dto.GenreDto;
import com.init.film.entity.Genre;

@Service
public class GenreDtoService {
	/**
	 * This method convert Genre to GenreDto
	 * 
	 * @Param genre
	 * @return GenreDto
	 */
	public GenreDto ChangeGenreToDto(Genre genre) {

		GenreDto genreDto = new GenreDto();
		genreDto.setName(genre.getName());
		return genreDto;
	}

	/**
	 * This method convert GenreDto to Genre
	 * 
	 * @Param genreDto
	 * @return Genre
	 */
	public Genre ChangeDtoToGenre(GenreDto genreDto) {

		Genre genre = new Genre();
		genre.setName(genreDto.getName());
		return genre;
	}
}
