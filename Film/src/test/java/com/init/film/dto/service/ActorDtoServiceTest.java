package com.init.film.dto.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.init.film.dto.ActorDto;
import com.init.film.entity.Actor;

@SpringBootTest(classes = ActorDtoService.class)
class ActorDtoServiceTest {

	@Autowired
	public ActorDtoService actorDtoService;

	@Test
	void ChangeActorToDtoTest() {
		Actor actor = new Actor(1, "romantic");
		ActorDto actorDto = new ActorDto("romantic");
		ActorDto expected = actorDtoService.ChangeActorToDto(actor);

		assertEquals(actorDto.getName(), expected.getName());
	}

	@Test
	void ChangeDtoToActorTest() {
		Actor actor = new Actor(1, "romantic");
		ActorDto actorDto = new ActorDto("romantic");
		Actor expected = actorDtoService.ChangeDtoToActor(actorDto);

		assertEquals(actor.getName(), expected.getName());
	}
}
