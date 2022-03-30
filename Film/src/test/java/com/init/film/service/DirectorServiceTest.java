package com.init.film.service;

import static org.junit.jupiter.api.Assertions.*;
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

import com.init.film.dto.DirectorDto;
import com.init.film.dto.service.DirectorDtoService;
import com.init.film.entity.Director;
import com.init.film.repository.DirectorRepository;

@SpringBootTest(classes = DirectorServiceTest.class)
@TestMethodOrder(OrderAnnotation.class)
class DirectorServiceTest {

	@Mock
	public DirectorDtoService directorDtoService;

	@Mock
	private DirectorRepository directorRepository;

	@InjectMocks
	private DirectorService directorService;

	List<DirectorDto> directorsDto;

	DirectorDto directorDto1;
	DirectorDto directorDto2;
	DirectorDto directorDto3;

	@Test
	@Order(1)
	public void test_listDirectors() {
		DirectorDto directorDto1 = new DirectorDto("Almodovar");
		DirectorDto directorDto2 = new DirectorDto("Bardem");
		Director director1 = new Director("Almodovar");
		Director director2 = new Director("Bardem");
		List<Director> directors = new ArrayList<Director>();
		directors.add(director1);
		directors.add(director2);
		List<DirectorDto> directorsDto = new ArrayList<DirectorDto>();
		directorsDto.add(directorDto1);
		directorsDto.add(directorDto2);
		Pageable pageable = PageRequest.of(0, 10);
		int start = (int) pageable.getOffset();
		int end = (int) ((start + pageable.getPageSize()) > directors.size() ? directors.size()
				: (start + pageable.getPageSize()));
		Page<Director> page = new PageImpl<Director>(directors.subList(start, end), pageable, directors.size());
		Page<DirectorDto> pageDto = new PageImpl<DirectorDto>(directorsDto.subList(start, end), pageable,
				directorsDto.size());

		when(directorRepository.findAll(pageable)).thenReturn(page);
		when(directorDtoService.ChangeDirectorToDto(director1)).thenReturn(directorDto1);
		when(directorDtoService.ChangeDirectorToDto(director2)).thenReturn(directorDto2);
		assertEquals(pageDto, directorService.listDirectors(pageable));

	}

	@Test
	@Order(2)
	public void test_createDirector() {
		directorDto1 = new DirectorDto("Almodovar");
		Director director1 = new Director("Almodovar");
		when(directorDtoService.ChangeDtoToDirector(directorDto1)).thenReturn(director1);
		List<Director> directors = new ArrayList<Director>();
		when(directorRepository.findByName(director1.getName())).thenReturn(directors);
		when(directorRepository.save(director1)).thenReturn(director1);
		String expected = "Director created with name " + director1.getName();
		assertEquals(expected, directorService.createDirector(directorDto1));
	}

	@Test
	@Order(3)
	public void test_createDirectorExists() {
		directorDto1 = new DirectorDto("Almodovar");
		Director director1 = new Director("Almodovar");
		when(directorDtoService.ChangeDtoToDirector(directorDto1)).thenReturn(director1);
		List<Director> directors = new ArrayList<Director>();
		directors.add(director1);
		when(directorRepository.findByName(director1.getName())).thenReturn(directors);
		when(directorRepository.save(director1)).thenReturn(director1);
		String expected = "This director already exists";
		assertEquals(expected, directorService.createDirector(directorDto1));
	}

	@Test
	@Order(4)
	public void test_updateDirector() {
		Director director1 = new Director(1, "Almodovar");
		DirectorDto directorDto1 = new DirectorDto("Almodovar");
		List<Director> directors = new ArrayList<Director>();
		int id = 1;
		when(directorRepository.findById(id)).thenReturn(Optional.of(director1));
		when(directorRepository.findByName(director1.getName())).thenReturn(directors);
		String expected = "Updated director success";
		assertEquals(expected, directorService.updateDirector(id, directorDto1));
	}

	@Test
	@Order(4)
	public void test_updateDirectorRepeat() {
		Director director1 = new Director(1, "Almodovar");
		DirectorDto directorDto1 = new DirectorDto("Almodovar");
		List<Director> directors = new ArrayList<Director>();
		directors.add(director1);
		int id = 1;
		when(directorRepository.findById(id)).thenReturn(Optional.of(director1));
		when(directorRepository.findByName(director1.getName())).thenReturn(directors);
		String expected = "This director already exists";
		assertEquals(expected, directorService.updateDirector(id, directorDto1));
	}
	
	@Test
	@Order(5)
	public void test_updateDirectorNotExixsts() {
		DirectorDto directorDto1 = new DirectorDto("Almodovar");
		int id = 1;
		when(directorRepository.findById(id)).thenReturn(Optional.empty());
		String expected = "Director not found";
		assertEquals(expected, directorService.updateDirector(id, directorDto1));
	}

	@Test
	@Order(6)
	public void test_deleteDirector() {
		int id = 1;
		directorService.deleteDirector(id);
		verify(directorRepository, times(1)).deleteById(id);
	}

	@Test
	@Order(7)
	public void test_findDirector() {
		Director director1 = new Director(1, "Almodovar");
		DirectorDto directorDto1 = new DirectorDto("Almodovar");
		int id = 1;
		when(directorRepository.findById(id)).thenReturn(Optional.of(director1));
		when(directorDtoService.ChangeDirectorToDto(director1)).thenReturn(directorDto1);
		assertEquals(directorDto1.getName(), directorService.findDirector(id).get().getName());
	}

	@Test
	@Order(8)
	public void test_findDirectorNotFound() {
		int id = 1;
		when(directorRepository.findById(id)).thenReturn(Optional.empty());

		Optional<Director> response = directorService.findDirector(id);
		assertEquals(true, response.isEmpty());
	}
}
