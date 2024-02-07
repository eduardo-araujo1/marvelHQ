package com.eduardo.MarvelApi.repositories;

import com.eduardo.MarvelApi.model.CharacterInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterInfoRepository extends JpaRepository<CharacterInfo,Long> {
}
