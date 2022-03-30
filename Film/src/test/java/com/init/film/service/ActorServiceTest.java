package com.init.film.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.init.film.dto.ActorDto;
import com.init.film.dto.service.ActorDtoService;
import com.init.film.entity.Actor;
import com.init.film.repository.ActorRepository;

@SpringBootTest(classes = ActorServiceTest.class)
@TestMethodOrder(OrderAnnotation.class)
class ActorServiceTest {

	@Mock
	public ActorDtoService actorDtoService;

	@Mock
	private ActorRepository actorRepository;

	@InjectMocks
	private ActorService actorService;

	@Test
	@Order(1)
	public void test_listActors() {
		ActorDto actorDto1 = new ActorDto("Penelope Cruz");
		ActorDto actorDto2 = new ActorDto("Antonio Banderas");
		Actor actor1 = new Actor("Penelope Cruz");
		Actor actor2 = new Actor("Antonio Banderas");
		List<Actor> actors = new ArrayList<Actor>();
		actors.add(actor1);
		actors.add(actor2);
		List<ActorDto> genresDto = new ArrayList<ActorDto>();
		genresDto.add(actorDto1);
		genresDto.add(actorDto2);
		Pageable pageable = PageRequest.of(0, 10);
		int start = (int) pageable.getOffset();
		int end = (int) ((start + pageable.getPageSize()) > actors.size() ? actors.size()
				: (start + pageable.getPageSize()));
		Page<Actor> page = new PageImpl<Actor>(actors.subList(start, end), pageable, actors.size());
		Page<ActorDto> pageDto = new PageImpl<ActorDto>(genresDto.subList(start, end), pageable, genresDto.size());

		when(actorRepository.findAll(pageable)).thenReturn(page);
		when(actorDtoService.ChangeActorToDto(actor1)).thenReturn(actorDto1);
		when(actorDtoService.ChangeActorToDto(actor2)).thenReturn(actorDto2);
		assertEquals(pageDto, actorService.listActors(pageable));
	}

	@Test
	@Order(2)
	public void test_createActor() throws Exception {
		ActorDto actorDto = new ActorDto("Penelope Cruz");
		Actor actor = new Actor("Penelope Cruz");
		List<Actor> actors = new ArrayList<Actor>();
		when(actorRepository.findByName(actorDto.getName())).thenReturn(actors);
		when(actorDtoService.ChangeDtoToActor(actorDto)).thenReturn(actor);
		when(actorRepository.save(actor)).thenReturn(actor);
		String expected = "Character created with name " + actor.getName();
		assertEquals(expected, actorService.createActor(actorDto));
	}

	@Test
	@Order(3)
	public void test_createActorExist() throws Exception {
		ActorDto actorDto = new ActorDto("Penelope Cruz");
		Actor actor = new Actor("Penelope Cruz");
		List<Actor> actors = new ArrayList<Actor>();
		actors.add(actor);
		when(actorRepository.findByName(actorDto.getName())).thenReturn(actors);
		when(actorDtoService.ChangeDtoToActor(actorDto)).thenReturn(actor);
		when(actorRepository.save(actor)).thenReturn(actor);
		String expected = "This character already exists";
		assertEquals(expected, actorService.createActor(actorDto));
	}

	@Test
	@Order(4)
	public void test_updateActor() {
		ActorDto actorDto = new ActorDto("Penelope Cruz");
		int id = 1;
		Actor actor = new Actor("Penelope Cruz");
		List<Actor> actors = new ArrayList<Actor>();
		when(actorRepository.findById(id)).thenReturn(Optional.of(actor));
		when(actorRepository.findByName(actorDto.getName())).thenReturn(actors);
		String expected = "Updated actor success";
		assertEquals(expected, actorService.updateActor(id, actorDto));
	}

	@Test
	@Order(4)
	public void test_updateActorRepeat() {
		ActorDto actorDto = new ActorDto("Penelope Cruz");
		int id = 1;
		Actor actor = new Actor("Penelope Cruz");
		List<Actor> actors = new ArrayList<Actor>();
		actors.add(actor);
		when(actorRepository.findById(id)).thenReturn(Optional.of(actor));
		when(actorRepository.findByName(actorDto.getName())).thenReturn(actors);
		String expected = "This character already exists";
		assertEquals(expected, actorService.updateActor(id, actorDto));
	}
	@Test
	@Order(5)
	public void test_updateActorNotFound() {
		ActorDto actorDto = new ActorDto("Penelope Cruz");
		int id = 1;
		when(actorRepository.findById(id)).thenReturn(Optional.empty());
		String expected = "Actor not found";
		assertEquals(expected, actorService.updateActor(id, actorDto));
	}

	@Test
	@Order(6)
	public void test_deleteActor() {
		int id = 1;
		actorService.deleteActor(id);
		verify(actorRepository, times(1)).deleteById(id);
	}

	@Test
	@Order(7)
	public void test_findActor() {
		Actor actor1 = new Actor(1, "Penelope Cruz");
		ActorDto actorDto1 = new ActorDto("Penelope Cruz");
		int id = 1;
		when(actorRepository.findById(id)).thenReturn(Optional.of(actor1));
		when(actorDtoService.ChangeActorToDto(actor1)).thenReturn(actorDto1);
		assertEquals(actorDto1.getName(), actorService.findActor(id).get().getName());
	}

	@Test
	@Order(8)
	public void test_findActorNotFound() {
		int id = 1;
		when(actorRepository.findById(id)).thenReturn(Optional.empty());

		Optional<Actor> response = actorService.findActor(id);
		assertEquals(true, response.isEmpty());
	}
}
