package com.init.film.dto.service;

import org.springframework.stereotype.Service;

import com.init.film.dto.DirectorDto;
import com.init.film.entity.Director;

@Service
public class DirectorDtoService {
	/**
	 * This method convert Director to DirectorDto
	 * 
	 * @Param director
	 * @return DirectorDto
	 */
	public DirectorDto ChangeDirectorToDto(Director director) {

		DirectorDto directorDto = new DirectorDto();
		directorDto.setName(director.getName());
		return directorDto;
	}

	/**
	 * This method convert DirectorDto to Director
	 * 
	 * @Param directorDto
	 * @return Director
	 */
	public Director ChangeDtoToDirector(DirectorDto directorDto) {

		Director director = new Director();
		director.setName(directorDto.getName());
		return director;
	}
}
