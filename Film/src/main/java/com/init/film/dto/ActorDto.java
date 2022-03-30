package com.init.film.dto;

import java.util.HashSet;
import java.util.Set;

import com.init.film.entity.Film;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ActorDto {

	private String name;

	private Set<Film> films = new HashSet<Film>();

	public ActorDto(String name) {
		this.name = name;
	}

}
