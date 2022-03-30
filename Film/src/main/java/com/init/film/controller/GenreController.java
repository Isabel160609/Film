package com.init.film.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.init.film.dto.GenreDto;
import com.init.film.service.GenreService;

@RestController
@RequestMapping("/Film/genre")
public class GenreController {

	@Autowired
	private GenreService genreService;

	/**
	 * This method list genres with pagination
	 * 
	 * @RequestParam page,size,order,asc
	 * @return ResponseEntity<Page<GenrerDto>>
	 */
	@GetMapping(value = "/list")
	public ResponseEntity<Page<GenreDto>> listGenres(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "name") String order,
			@RequestParam(defaultValue = "true") boolean asc) {
		try {
			Page<GenreDto> genres = (Page<GenreDto>) genreService
					.listGenres(PageRequest.of(page, size, Sort.by(order)));

			if (!asc) {
				genres = (Page<GenreDto>) genreService
						.listGenres(PageRequest.of(page, size, Sort.by(order).descending()));
			}
			return new ResponseEntity<Page<GenreDto>>(genres, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	/**
	 * This method create genres
	 * 
	 * @RequestBody genreDto
	 * @return ResponseEntity<String>
	 */
	@PostMapping(value = "/create")
	public ResponseEntity<String> createGenre(@RequestBody GenreDto genreDto) {

		try {
			String genreCreate = genreService.createGenre(genreDto);
			return new ResponseEntity<String>(genreCreate, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	/**
	 * This method update genres
	 * 
	 * @RequestBody genreDto
	 * @PathVariable id
	 * @return ResponseEntity<String>
	 */
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<String> updateGenre(@PathVariable int id, @RequestBody GenreDto genreDto) {

		try {
			String message = genreService.updateGenre(id, genreDto);

			return new ResponseEntity<>(message, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	/**
	 * This method delete genres
	 * 
	 * @PathVariable id
	 * @return ResponseEntity<String>
	 */
	@Transactional
	@DeleteMapping(value = "delete/{id}")
	public ResponseEntity<String> deleteGenre(@PathVariable(name = "id") int id) {
		GenreDto optionalGenre = genreService.findGenre(id);
		if (optionalGenre.getName() != null) {
			genreService.deleteGenre(id);
			return new ResponseEntity<String>("Genre deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Genre not found", HttpStatus.OK);
		}
	}
}
