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
import com.init.film.dto.DirectorDto;
import com.init.film.entity.Director;
import com.init.film.service.DirectorService;

@TestMethodOrder(OrderAnnotation.class)
@ComponentScan(basePackages = "com.init.film")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = { DirectorControllerTest.class })
class DirectorControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Mock
	DirectorService directorService;

	@InjectMocks
	DirectorController directorController;

	@BeforeEach
	public void setUp() {

		mockMvc = MockMvcBuilders.standaloneSetup(directorController).build();
	}

	@Test
	@Order(1)
	public void test_listDirectors() throws Exception {
		DirectorDto directorDto1 = new DirectorDto("Bardem");
		DirectorDto directorDto2 = new DirectorDto("Almodovar");

		List<DirectorDto> directorsDto = new ArrayList<DirectorDto>();
		directorsDto.add(directorDto1);
		directorsDto.add(directorDto2);
		PageRequest pageable = PageRequest.of(1, 10);
		Page<DirectorDto> directorDtopage = new PageImpl<>(directorsDto, pageable, directorsDto.size());

		when(directorService.listDirectors(Mockito.any(PageRequest.class))).thenReturn(directorDtopage);

		this.mockMvc.perform(get("/Film/director/list")).andExpect(status().isFound()).andDo(print());
	}

	@Test
	@Order(2)
	public void test_createDirector() throws Exception {
		DirectorDto directorDto1 = new DirectorDto("Bardem");
		String response = "Director created with name " + directorDto1.getName();

		ObjectMapper mapper = new ObjectMapper();
		String jsonBody = mapper.writeValueAsString(directorDto1);
		when(directorService.createDirector(directorDto1)).thenReturn(response);

		this.mockMvc.perform(post("/Film/director/create").content(jsonBody).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());

	}

//	@Test
//	@Order(3)
//	public void test_updateDirector() throws Exception {
//		DirectorDto directorDto = new DirectorDto("Bardem");
//		String response = "Updated director success";
//		int id = 1;
//		ObjectMapper mapper = new ObjectMapper();
//		String jsonBody = mapper.writeValueAsString(directorDto);
//
//		when(directorService.updateDirector(id, directorDto)).thenReturn(response);
//		this.mockMvc
//				.perform(put("/Film/director/update/{id}", id).content(jsonBody).contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isFound()).andDo(print());
//
//	}

	@Test
	@Order(4)
	public void test_deleteDirector() throws Exception {
		int id = 1;
		Director director = new Director("romantic");
		Optional<Director>directionOptional=Optional.of(director );
		when(directorService.findDirector(id)).thenReturn(directionOptional);
		this.mockMvc.perform(delete("/Film/director/delete/{id}", id)).andExpect(status().isOk()).andDo(print());

	}
	
	@Test
	@Order(4)
	public void test_deleteDirectorNotFound() throws Exception  {
		int id = 1;
		Optional<Director>directionOptional=Optional.empty();
		when(directorService.findDirector(id)).thenReturn(directionOptional);
		this.mockMvc.perform(delete("/Film/director/delete/{id}", id)).andExpect(status().isOk()).andDo(print());

	}
}
