package com.init.film.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.init.film.dto.ActorDto;
import com.init.film.entity.Actor;
import com.init.film.service.ActorService;

@TestMethodOrder(OrderAnnotation.class)
@ComponentScan(basePackages = "com.init.film")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = { ActorControllerTest.class })
class ActorControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Mock
	ActorService actorService;

	@InjectMocks
	ActorController actorController;

	@BeforeEach
	public void setUp() {

		mockMvc = MockMvcBuilders.standaloneSetup(actorController).build();
	}

	@Test
	@Order(1)
	public void test_listActors() throws Exception {
		ActorDto actorDto1 = new ActorDto("Antonio Banderas");
		ActorDto actorDto2 = new ActorDto("Penelope Cruz");

		List<ActorDto> actorsDto = new ArrayList<ActorDto>();
		actorsDto.add(actorDto1);
		actorsDto.add(actorDto2);
		PageRequest pageable = PageRequest.of(1, 10);
		Page<ActorDto> actorDtopage = new PageImpl<>(actorsDto, pageable, actorsDto.size());

		when(actorService.listActors(Mockito.any(PageRequest.class))).thenReturn(actorDtopage);

		this.mockMvc.perform(get("/Film/character/list")).andExpect(status().isOk()).andDo(print());
	}

	@Test
	@Order(2)
	public void test_createActor() throws Exception {
		ActorDto actorDto1 = new ActorDto("Penelope Cruz");
		String response = "Character created with name " + actorDto1.getName();

		ObjectMapper mapper = new ObjectMapper();
		String jsonBody = mapper.writeValueAsString(actorDto1);
		when(actorService.createActor(actorDto1)).thenReturn(response);

		this.mockMvc.perform(post("/Film/character/create").content(jsonBody).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());

	}

//	@Test
//	@Order(3)
//	public void test_updateActor() throws Exception {
//		ActorDto actorDto1 = new ActorDto("Penelope Cruz");
//		String response = "Updated actor success";
//		int id = 1;
//		ObjectMapper mapper = new ObjectMapper();
//		String jsonBody = mapper.writeValueAsString(actorDto1);
//
//		when(actorService.updateActor(id, actorDto1)).thenReturn(response);
//		this.mockMvc
//				.perform(put("/Film/character/update/{id}", id).content(jsonBody).contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isFound()).andDo(print());
//
//	}

	@Test
	@Order(4)
	public void test_deleteActor() throws Exception {
		int id = 1;
		Actor actor1 = new Actor("romantic");
		Optional<Actor> actorOptional = Optional.of(actor1);
		when(actorService.findActor(id)).thenReturn(actorOptional);
		this.mockMvc.perform(delete("/Film/character/delete/{id}", id)).andExpect(status().isOk()).andDo(print());

	}

}
