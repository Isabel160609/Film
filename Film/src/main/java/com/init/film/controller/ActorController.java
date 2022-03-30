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

import com.init.film.dto.ActorDto;
import com.init.film.entity.Actor;
import com.init.film.service.ActorService;

@RestController
@RequestMapping("/Film/character")
public class ActorController {

	@Autowired
	private ActorService actorService;

	/**
	 * This method list actors with pagination
	 * 
	 * @RequestParam page,size,order,asc
	 * @return ResponseEntity<Page<ActorDto>>
	 */
	@GetMapping(value = "/list")
	public ResponseEntity<Page<ActorDto>> listActors(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "name") String order,
			@RequestParam(defaultValue = "true") boolean asc) {
		try {
			Page<ActorDto> actors = (Page<ActorDto>) actorService
					.listActors(PageRequest.of(page, size, Sort.by(order)));
			if (!asc) {
				actors = (Page<ActorDto>) actorService
						.listActors(PageRequest.of(page, size, Sort.by(order).descending()));
			}

			return new ResponseEntity<Page<ActorDto>>(actors, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	/**
	 * This method create actors
	 * 
	 * @RequestBody actorDto
	 * @return ResponseEntity<String>
	 */
	@PostMapping(value = "/create")
	public ResponseEntity<String> createActor(@RequestBody ActorDto actorDto) {

		try {
			String actorCreate = actorService.createActor(actorDto);
			return new ResponseEntity<String>(actorCreate, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	/**
	 * This method update actors
	 * 
	 * @RequestBody actorDto
	 * @PathVariable id
	 * @return ResponseEntity<String>
	 */
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<String> updateActor(@PathVariable int id, @RequestBody ActorDto actorDto) {

		try {
			String message = actorService.updateActor(id, actorDto);
			if (message.equals("Updated actor success")) {

				return new ResponseEntity<>(message, HttpStatus.FOUND);
			} else {
				return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	/**
	 * This method delete actors
	 * 
	 * @PathVariable id
	 * @return ResponseEntity<String>
	 */
	@Transactional
	@DeleteMapping(value = "delete/{id}")
	public ResponseEntity<String> deleteActor(@PathVariable(name = "id") int id) {
		Optional<Actor> optionalActor = actorService.findActor(id);
		if (optionalActor.isPresent()) {
			actorService.deleteActor(id);
			return new ResponseEntity<String>("Actor deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Actor not found", HttpStatus.NOT_FOUND);
		}
	}
}
