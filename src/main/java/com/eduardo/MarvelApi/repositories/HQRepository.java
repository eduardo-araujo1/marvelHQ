package com.eduardo.MarvelApi.repositories;

import com.eduardo.MarvelApi.model.HQ;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HQRepository extends JpaRepository<HQ,Long> {

    Optional<HQ> findByName(String name);

    Page<HQ> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);
}
