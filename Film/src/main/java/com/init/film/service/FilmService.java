package com.init.film.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

@Service
@Transactional
public class FilmService {

	@Autowired
	private ActorRepository characterRepository;

	@Autowired
	private DirectorRepository directorRepository;

	@Autowired
	private FilmRepository filmRepository;

	@Autowired
	private GenreRepository genreRepository;

	@Autowired
	private FilmDtoService filmDtoService;

	/**
	 * This method list movies with pagination
	 * 
	 * @Param pageable
	 * @return Page<FilmDto>
	 */
	public Page<FilmDto> listFilms(Pageable pageable) {
		Page<Film> entities = filmRepository.findAll(pageable);
		Page<FilmDto> dtoPage = entities.map(new Function<Film, FilmDto>() {

			@Override
			public FilmDto apply(Film film) {

				return filmDtoService.ChangeFilmToDto(film);
			}
		});
		return dtoPage;
	}

	/**
	 * This method create movies
	 * 
	 * @Param filmDto
	 * @return FilmDto
	 */
	public FilmDto createFilm(FilmDto filmDto) {
		List<Film> films = filmRepository.findByName(filmDto.getName());
		if (films.isEmpty()) {

			Film newFilm = new Film();
			newFilm.setName(filmDto.getName());
			newFilm.setYear(filmDto.getYear());
			newFilm.setOverview(filmDto.getOverview());

			if (filmDto.getGenre() != null) {
				List<Genre> genres = genreRepository.findByName(filmDto.getGenre().getName());
				if (genres.isEmpty()) {

					genreRepository.save(filmDto.getGenre());
					newFilm.setGenre(filmDto.getGenre());
				} else
					newFilm.setGenre(genres.get(0));
			}

			if (filmDto.getDirector() != null) {

				List<Director> directors = directorRepository.findByName(filmDto.getDirector().getName());
				if (directors.isEmpty()) {

					directorRepository.save(filmDto.getDirector());
					newFilm.setDirector(filmDto.getDirector());
				} else {
					newFilm.setDirector(directors.get(0));
				}

			}

			if (filmDto.getActors() != null) {
				for (Actor actor : filmDto.getActors()) {
					List<Actor> actors = characterRepository.findByName(actor.getName());
					if (actors.isEmpty()) {

						characterRepository.save(actor);
						newFilm.addActor(actor);
					} else {
						newFilm.addActor(actors.get(0));
					}
				}
			}

			filmRepository.save(newFilm);
			//System.out.println(newFilm.getName() + " 1");
			FilmDto newFilmDto = filmDtoService.ChangeFilmToDto(newFilm);
			//System.out.println(newFilmDto.getName() + " 2");
			return newFilmDto;
		} else {
			FilmDto newFilmDtoRepit = new FilmDto();
			return newFilmDtoRepit;
		}
	}

	/**
	 * This method update movies
	 * 
	 * @Param id, flmDto
	 * @return String
	 */
	public String updateFilm(int id, FilmDto filmDto) {
		Optional<Film> filmOptional = filmRepository.findById(id);
		if (filmOptional.isPresent()) {

			Film filmUpdate = filmOptional.get();
			filmUpdate.setFilm_id(id);
			List<Film> films = filmRepository.findByName(filmDto.getName());
			if (films.isEmpty() || films.get(0).getFilm_id() == id) {
				filmUpdate.setName(filmDto.getName());
				filmUpdate.setActors(filmDto.getActors());
				filmUpdate.setDirector(filmDto.getDirector());
				filmUpdate.setGenre(filmDto.getGenre());
				filmUpdate.setOverview(filmDto.getOverview());
				filmUpdate.setYear(filmDto.getYear());

				filmRepository.save(filmUpdate);

				return "Updated film success";
			} else {
				return "This name already exists";
			}
		} else {
			return "Film not found";
		}
	}

	/**
	 * This method delete movies
	 * 
	 * @Param id
	 * @return void
	 */
	public void deleteFilm(int id) {

		filmRepository.deleteById(id);

	}

	/**
	 * This method filters movies by name with pagination
	 * 
	 * @Param name, pageable
	 * @return Page<String>
	 */
	public Page<String> filterByName(String name, Pageable pageable) {
		Page<Film> films = filmRepository.findByName(name, pageable);
		Page<String> namePage = films.map(new Function<Film, String>() {

			@Override
			public String apply(Film film) {

				return film.getName();
			}

		});

//		List<Film> films = filmRepository.findAll();
//		List<Film> filmSearch = films.stream().filter(film -> film.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
//		List<String> listFilms=filmSearch.stream().map((film->film.getName())).collect(Collectors.toList());

		return namePage;

	}

	/**
	 * This method filters movies by year with pagination
	 * 
	 * @Param year, pageable
	 * @return Page<String>
	 */
	public Page<String> filterByYear(int year, Pageable pageable) {
		Page<Film> films = filmRepository.findByYear(year, pageable);
		Page<String> namePage = films.map(new Function<Film, String>() {

			@Override
			public String apply(Film film) {

				return film.getName();
			}

		});
		return namePage;
//		List<Film> films = filmRepository.findAll();
//		List<Film> filmSearch = films.stream().filter(film -> film.getYear() == year).collect(Collectors.toList());
//		List<String>listFilms=filmSearch.stream().map(film->film.getName()).collect(Collectors.toList()); 
//		return listFilms;
	}

	/**
	 * This method filters movies by genre with pagination
	 * 
	 * @Param genre, pageable
	 * @return Page<String>
	 */
	public Page<String> filterByGenre(String genre, Pageable pageable) {
		Page<Film> films = filmRepository.findByGenreName(genre, pageable);
		Page<String> namePage = films.map(new Function<Film, String>() {

			@Override
			public String apply(Film film) {

				return film.getName();
			}

		});
		return namePage;

//		List<Film> films = filmRepository.findAll();
//		List<Film> filmSearch = films.stream().filter(film -> film.getGenre().getName().equalsIgnoreCase(genre)).collect(Collectors.toList());
//		List<String>listFilms=filmSearch.stream().map(film->film.getName()).collect(Collectors.toList()); 
//		return listFilms;
	}

	/**
	 * This method filters movies by director with pagination
	 * 
	 * @Param director, pageable
	 * @return Page<String>
	 */
	public Page<String> filterByDirector(String director, Pageable pageable) {
		Page<Film> films = filmRepository.findByDirectorName(director, pageable);
		Page<String> namePage = films.map(new Function<Film, String>() {

			@Override
			public String apply(Film film) {

				return film.getName();
			}

		});
		return namePage;

//		List<Film> films = filmRepository.findAll();
//		List<Film> filmSearch = films.stream().filter(film -> film.getDirector().getName().equalsIgnoreCase(director)).collect(Collectors.toList());
//		List<String>listFilms=filmSearch.stream().map(film->film.getName()).collect(Collectors.toList()); 
//		return listFilms;
	}

	/**
	 * This method filters movies by character with pagination
	 * 
	 * @Param character, pageable
	 * @return Page<String>
	 */
	public Page<String> filterByCharacter(String character, Pageable pageable) {
		Page<String> films = filmRepository.filmsByActor(character, pageable);
		return films;

//		List<Film> films = filmRepository.findAll();
//		List<Film> filmSearch = films.stream().filter(film->film.ContainsActor(character)).collect(Collectors.toList());
//		List<String>listFilms=filmSearch.stream().map(film->film.getName()).collect(Collectors.toList()); 
//		return listFilms;
	}

	/**
	 * This method find movies by id
	 * 
	 * @Param id
	 * @return Optional<Film>
	 */
	public Optional<Film> findFilm(int id) {
		Optional<Film> film = filmRepository.findById(id);
		return film;
	}

	/**
	 * This method to add genre to the movie
	 * 
	 * @Param id_film, id_genre
	 * @return String
	 */
	public String addGenre(int id_film, int id_genre) {
		Optional<Film> OptionalFilm = filmRepository.findById(id_film);
		Optional<Genre> OptionalGenre = genreRepository.findById(id_genre);
		if (OptionalFilm.isPresent() && OptionalGenre.isPresent()) {
			Film film = OptionalFilm.get();
			Genre genre = OptionalGenre.get();
			film.setGenre(genre);
			filmRepository.save(film);
			return "Added successfully";
		}
		return "Imposible to add";
	}

	/**
	 * This method to add director to the movie
	 * 
	 * @Param id_film, id_director
	 * @return String
	 */
	public String addDirector(int id_film, int id_director) {
		Optional<Film> OptionalFilm = filmRepository.findById(id_film);
		Optional<Director> OptionalDirector = directorRepository.findById(id_director);
		if (OptionalFilm.isPresent() && OptionalDirector.isPresent()) {
			Film film = OptionalFilm.get();
			Director director = OptionalDirector.get();
			film.setDirector(director);
			filmRepository.save(film);
			return "Added successfully";
		}
		return "Imposible to add";
	}

	/**
	 * This method to add actor to the movie
	 * 
	 * @Param id_film, id_actor
	 * @return String
	 */
	public String addActor(int id_film, int id_actor) {
		Optional<Film> OptionalFilm = filmRepository.findById(id_film);
		Optional<Actor> OptionalActor = characterRepository.findById(id_actor);
		if (OptionalFilm.isPresent() && OptionalActor.isPresent()) {
			Film film = OptionalFilm.get();
			Actor actor = OptionalActor.get();
			film.addActor(actor);
			filmRepository.save(film);
			return "Added successfully";
		}
		return "Imposible to add";
	}

}
