package com.init.film.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.init.film.dto.GenreDto;
import com.init.film.dto.service.GenreDtoService;
import com.init.film.entity.Genre;
import com.init.film.repository.GenreRepository;

@SpringBootTest(classes = GenreServiceTest.class)
@TestMethodOrder(OrderAnnotation.class)
class GenreServiceTest {

	@Mock
	public GenreDtoService genreDtoService;

	@Mock
	private GenreRepository genreRepository;

	@InjectMocks
	private GenreService genreService;

	List<GenreDto> genresDto;

	GenreDto genreDto1;
	GenreDto genreDto2;
	GenreDto genreDto3;

	@Test
	@Order(1)
	public void test_listGenres() {
		GenreDto genreDto1 = new GenreDto("romantic");
		GenreDto genreDto2 = new GenreDto("action");
		Genre genre1 = new Genre("romantic");
		Genre genre2 = new Genre("action");
		List<Genre> genres = new ArrayList<Genre>();
		genres.add(genre1);
		genres.add(genre2);
		List<GenreDto> genresDto = new ArrayList<GenreDto>();
		genresDto.add(genreDto1);
		genresDto.add(genreDto2);
		Pageable pageable = PageRequest.of(0, 10);
		int start = (int) pageable.getOffset();
		int end = (int) ((start + pageable.getPageSize()) > genres.size() ? genres.size()
				: (start + pageable.getPageSize()));
		Page<Genre> page = new PageImpl<Genre>(genres.subList(start, end), pageable, genres.size());
		Page<GenreDto> pageDto = new PageImpl<GenreDto>(genresDto.subList(start, end), pageable, genresDto.size());

		when(genreRepository.findAll(pageable)).thenReturn(page);
		when(genreDtoService.ChangeGenreToDto(genre1)).thenReturn(genreDto1);
		when(genreDtoService.ChangeGenreToDto(genre2)).thenReturn(genreDto2);
		assertEquals(pageDto, genreService.listGenres(pageable));

	}

	@Test
	@Order(2)
	public void test_createGenre() {
		genreDto1 = new GenreDto("Romantic");
		Genre genre1 = new Genre("Romantic");
		when(genreDtoService.ChangeDtoToGenre(genreDto1)).thenReturn(genre1);
		List<Genre> genres = new ArrayList<Genre>();
		when(genreRepository.findByName(genre1.getName())).thenReturn(genres);
		when(genreRepository.save(genre1)).thenReturn(genre1);
		String expected = "Genre created with name " + genre1.getName();
		assertEquals(expected, genreService.createGenre(genreDto1));
	}

	@Test
	@Order(3)
	public void test_createGenreExists() {
		genreDto1 = new GenreDto("Romantic");
		Genre genre1 = new Genre("Romantic");
		when(genreDtoService.ChangeDtoToGenre(genreDto1)).thenReturn(genre1);
		List<Genre> genres = new ArrayList<Genre>();
		genres.add(genre1);
		when(genreRepository.findByName(genre1.getName())).thenReturn(genres);
		when(genreRepository.save(genre1)).thenReturn(genre1);
		String expected = "This genre already exists";
		assertEquals(expected, genreService.createGenre(genreDto1));
	}

	@Test
	@Order(4)
	public void test_updateGenre() {
		Genre genre1 = new Genre(1, "Romantic");
		GenreDto genreDto1 = new GenreDto("Romantic");
		List<Genre> genres = new ArrayList<Genre>();
		int id = 1;
		when(genreRepository.findById(id)).thenReturn(Optional.of(genre1));
		when(genreRepository.findByName(genreDto1.getName())).thenReturn(genres);
		String expected = "Updated genre success";
		assertEquals(expected, genreService.updateGenre(id, genreDto1));
	}

	@Test
	@Order(4)
	public void test_updateGenreRepeat() {
		Genre genre1 = new Genre(1, "Romantic");
		GenreDto genreDto1 = new GenreDto("Romantic");
		List<Genre> genres = new ArrayList<Genre>();
		genres.add(genre1);
		int id = 1;
		when(genreRepository.findById(id)).thenReturn(Optional.of(genre1));
		when(genreRepository.findByName(genreDto1.getName())).thenReturn(genres);
		String expected = "This genre already exists";
		assertEquals(expected, genreService.updateGenre(id, genreDto1));
	}

	@Test
	@Order(5)
	public void test_updateGenreNotExixsts() {
		GenreDto genreDto1 = new GenreDto("Romantic");
		int id = 1;
		when(genreRepository.findById(id)).thenReturn(Optional.empty());
		String expected = "Genre not found";
		assertEquals(expected, genreService.updateGenre(id, genreDto1));
	}

	@Test
	@Order(6)
	public void test_deleteGenre() {
		int id = 1;
		genreService.deleteGenre(id);
		verify(genreRepository, times(1)).deleteById(id);
	}

	@Test
	@Order(7)
	public void test_findGenre() {
		Genre genre1 = new Genre(1, "Romantic");
		GenreDto genreDto1 = new GenreDto("Romantic");
		int id = 1;
		when(genreRepository.findById(id)).thenReturn(Optional.of(genre1));
		when(genreDtoService.ChangeGenreToDto(genre1)).thenReturn(genreDto1);
		assertEquals(genreDto1, genreService.findGenre(id));
	}

	@Test
	@Order(8)
	public void test_findGenreNotFound() {
		int id = 1;
		when(genreRepository.findById(id)).thenReturn(Optional.empty());

		GenreDto response = genreService.findGenre(id);
		assertEquals(null, response.getName());
	}

}
