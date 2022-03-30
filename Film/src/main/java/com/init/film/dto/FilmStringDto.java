package com.init.film.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FilmStringDto {

	private String name;

	private int year;

	private String genre;

	private String overview;

	private String director;

	private List<String> actors = new ArrayList<String>();

	@Override
	public String toString() {
		return "FilmDto [name=" + name + ", year=" + year + ", genre=" + genre + ", overview=" + overview
				+ ", director=" + director + ", actors=" + actors + "]";
	}
}
