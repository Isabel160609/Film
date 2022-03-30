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
//		Genre genre1 = new Genre("romantic");
//		Genre genre2 = new Genre("action");
//		List<Genre> genres = new ArrayList<Genre>();
//		genres.add(genre1);
//		genres.add(genre2);
		List<GenreDto> genresDto = new ArrayList<GenreDto>();
		genresDto.add(genreDto1);
		genresDto.add(genreDto2);
		Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));
		int start = (int) pageable.getOffset();
		int end = (int) ((start + pageable.getPageSize()) > genresDto.size() ? genresDto.size()
				: (start + pageable.getPageSize()));
		//Page<Genre> pageP = new PageImpl<Genre>(genres.subList(start, end), pageable, genres.size());
		Page<GenreDto> pageDto = new PageImpl<GenreDto>(genresDto.subList(start, end), pageable, genresDto.size());
		int page=0;
		String order="name";
		int size=10;
		when(genreService.listGenres(PageRequest.of(page, size, Sort.by(order)))).thenReturn(pageDto);
		
		this.mockMvc.perform(get("/list"))
		.andExpect(status().isOk())
		.andDo(print());
		}
	}


