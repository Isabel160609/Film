package com.init.film.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "characters")
public class Character {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "character_id")
	private int character_id;

	@Column(name = "name")
	String name;

	@ManyToMany(mappedBy = "characters", cascade = { CascadeType.ALL })
	private Set<Film> films = new HashSet<Film>();
}
