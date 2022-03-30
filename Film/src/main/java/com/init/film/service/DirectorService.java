package com.init.film.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.init.film.dto.DirectorDto;
import com.init.film.dto.service.DirectorDtoService;
import com.init.film.entity.Director;
import com.init.film.repository.DirectorRepository;

@Service
public class DirectorService {

	@Autowired
	private DirectorRepository directorRepository;

	@Autowired
	private DirectorDtoService directorDtoService;

	/**
	 * This method list directors with pagination
	 * 
	 * @Param pageable
	 * @return Page<DirectorDto>
	 */
	public Page<DirectorDto> listDirectors(Pageable pageable) {
		Page<Director> entities = directorRepository.findAll(pageable);
		Page<DirectorDto> dtoPage = entities.map(new Function<Director, DirectorDto>() {

			@Override
			public DirectorDto apply(Director director) {

				return directorDtoService.ChangeDirectorToDto(director);
			}
		});
		return dtoPage;
	}

	/**
	 * This method create directors
	 * 
	 * @Param directorDto
	 * @return String
	 */
	public String createDirector(DirectorDto directorDto) {
		String response;
		List<Director> directors = directorRepository.findByName(directorDto.getName());
		if (directors.isEmpty()) {
			Director director = directorDtoService.ChangeDtoToDirector(directorDto);
			Director newDirector = directorRepository.save(director);
			response = "Director created with name " + newDirector.getName();

		} else {
			response = "This director already exists";

		}
		return response;
	}

	/**
	 * This method update directors
	 * 
	 * @Param id, directorDto
	 * @return String
	 */
	public String updateDirector(int id, DirectorDto directorDto) {
		Optional<Director> directorOptional = directorRepository.findById(id);
		if (directorOptional.isPresent()) {

			Director directorUpdate = directorOptional.get();
			directorUpdate.setId_director(id);
			List<Director> directors = directorRepository.findByName(directorDto.getName());
			if (directors.isEmpty()) {
				directorUpdate.setName(directorDto.getName());
				directorRepository.save(directorUpdate);
				return "Updated director success";
			} else {
				return "This director already exists";
			}
		} else {
			return "Director not found";
		}
	}

	/**
	 * This method delete directors
	 * 
	 * @Param id
	 * @return void
	 */
	public void deleteDirector(int id) {
		directorRepository.deleteById(id);
	}

	/**
	 * This method find directors by id
	 * 
	 * @Param id
	 * @return Optional<Director>
	 */
	public Optional<Director> findDirector(int id) {
		Optional<Director> director = directorRepository.findById(id);
		return director;
	}
}
