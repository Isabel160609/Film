package com.init.film.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "film")
public class Film {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "film_id")
	private int film_id;

	@Column(name = "name",unique=true)
	private String name;

	@Column(name = "year")
	private int year;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "genre_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Genre genre;

	@Column(name = "overview")
	private String overview;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "director_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Director director;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "films_actors", joinColumns = { @JoinColumn(name = "film_id") }, inverseJoinColumns = {
			@JoinColumn(name = "actor_id") })
	private Set<Actor> actors = new HashSet<Actor>();

	public Film(String name, int year, String overview) {
		this.name = name;
		this.year = year;
		this.overview = overview;
	}

	public Film(int film_id, String name, int year, String overview) {
		this.film_id = film_id;
		this.name = name;
		this.year = year;
		this.overview = overview;
	}

	public Film(String name, int year, Genre genre, String overview, Director director, Set<Actor> actors) {
		this.name = name;
		this.year = year;
		this.genre = genre;
		this.overview = overview;
		this.director = director;
		this.actors = actors;
	}

	@Override
	public String toString() {
		String artist = "";
		for (Actor actor : actors) {
			artist += " actor_id= " + actor.getActor_id() + " name= " + actor.getName();
		}
		return "Film [film_id=" + film_id + ", name=" + name + ", year=" + year + ",  Genre [genre_id="
				+ genre.getGenre_id() + ", name=" + genre.getName() + "]" + ", overview=" + overview
				+ ",Director [id_director=" + director.getId_director() + ", name=" + director.getName() + "]"
				+ ", [actors=" + artist + "]";
	}

	/**
	 * This method add actor to film
	 * 
	 * @Param TheActor
	 * @return void
	 */
	public void addActor(Actor TheActor) {

		if (actors == null)
			actors = new HashSet<Actor>();

		actors.add(TheActor);

	}

	/**
	 * This Method is to know if the movie has an actor
	 * 
	 * @Param name
	 * @return boolean
	 */
	public boolean ContainsActor(String name) {
		boolean contains = false;
		int i = 0;
		while (!contains && i < actors.size()) {
			for (Actor actor : actors) {
				if (actor.getName().equalsIgnoreCase(name)) {
					contains = true;
				}
			}
			i++;
		}
		return contains;
	}

}
