package com.init.film.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.init.film.dto.GenreDto;
import com.init.film.dto.service.GenreDtoService;
import com.init.film.entity.Genre;
import com.init.film.repository.GenreRepository;

@Service
public class GenreService {

	@Autowired
	private GenreRepository genreRepository;

	@Autowired
	private GenreDtoService genreDtoService;

	/**
	 * This method list genres with pagination
	 * 
	 * @Param pageable
	 * @return Page<GenreDto>
	 */
	public Page<GenreDto> listGenres(Pageable pageable) {
		Page<Genre> entities = genreRepository.findAll(pageable);
		Page<GenreDto> dtoPage = entities.map(new Function<Genre, GenreDto>() {

			@Override
			public GenreDto apply(Genre genre) {

				return genreDtoService.ChangeGenreToDto(genre);
			}
		});
		return dtoPage;
	}

	/**
	 * This method create genres
	 * 
	 * @Param genreDto
	 * @return String
	 */
	public String createGenre(GenreDto genreDto) {
		String response;
		Genre genre = genreDtoService.ChangeDtoToGenre(genreDto);
		List<Genre> genres = genreRepository.findByName(genre.getName());
		if (genres.isEmpty()) {
			Genre newGenre = genreRepository.save(genre);
			response = "Genre created with name " + newGenre.getName();

		} else {
			response = "This genre already exists";

		}
		return response;
	}

	/**
	 * This method update genres
	 * 
	 * @Param id, genreDto
	 * @return String
	 */
	public String updateGenre(int id, GenreDto genreDto) {
		Optional<Genre> genreOptional = genreRepository.findById(id);
		if (genreOptional.isPresent()) {

			Genre genreUpdate = genreOptional.get();
			genreUpdate.setGenre_id(id);
			List<Genre> genres = genreRepository.findByName(genreDto.getName());
			if (genres.isEmpty()) {
				genreUpdate.setName(genreDto.getName());
				genreRepository.save(genreUpdate);
				return "Updated genre success";
			} else {
				return "This genre already exists";
			}
		} else {
			return "Genre not found";
		}
	}

	/**
	 * This method delete genres
	 * 
	 * @Param id
	 * @return void
	 */
	public void deleteGenre(int id) {

		genreRepository.deleteById(id);

	}

	/**
	 * This method find genres by id
	 * 
	 * @Param id
	 * @return Optional<Genre>
	 */
	public GenreDto findGenre(int id) {
		Optional<Genre> genreOptional = genreRepository.findById(id);
		if (genreOptional.isEmpty()) {
			GenreDto genredto = new GenreDto();
			return genredto;
		} else {
			GenreDto genredto = genreDtoService.ChangeGenreToDto(genreOptional.get());
			return genredto;
		}

	}

}
