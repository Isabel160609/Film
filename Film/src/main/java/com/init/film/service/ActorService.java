package com.init.film.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.init.film.dto.ActorDto;
import com.init.film.dto.service.ActorDtoService;
import com.init.film.entity.Actor;
import com.init.film.repository.ActorRepository;

@Service
public class ActorService {

	@Autowired
	private ActorRepository actorRepository;

	@Autowired
	private ActorDtoService actorDtoService;

	/**
	 * This method list actors with pagination
	 * 
	 * @Param pageable
	 * @return Page<ActorDto>
	 */
	public Page<ActorDto> listActors(Pageable pageable) {
		Page<Actor> entities = actorRepository.findAll(pageable);
		Page<ActorDto> dtoPage = entities.map(new Function<Actor, ActorDto>() {

			@Override
			public ActorDto apply(Actor actor) {

				return actorDtoService.ChangeActorToDto(actor);
			}
		});
		return dtoPage;
	}

	/**
	 * This method create actors
	 * 
	 * @Param actorDto
	 * @return String
	 */
	public String createActor(ActorDto actorDto) throws Exception {
		String response;
		List<Actor> actors = actorRepository.findByName(actorDto.getName());
		if (actors.isEmpty()) {
			Actor actor = actorDtoService.ChangeDtoToActor(actorDto);
			Actor newActor = actorRepository.save(actor);
			response = "Character created with name " + newActor.getName();

		} else {
			response = "This character already exists";

		}
		return response;
	}

	/**
	 * This method update actors
	 * 
	 * @Param id, actorDto
	 * @return String
	 */
	public String updateActor(int id, ActorDto actorDto) {

		Optional<Actor> actorOptional = actorRepository.findById(id);
		if (actorOptional.isPresent()) {
			Actor actorUpdate = actorOptional.get();
			actorUpdate.setActor_id(id);
			List<Actor> actors = actorRepository.findByName(actorDto.getName());
			if (actors.isEmpty()) {
				actorUpdate.setName(actorDto.getName());
				actorUpdate.setFilms(actorDto.getFilms());

				actorRepository.save(actorUpdate);
				return "Updated actor success";
			} else {
				return "This character already exists";
			}
		} else {
			return "Actor not found";
		}

	}

	/**
	 * This method delete actors
	 * 
	 * @Param id
	 * @return void
	 */
	public void deleteActor(int id) {

		actorRepository.deleteById(id);

	}

	/**
	 * This method find actors by id
	 * 
	 * @Param id
	 * @return Optional<Actor>
	 */
	public Optional<Actor> findActor(int id) {
		Optional<Actor> actor = actorRepository.findById(id);
		return actor;
	}
}
