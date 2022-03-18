package com.init.film.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.init.film.entity.Character;

@Repository
public interface CharacterRepository  extends JpaRepository<Character, Integer> {

}
