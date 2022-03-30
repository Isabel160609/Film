package com.init.film.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.init.film.dto.GenreDto;
import com.init.film.entity.Genre;
import com.init.film.service.GenreService;


@TestMethodOrder(OrderAnnotation.class)
@ComponentScan(basePackages="com.init.film")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes= {GenreControllerTest.class})
class GenreControllerTest {
	@Autowired
	MockMvc	mockMvc	;
	
	@Mock
	GenreService  genreService;
	
	@InjectMocks
	GenreController genreController;
	
	@BeforeEach
	public void setUp(){
		
		mockMvc=MockMvcBuilders.standaloneSetup(genreController).build();
	}
	@Test
	@Order(1)
	public void test_listGenres() throws Exception {
		GenreDto genreDto1 = new GenreDto("romantic");
		GenreDto genreDto2 = new GenreDto("action");

		List<GenreDto> genresDto = new ArrayList<GenreDto>();
		genresDto.add(genreDto1);
		genresDto.add(genreDto2);
		PageRequest pageable=PageRequest.of(1, 10);
		Page<GenreDto>genreDtopage=new PageImpl<>(genresDto, pageable, genresDto.size());
		

		when(genreService.listGenres(Mockito.any(PageRequest.class))).thenReturn(genreDtopage);
		
		this.mockMvc.perform(get("/Film/genre/list"))
		.andExpect(status().isOk())
		.andDo(print());
		}
	}


