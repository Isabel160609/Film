package com.init.film.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "actors")
public class Actor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "actor_id")
	private Integer actor_id;

	@NonNull
	@Column(name = "name", unique=true)
	private String name;

	@JsonIgnore
	@ManyToMany(mappedBy = "actors", cascade = { CascadeType.ALL })
	private Set<Film> films = new HashSet<Film>();

	public Actor(String name) {
		this.name = name;
	}

	public Actor(@NonNull String name, Set<Film> films) {
		this.name = name;
		this.films = films;
	}

	public Actor(Integer actor_id, @NonNull String name) {
		this.actor_id = actor_id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Actor [actor_id=" + actor_id + ", name=" + name + ", films=" + films + "]";
	}

	/**
	 * This method add actor to film
	 * 
	 * @Param TheFilm
	 * @return void
	 */
	public void addActor(Film TheFilm) {

		if (films == null)
			films = new HashSet<Film>();

		films.add(TheFilm);

	}

}
