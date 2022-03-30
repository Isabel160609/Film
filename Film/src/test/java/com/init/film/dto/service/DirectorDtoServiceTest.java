package com.init.film.dto.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.init.film.dto.DirectorDto;
import com.init.film.entity.Director;

@SpringBootTest(classes = DirectorDtoService.class)
class DirectorDtoServiceTest {

	@Autowired
	public DirectorDtoService directorDtoService;

	@Test
	void ChangeGenreToDtoTest() {
		Director director = new Director(1, "Isa");
		DirectorDto directorDto = new DirectorDto("Isa");
		DirectorDto expected = directorDtoService.ChangeDirectorToDto(director);

		assertEquals(directorDto.getName(), expected.getName());
	}

	@Test
	void ChangeDtoToDirectorTest() {
		Director director = new Director(1, "Isa");
		DirectorDto directorDto = new DirectorDto("Isa");
		Director expected = directorDtoService.ChangeDtoToDirector(directorDto);

		assertEquals(director.getName(), expected.getName());
	}

}
