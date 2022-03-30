package com.init.film.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import com.init.film.dto.GenreDto;
import com.init.film.service.GenreService;

@TestMethodOrder(OrderAnnotation.class)
@ComponentScan(basePackages = "com.init.film")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = { GenreControllerTest.class })
class GenreControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Mock
	GenreService genreService;

	@InjectMocks
	GenreController genreController;

	@BeforeEach
	public void setUp() {

		mockMvc = MockMvcBuilders.standaloneSetup(genreController).build();
	}

	@Test
	@Order(1)
	public void test_listGenres() throws Exception {
		GenreDto genreDto1 = new GenreDto("romantic");
		GenreDto genreDto2 = new GenreDto("action");

		List<GenreDto> genresDto = new ArrayList<GenreDto>();
		genresDto.add(genreDto1);
		genresDto.add(genreDto2);
		PageRequest pageable = PageRequest.of(1, 10);
		Page<GenreDto> genreDtopage = new PageImpl<>(genresDto, pageable, genresDto.size());

		when(genreService.listGenres(Mockito.any(PageRequest.class))).thenReturn(genreDtopage);

		this.mockMvc.perform(get("/Film/genre/list")).andExpect(status().isOk()).andDo(print());
	}

	@Test
	@Order(2)
	public void test_createGenre() throws Exception {
		GenreDto genreDto1 = new GenreDto("romantic");
		String response = "Genre created with name " + genreDto1.getName();

		ObjectMapper mapper = new ObjectMapper();
		String jsonBody = mapper.writeValueAsString(genreDto1);
		when(genreService.createGenre(genreDto1)).thenReturn(response);

		this.mockMvc.perform(post("/Film/genre/create").content(jsonBody).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());

	}

	@Test
	@Order(3)
	public void test_updateGenre() throws Exception {
		GenreDto genreDto1 = new GenreDto("romantic");
		String response = "Updated genre success";
		int id = 1;
		ObjectMapper mapper = new ObjectMapper();
		String jsonBody = mapper.writeValueAsString(genreDto1);

		when(genreService.updateGenre(id, genreDto1)).thenReturn(response);
		this.mockMvc
				.perform(put("/Film/genre/update/{id}", id).content(jsonBody).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());

	}

	@Test
	@Order(4)
	public void test_deleteGenre() throws Exception {
		int id = 1;
		GenreDto genreDto1 = new GenreDto("romantic");
		when(genreService.findGenre(id)).thenReturn(genreDto1);
		this.mockMvc.perform(delete("/Film/genre/delete/{id}", id)).andExpect(status().isOk()).andDo(print());

	}
}

