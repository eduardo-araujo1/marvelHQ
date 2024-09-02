package com.eduardo.MarvelApi.repositories;

import com.eduardo.MarvelApi.enums.Category;
import com.eduardo.MarvelApi.model.HQ;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
@Repository
public interface HQRepository extends JpaRepository<HQ,Long> {

    Optional<HQ> findByName(String name);

    Page<HQ> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Page<HQ> findByCategory(Category category, Pageable pageable);

    @Query("SELECT h FROM HQ h WHERE h.name LIKE %:keyword%")
    Page<HQ> findByNameContaining(@Param("keyword") String keyword, Pageable pageable);
}
