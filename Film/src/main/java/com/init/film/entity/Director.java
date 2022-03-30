package com.init.film.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "director")
public class Director {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_director")
	private int id_director;

	@Column(name = "name",unique=true)
	private String name;

	public Director(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Director [id_director=" + id_director + ", name=" + name + " ]";
	}

}
