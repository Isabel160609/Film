package com.init.film.controller;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;

import com.init.film.dto.FilmDto;
import com.init.film.entity.Film;
import com.init.film.service.FilmService;

@RestController
@RequestMapping("/Film/film")
@CrossOrigin(origins = "*") // puede acceder desde cualquier origen
public class FilmController {

	@Autowired
	private FilmService filmService;

	/**
	 * This method tests the connection
	 * 
	 * @Param
	 * @return String
	 */
	@GetMapping(value = "/test")
	public String test() {
		return "conectado";
	}

	/**
	 * This method list movies with pagination
	 * 
	 * @RequestParam page,size,order,asc
	 * @return ResponseEntity<Page<FilmDto>>
	 */
	@GetMapping(value = "/list")
	public ResponseEntity<Page<FilmDto>> listFilms(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "name") String order,
			@RequestParam(defaultValue = "true") boolean asc) {
		try {
			Page<FilmDto> films = (Page<FilmDto>) filmService.listFilms(PageRequest.of(page, size, Sort.by(order)));

			if (!asc) {
				films = (Page<FilmDto>) filmService.listFilms(PageRequest.of(page, size, Sort.by(order).descending()));
			}
			return new ResponseEntity<Page<FilmDto>>(films, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	/**
	 * This method create movies
	 * 
	 * @RequestBody filmDto
	 * @return ResponseEntity<?>
	 */
	@PostMapping(value = "/create")
	public ResponseEntity<?> createFilm(@RequestBody FilmDto filmDto) {

		ResponseEntity<?> result = null;
		try {
			FilmDto filmCreated = filmService.createFilm(filmDto);
			if (filmCreated.getName() == null) {
				String response = "This Film already Exist";
				result = new ResponseEntity<String>(response, HttpStatus.OK);
			} else {
				result = new ResponseEntity<FilmDto>(filmCreated, HttpStatus.CREATED);
			}
		} catch (Exception ex) {
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	/**
	 * This method update movies
	 * 
	 * @RequestBody filmDto
	 * @PathVariable id
	 * @return ResponseEntity<String>
	 */
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<String> updateFilm(@PathVariable int id, @RequestBody FilmDto filmDto) {

		try {
			String message = filmService.updateFilm(id, filmDto);
			if (message.equals("Updated film success")) {

				return new ResponseEntity<>(message, HttpStatus.FOUND);
			} else {
				return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	/**
	 * This method delete movies
	 * 
	 * @PathVariable id
	 * @return ResponseEntity<String>
	 */
	@Transactional
	@DeleteMapping(value = "delete/{id}")
	public ResponseEntity<String> deleteFilm(@PathVariable(name = "id") int id) {
		Optional<Film> optionalFilm = filmService.findFilm(id);
		if (optionalFilm.isPresent()) {
			filmService.deleteFilm(id);
			return new ResponseEntity<String>("Film deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Film not found", HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * This method filters movies by name with pagination
	 * 
	 * @PathVariable name
	 * @RequestParam page,size,order,asc
	 * @return ResponseEntity<?>
	 */
	@GetMapping(value = "/filterByName/{name}")
	public ResponseEntity<?> filterByName(@PathVariable String name, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "name") String order,
			@RequestParam(defaultValue = "true") boolean asc) {
		ResponseEntity<?> result = null;
		try {
			Page<String> films = filmService.filterByName(name, PageRequest.of(page, size, Sort.by(order)));
			if (!asc) {
				films = filmService.filterByName(name, PageRequest.of(page, size, Sort.by(order).descending()));
			}
			if (films.isEmpty()) {
				String response = "There is no movie with this title";
				result = new ResponseEntity<String>(response, HttpStatus.OK);
			} else {
				result = new ResponseEntity<Page<String>>(films, HttpStatus.OK);
			}
		} catch (Exception ex) {
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return result;
	}

	/**
	 * This method filters movies by year with pagination
	 * 
	 * @PathVariable year
	 * @RequestParam page,size,order,asc
	 * @return ResponseEntity<?>
	 */
	@GetMapping(value = "/filterByYear/{year}")
	public ResponseEntity<?> filterByYear(@PathVariable int year, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "name") String order,
			@RequestParam(defaultValue = "true") boolean asc) {
		ResponseEntity<?> result = null;
		try {
			Page<String> films = filmService.filterByYear(year, PageRequest.of(page, size, Sort.by(order)));
			if (!asc) {
				films = filmService.filterByYear(year, PageRequest.of(page, size, Sort.by(order).descending()));
			}
			if (films.isEmpty()) {
				String response = "There are no movies in this year";
				result = new ResponseEntity<String>(response, HttpStatus.OK);
			} else {
				result = new ResponseEntity<Page<String>>(films, HttpStatus.OK);
			}
		} catch (Exception ex) {
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return result;
	}

	/**
	 * This method filters movies by genre with pagination
	 * 
	 * @PathVariable genre
	 * @RequestParam page,size,order,asc
	 * @return ResponseEntity<?>
	 */
	@GetMapping(value = "/filterByGenre/{genre}")
	public ResponseEntity<?> filterByGenre(@PathVariable String genre, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "name") String order,
			@RequestParam(defaultValue = "true") boolean asc) {
		ResponseEntity<?> result = null;
		try {
			Page<String> films = filmService.filterByGenre(genre, PageRequest.of(page, size, Sort.by(order)));
			if (!asc) {
				films = filmService.filterByGenre(genre, PageRequest.of(page, size, Sort.by(order).descending()));
			}
			if (films.isEmpty()) {
				String response = "There is no movie of this genre";
				result = new ResponseEntity<String>(response, HttpStatus.OK);
			} else {
				result = new ResponseEntity<Page<String>>(films, HttpStatus.OK);
			}
		} catch (Exception ex) {
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return result;
	}

	/**
	 * This method filters movies by director with pagination
	 * 
	 * @PathVariable director
	 * @RequestParam page,size,order,asc
	 * @return ResponseEntity<?>
	 */
	@GetMapping(value = "/filterByDirector/{director}")
	public ResponseEntity<?> filterByDirector(@PathVariable String director, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "name") String order,
			@RequestParam(defaultValue = "true") boolean asc) {
		ResponseEntity<?> result = null;
		try {
			Page<String> films = filmService.filterByDirector(director, PageRequest.of(page, size, Sort.by(order)));

			if (!asc) {
				films = filmService.filterByDirector(director, PageRequest.of(page, size, Sort.by(order).descending()));
			}
			if (films.isEmpty()) {
				String response = "There is no movie of this director";
				result = new ResponseEntity<String>(response, HttpStatus.OK);
			} else {
				result = new ResponseEntity<Page<String>>(films, HttpStatus.OK);
			}
		} catch (Exception ex) {
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return result;
	}

	/**
	 * This method filters movies by character with pagination
	 * 
	 * @PathVariable character
	 * @RequestParam page,size,order,asc
	 * @return ResponseEntity<?>
	 */
	@GetMapping(value = "/filterByCharacter/{character}")
	public ResponseEntity<?> filterByCharacter(@PathVariable String character,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "name") String order, @RequestParam(defaultValue = "true") boolean asc) {
		ResponseEntity<?> result = null;
		try {
			Page<String> films = filmService.filterByCharacter(character, PageRequest.of(page, size, Sort.by(order)));

			if (!asc) {
				films = filmService.filterByCharacter(character,
						PageRequest.of(page, size, Sort.by(order).descending()));
			}

			if (films.isEmpty()) {
				String response = "There is no movie of this actor";
				result = new ResponseEntity<String>(response, HttpStatus.OK);
			} else {
				result = new ResponseEntity<Page<String>>(films, HttpStatus.OK);
			}
		} catch (Exception ex) {
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return result;
	}

	/**
	 * This method to add genre to the movie
	 * 
	 * @PathVariable id_film, id_genre
	 * @return ResponseEntity<String>
	 */
	@PutMapping(value = "/addGenre/{id_film}/{id_genre}")
	public ResponseEntity<String> addGenre(@PathVariable int id_film, @PathVariable int id_genre) {

		try {
			String message = filmService.addGenre(id_film, id_genre);

			return new ResponseEntity<>(message, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	/**
	 * This method to add director to the movie
	 * 
	 * @PathVariable id_film, id_director
	 * @returnResponseEntity<String>
	 */
	@PutMapping(value = "/addDirector/{id_film}/{id_director}")
	public ResponseEntity<String> addDirector(@PathVariable int id_film, @PathVariable int id_director) {

		try {
			String message = filmService.addDirector(id_film, id_director);

			return new ResponseEntity<>(message, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	/**
	 * This method to add actor to the movie
	 * 
	 * @PathVariable id_film, id_actor
	 * @return ResponseEntity<String>
	 */
	@PutMapping(value = "/addActor/{id_film}/{id_actor}")
	public ResponseEntity<String> addActor(@PathVariable int id_film, @PathVariable int id_actor) {

		try {
			String message = filmService.addActor(id_film, id_actor);

			return new ResponseEntity<>(message, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

}
