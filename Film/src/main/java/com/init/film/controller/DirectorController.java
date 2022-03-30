package com.init.film.controller;

import java.util.Optional;

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

import com.init.film.dto.DirectorDto;
import com.init.film.entity.Director;
import com.init.film.service.DirectorService;

@RestController
@RequestMapping("/Film/director")
public class DirectorController {

	@Autowired
	private DirectorService directorService;

	/**
	 * This method list directors with pagination
	 * 
	 * @RequestParam page,size,order,asc
	 * @return ResponseEntity<Page<DirectorDto>>
	 */
	@GetMapping(value = "/list")
	public ResponseEntity<Page<DirectorDto>> listDirectors(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "name") String order,
			@RequestParam(defaultValue = "true") boolean asc) {
		try {
			Page<DirectorDto> directors = (Page<DirectorDto>) directorService
					.listDirectors(PageRequest.of(page, size, Sort.by(order)));

			if (!asc) {
				directors = (Page<DirectorDto>) directorService
						.listDirectors(PageRequest.of(page, size, Sort.by(order).descending()));

			}
			return new ResponseEntity<Page<DirectorDto>>(directors, HttpStatus.FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * This method create directors
	 * 
	 * @RequestBody directorDto
	 * @return ResponseEntity<String>
	 */
	@PostMapping(value = "/create")
	public ResponseEntity<String> createDirector(@RequestBody DirectorDto directorDto) {

		try {
			String directorCreate = directorService.createDirector(directorDto);
			return new ResponseEntity<String>(directorCreate, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	/**
	 * This method update directors
	 * 
	 * @RequestBody directorDto
	 * @PathVariable id
	 * @return ResponseEntity<String>
	 */
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<String> updateDirector(@PathVariable int id, @RequestBody DirectorDto directorDto) {

		try {
			String message = directorService.updateDirector(id, directorDto);
			if (message.equals("Updated director success")) {

				return new ResponseEntity<>(message, HttpStatus.FOUND);
			} else {
				return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	/**
	 * This method delete directors
	 * 
	 * @PathVariable id
	 * @return ResponseEntity<String>
	 */
	@Transactional
	@DeleteMapping(value = "delete/{id}")
	public ResponseEntity<String> deleteDirector(@PathVariable(name = "id") int id) {
		Optional<Director> optionalDirector = directorService.findDirector(id);
		if (optionalDirector.isPresent()) {
			directorService.deleteDirector(id);
			return new ResponseEntity<String>("Director deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Director not found", HttpStatus.OK);
		}
	}
}
