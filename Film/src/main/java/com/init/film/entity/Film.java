package com.init.film.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "films")
public class Film {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "film_id")
	private int film_id;

	@Column(name = "name")
	private String name;

	@Column(name = "year")
	private int year;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "genre_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Genre genre;
	
	@Column(name = "overview")
	private String overview;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "director_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Director director;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "films_characters", joinColumns = { @JoinColumn(name = "film_id") }, inverseJoinColumns = {@JoinColumn(name = "character_id") })
	Set<Character> characters = new HashSet<Character>();

	
}
