package com.init.film.dto;

import java.util.HashSet;
import java.util.Set;

import com.init.film.entity.Actor;
import com.init.film.entity.Director;
import com.init.film.entity.Genre;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FilmDto {

	private String name;

	private int year;

	private Genre genre;

	private String overview;

	private Director director;

	private Set<Actor> actors = new HashSet<Actor>();

	public FilmDto(String name, int year, String overview) {
		this.name = name;
		this.year = year;
		this.overview = overview;
	}

}
