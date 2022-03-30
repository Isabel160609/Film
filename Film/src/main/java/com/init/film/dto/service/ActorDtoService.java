package com.init.film.dto.service;

import org.springframework.stereotype.Service;

import com.init.film.dto.ActorDto;
import com.init.film.entity.Actor;

@Service
public class ActorDtoService {

	/**
	 * This method convert Actor to ActorDto
	 * 
	 * @Param actor
	 * @return ActorDto
	 */
	public ActorDto ChangeActorToDto(Actor actor) {

		ActorDto actorDto = new ActorDto();
		actorDto.setName(actor.getName());
		actorDto.setFilms(actor.getFilms());
		return actorDto;

	}

	/**
	 * This method convert ActorDto to Actor
	 * 
	 * @Param actorDto
	 * @return Actor
	 */
	public Actor ChangeDtoToActor(ActorDto actorDto) {

		Actor actor = new Actor();
		actor.setName(actorDto.getName());
		actor.setFilms(actorDto.getFilms());
		return actor;

	}
}
