package com.init.film.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

import com.init.film.dto.FilmDto;
import com.init.film.dto.service.FilmDtoService;
import com.init.film.entity.Actor;
import com.init.film.entity.Director;
import com.init.film.entity.Film;
import com.init.film.entity.Genre;
import com.init.film.repository.ActorRepository;
import com.init.film.repository.DirectorRepository;
import com.init.film.repository.FilmRepository;
import com.init.film.repository.GenreRepository;

@SpringBootTest(classes = FilmServiceTest.class)
@TestMethodOrder(OrderAnnotation.class)
class FilmServiceTest {

	@Mock
	private FilmDtoService filmDtoService;

	@Mock
	private FilmRepository filmRepository;

	@Mock
	private ActorRepository characterRepository;

	@Mock
	private DirectorRepository directorRepository;

	@Mock
	private GenreRepository genreRepository;

	@InjectMocks
	private FilmService filmService;

	@Test
	@Order(1)
	public void test_listFilms() {
		FilmDto filmDto1 = new FilmDto(" Mujeres al borde de un ataque de nervios", 1988, "jvgkj.lñkñ´l");
		FilmDto filmDto2 = new FilmDto("Los lunes al sol", 2001, "jhghkljlñl");
		Film film1 = new Film(" Mujeres al borde de un ataque de nervios", 1988, "jvgkj.lñkñ´l");
		Film film2 = new Film("Los lunes al sol", 2001, "jhghkljlñl");
		List<Film> films = new ArrayList<Film>();
		films.add(film1);
		films.add(film2);
		List<FilmDto> filmsDto = new ArrayList<FilmDto>();
		filmsDto.add(filmDto1);
		filmsDto.add(filmDto2);

		Pageable pageable = PageRequest.of(0, 10);
		int start = (int) pageable.getOffset();
		int end = (int) ((start + pageable.getPageSize()) > films.size() ? films.size()
				: (start + pageable.getPageSize()));
		Page<Film> page = new PageImpl<Film>(films.subList(start, end), pageable, films.size());
		Page<FilmDto> pageDto = new PageImpl<FilmDto>(filmsDto.subList(start, end), pageable, filmsDto.size());

		when(filmRepository.findAll(pageable)).thenReturn(page);
		when(filmDtoService.ChangeFilmToDto(film1)).thenReturn(filmDto1);
		when(filmDtoService.ChangeFilmToDto(film2)).thenReturn(filmDto2);
		assertEquals(pageDto, filmService.listFilms(pageable));

	}

	@Test
	@Order(2)
	public void test_createFilm() {
		Genre genre1 = new Genre(1, "Romantic");
		Director director1 = new Director(1, "Almodovar");
		Actor actor1 = new Actor(1, "Penelope Cruz");
		Set<Actor> actors = new HashSet<Actor>();
		actors.add(actor1);
		FilmDto filmDto = new FilmDto("Mujeres al borde de un ataque de nervios", 1988, genre1, "zhfgzsdxzgzs",
				director1, actors);
		FilmDto filmDtoresposta = new FilmDto("Mujeres al borde de un ataque de nervios", 1988, genre1, "zhfgzsdxzgzs",
				director1, actors);
		Film film = new Film(1, "Mujeres al borde de un ataque de nervios", 1988, genre1, "zhfgzsdxzgzs", director1,
				actors);

		List<Film> films = new ArrayList<Film>();
		List<Genre> genres = new ArrayList<Genre>();
		genres.add(genre1);
		List<Director> directors = new ArrayList<Director>();
		directors.add(director1);
		List<Actor> actorsEmpty = new ArrayList<Actor>();
		actorsEmpty.add(actor1);
		when(filmRepository.findByName(filmDto.getName())).thenReturn(films);
		when(genreRepository.findByName(filmDto.getGenre().getName())).thenReturn(genres);
		// when(genreRepository.save(film.getGenre())).thenReturn(genre1);
		when(directorRepository.findByName(filmDto.getDirector().getName())).thenReturn(directors);
		// when(directorRepository.save(film.getDirector())).thenReturn(director1);
		when(characterRepository.findByName(actor1.getName())).thenReturn(actorsEmpty);
		// when(characterRepository.save(actor1)).thenReturn(actor1);
		// when(filmRepository.save(film)).thenReturn(film);
		// FilmDtoService filmDtoService=new FilmDtoService();
		when(filmDtoService.ChangeFilmToDto(film)).thenReturn(filmDto);

		//System.out.println(filmDtoService.ChangeFilmToDto(film).getName());
		assertEquals(true, films.isEmpty());
		assertEquals(filmDto.getName(), filmDtoService.ChangeFilmToDto(film).getName());
		// assertEquals(filmDto, filmService.createFilm(filmDtoresposta));
		 assertEquals(null, filmService.createFilm(filmDtoresposta));
	}

	@Test
	@Order(2)
	public void test_createFilmRepeat() {
		Genre genre1 = new Genre(1, "Romantic");
		Director director1 = new Director(1, "Almodovar");
		Actor actor1 = new Actor(1, "Penelope Cruz");
		Set<Actor> actors = new HashSet<Actor>();
		actors.add(actor1);
		FilmDto filmDto = new FilmDto(" Mujeres al borde de un ataque de nervios", 1988, genre1, "jvgkj.lñkñ´l",
				director1, actors);
		Film film = new Film(1, " Mujeres al borde de un ataque de nervios", 1988, genre1, "jvgkj.lñkñ´l", director1,
				actors);
		FilmDto filmDtoEmpty = new FilmDto();
		List<Film> films = new ArrayList<Film>();
		films.add(film);

		when(filmRepository.findByName(filmDto.getName())).thenReturn(films);

		assertEquals(filmDtoEmpty.getName(), filmService.createFilm(filmDto).getName());
		assertEquals(false, films.isEmpty());
	}

	@Test
	@Order(4)
	public void test_updateFilm() {
		Film film1 = new Film(1, " Mujeres al borde de un ataque de nervios", 1988, "jvgkj.lñkñ´l");
		FilmDto filmDto1 = new FilmDto(" Mujeres al borde de un ataque de nervios", 1988, "jvgkj.lñkñ´l");
		int id = 1;
		List<Film> films = new ArrayList<Film>();
		when(filmRepository.findById(id)).thenReturn(Optional.of(film1));
		when(filmRepository.findByName(filmDto1.getName())).thenReturn(films);
		String expected = "Updated film success";
		assertEquals(expected, filmService.updateFilm(id, filmDto1));
	}

	@Test
	@Order(4)
	public void test_updateFilmRepeat() {
		Film film1 = new Film(1, " Mujeres al borde de un ataque de nervios", 1988, "jvgkj.lñkñ´l");
		Film film2 = new Film(2, "Los lunes al sol", 2001, "jhghkljlñl");
		FilmDto filmDto1 = new FilmDto(" Mujeres al borde de un ataque de nervios", 1988, "jvgkj.lñkñ´l");
		int id = 1;
		List<Film> films = new ArrayList<Film>();
		films.add(film2);
		when(filmRepository.findById(id)).thenReturn(Optional.of(film1));
		when(filmRepository.findByName(filmDto1.getName())).thenReturn(films);
		String expected = "This name already exists";
		assertEquals(expected, filmService.updateFilm(id, filmDto1));
	}

	@Test
	@Order(5)
	public void test_updateFilmNotExixsts() {
		FilmDto filmDto1 = new FilmDto(" Mujeres al borde de un ataque de nervios", 1988, "jvgkj.lñkñ´l");
		int id = 1;
		when(filmRepository.findById(id)).thenReturn(Optional.empty());
		String expected = "Film not found";
		assertEquals(expected, filmService.updateFilm(id, filmDto1));
	}

	@Test
	@Order(6)
	public void test_deleteFilm() {
		int id = 1;
		filmService.deleteFilm(id);
		verify(filmRepository, times(1)).deleteById(id);
	}

	@Test
	@Order(6)
	public void test_filterByName() {
		Film film1 = new Film(" Mujeres al borde de un ataque de nervios", 1988, "jvgkj.lñkñ´l");
		String name = " Mujeres al borde de un ataque de nervios";
		List<Film> films = new ArrayList<Film>();
		films.add(film1);
		List<String> titles = new ArrayList<String>();
		titles.add(name);
		Pageable pageable = PageRequest.of(0, 10);
		int startF = (int) pageable.getOffset();
		int endF = (int) ((startF + pageable.getPageSize()) > films.size() ? films.size()
				: (startF + pageable.getPageSize()));
		Page<Film> pageF = new PageImpl<Film>(films.subList(startF, endF), pageable, films.size());
		int startS = (int) pageable.getOffset();
		int endS = (int) ((startS + pageable.getPageSize()) > titles.size() ? titles.size()
				: (startF + pageable.getPageSize()));
		Page<String> pageS = new PageImpl<String>(titles.subList(startS, endS), pageable, titles.size());
		when(filmRepository.findByName(name, pageable)).thenReturn(pageF);
		assertEquals(pageS, filmService.filterByName(name, pageable));
	}

	@Test
	@Order(7)
	public void test_filterByYear() {
		Film film1 = new Film(" Mujeres al borde de un ataque de nervios", 1988, "jvgkj.lñkñ´l");
		int year = 1988;
		String name = " Mujeres al borde de un ataque de nervios";
		List<Film> films = new ArrayList<Film>();
		films.add(film1);
		List<String> titles = new ArrayList<String>();
		titles.add(name);
		Pageable pageable = PageRequest.of(0, 10);
		int startF = (int) pageable.getOffset();
		int endF = (int) ((startF + pageable.getPageSize()) > films.size() ? films.size()
				: (startF + pageable.getPageSize()));
		Page<Film> pageF = new PageImpl<Film>(films.subList(startF, endF), pageable, films.size());
		int startS = (int) pageable.getOffset();
		int endS = (int) ((startS + pageable.getPageSize()) > titles.size() ? titles.size()
				: (startF + pageable.getPageSize()));
		Page<String> pageS = new PageImpl<String>(titles.subList(startS, endS), pageable, titles.size());
		when(filmRepository.findByYear(year, pageable)).thenReturn(pageF);
		assertEquals(pageS, filmService.filterByYear(year, pageable));
	}

	@Test
	@Order(8)
	public void test_filterByGenre() {
		Film film1 = new Film(" Mujeres al borde de un ataque de nervios", 1988, "jvgkj.lñkñ´l");
		String genre = "comic";
		String name = " Mujeres al borde de un ataque de nervios";
		List<Film> films = new ArrayList<Film>();
		films.add(film1);
		List<String> titles = new ArrayList<String>();
		titles.add(name);
		Pageable pageable = PageRequest.of(0, 10);
		int startF = (int) pageable.getOffset();
		int endF = (int) ((startF + pageable.getPageSize()) > films.size() ? films.size()
				: (startF + pageable.getPageSize()));
		Page<Film> pageF = new PageImpl<Film>(films.subList(startF, endF), pageable, films.size());
		int startS = (int) pageable.getOffset();
		int endS = (int) ((startS + pageable.getPageSize()) > titles.size() ? titles.size()
				: (startF + pageable.getPageSize()));
		Page<String> pageS = new PageImpl<String>(titles.subList(startS, endS), pageable, titles.size());
		when(filmRepository.findByGenreName(genre, pageable)).thenReturn(pageF);
		assertEquals(pageS, filmService.filterByGenre(genre, pageable));
	}

	@Test
	@Order(9)
	public void test_filterByDirector() {
		Film film1 = new Film(" Mujeres al borde de un ataque de nervios", 1988, "jvgkj.lñkñ´l");
		String name = " Mujeres al borde de un ataque de nervios";
		String director = "Almodovar";
		List<Film> films = new ArrayList<Film>();
		films.add(film1);
		List<String> directors = new ArrayList<String>();
		directors.add(name);
		Pageable pageable = PageRequest.of(0, 10);
		int startF = (int) pageable.getOffset();
		int endF = (int) ((startF + pageable.getPageSize()) > films.size() ? films.size()
				: (startF + pageable.getPageSize()));
		Page<Film> pageF = new PageImpl<Film>(films.subList(startF, endF), pageable, films.size());
		int startS = (int) pageable.getOffset();
		int endS = (int) ((startS + pageable.getPageSize()) > directors.size() ? directors.size()
				: (startS + pageable.getPageSize()));
		Page<String> pageS = new PageImpl<String>(directors.subList(startS, endS), pageable, directors.size());
		when(filmRepository.findByDirectorName(director, pageable)).thenReturn(pageF);
		assertEquals(pageS, filmService.filterByDirector(director, pageable));
	}

	@Test
	@Order(10)
	public void test_filterByCharacter() {

		String actor = "Penelope Cruz";
		List<String> actors = new ArrayList<String>();
		actors.add(actor);
		Pageable pageable = PageRequest.of(0, 10);
		int startS = (int) pageable.getOffset();
		int endS = (int) ((startS + pageable.getPageSize()) > actors.size() ? actors.size()
				: (startS + pageable.getPageSize()));
		Page<String> pageS = new PageImpl<String>(actors.subList(startS, endS), pageable, actors.size());
		when(filmRepository.filmsByActor(actor, pageable)).thenReturn(pageS);
		assertEquals(pageS, filmService.filterByCharacter(actor, pageable));
	}

	@Test
	@Order(11)
	public void test_findFilm() {
		Film film1 = new Film(1, " Mujeres al borde de un ataque de nervios", 1988, "jvgkj.lñkñ´l");
		FilmDto filmDto1 = new FilmDto(" Mujeres al borde de un ataque de nervios", 1988, "jvgkj.lñkñ´l");
		;
		int id = 1;
		when(filmRepository.findById(id)).thenReturn(Optional.of(film1));
		when(filmDtoService.ChangeFilmToDto(film1)).thenReturn(filmDto1);
		assertEquals(filmDto1.getName(), filmService.findFilm(id).get().getName());
	}

	@Test
	@Order(12)
	public void test_findFilmNotFound() {
		int id = 1;
		when(filmRepository.findById(id)).thenReturn(Optional.empty());

		Optional<Film> response = filmService.findFilm(id);
		assertEquals(true, response.isEmpty());
	}

	@Test
	@Order(13)
	public void test_addGenre() {
		int idF = 1;
		int idG = 1;
		Film film1 = new Film(1, " Mujeres al borde de un ataque de nervios", 1988, "jvgkj.lñkñ´l");
		Genre genre1 = new Genre(1, "Romantic");
		when(filmRepository.findById(idF)).thenReturn(Optional.of(film1));
		when(genreRepository.findById(idG)).thenReturn(Optional.of(genre1));
		String expected = "Added successfully";
		assertEquals(expected, filmService.addGenre(idF, idG));
	}

	@Test
	@Order(14)
	public void test_addGenreNotFoundFilm() {
		int idF = 1;
		int idG = 1;
		Genre genre1 = new Genre(1, "Romantic");
		when(filmRepository.findById(idF)).thenReturn(Optional.empty());
		when(genreRepository.findById(idG)).thenReturn(Optional.of(genre1));
		String expected = "Imposible to add";
		assertEquals(expected, filmService.addGenre(idF, idG));
	}

	@Test
	@Order(15)
	public void test_addGenreNotFoundGendre() {
		int idF = 1;
		int idG = 1;
		Film film1 = new Film(1, " Mujeres al borde de un ataque de nervios", 1988, "jvgkj.lñkñ´l");
		when(filmRepository.findById(idF)).thenReturn(Optional.of(film1));
		when(genreRepository.findById(idG)).thenReturn(Optional.empty());
		String expected = "Imposible to add";
		assertEquals(expected, filmService.addGenre(idF, idG));
	}

	@Test
	@Order(16)
	public void test_addDirector() {
		int idF = 1;
		int idD = 1;
		Film film1 = new Film(1, " Mujeres al borde de un ataque de nervios", 1988, "jvgkj.lñkñ´l");
		Director director1 = new Director(1, "Almodovar");
		when(filmRepository.findById(idF)).thenReturn(Optional.of(film1));
		when(directorRepository.findById(idD)).thenReturn(Optional.of(director1));
		String expected = "Added successfully";
		assertEquals(expected, filmService.addDirector(idF, idD));
	}

	@Test
	@Order(17)
	public void test_addDirectorNotFoundFilm() {
		int idF = 1;
		int idD = 1;
		Director director1 = new Director(1, "Almodovar");
		when(filmRepository.findById(idF)).thenReturn(Optional.empty());
		when(directorRepository.findById(idD)).thenReturn(Optional.of(director1));
		String expected = "Imposible to add";
		assertEquals(expected, filmService.addDirector(idF, idD));
	}

	@Test
	@Order(18)
	public void test_addDirectorNotFoundDirector() {
		int idF = 1;
		int idD = 1;
		Film film1 = new Film(1, " Mujeres al borde de un ataque de nervios", 1988, "jvgkj.lñkñ´l");
		when(filmRepository.findById(idF)).thenReturn(Optional.of(film1));
		when(genreRepository.findById(idD)).thenReturn(Optional.empty());
		String expected = "Imposible to add";
		assertEquals(expected, filmService.addDirector(idF, idD));
	}

	@Test
	@Order(19)
	public void test_addActor() {
		int idF = 1;
		int idA = 1;
		Film film1 = new Film(1, " Mujeres al borde de un ataque de nervios", 1988, "jvgkj.lñkñ´l");
		Actor actor1 = new Actor(1, "Penelope Cruz");
		when(filmRepository.findById(idF)).thenReturn(Optional.of(film1));
		when(characterRepository.findById(idA)).thenReturn(Optional.of(actor1));
		String expected = "Added successfully";
		assertEquals(expected, filmService.addActor(idF, idA));
	}

	@Test
	@Order(20)
	public void test_addActorNotFoundFilm() {
		int idF = 1;
		int idA = 1;
		Actor actor1 = new Actor(1, "Penelope Cruz");
		when(filmRepository.findById(idF)).thenReturn(Optional.empty());
		when(characterRepository.findById(idA)).thenReturn(Optional.of(actor1));
		String expected = "Imposible to add";
		assertEquals(expected, filmService.addActor(idF, idA));
	}

	@Test
	@Order(21)
	public void test_addActorNotFoundActor() {
		int idF = 1;
		int idD = 1;
		Film film1 = new Film(1, " Mujeres al borde de un ataque de nervios", 1988, "jvgkj.lñkñ´l");
		when(filmRepository.findById(idF)).thenReturn(Optional.of(film1));
		when(characterRepository.findById(idD)).thenReturn(Optional.empty());
		String expected = "Imposible to add";
		assertEquals(expected, filmService.addActor(idF, idD));
	}

}
