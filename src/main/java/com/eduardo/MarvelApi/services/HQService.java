package com.eduardo.MarvelApi.services;

import com.eduardo.MarvelApi.converter.HQConverter;
import com.eduardo.MarvelApi.dto.HQDTO;
import com.eduardo.MarvelApi.model.Category;
import com.eduardo.MarvelApi.model.HQ;
import com.eduardo.MarvelApi.repositories.HQRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HQService {

    private final HQRepository repository;
    private final HQConverter converter;

    @Transactional
    public HQDTO registerHQ(HQDTO hqdto) {
        verifyIsHqAlreadyRegistered(hqdto.getName());
        HQ hqToSave = converter.toModel(hqdto);
        HQ savedHQ = repository.save(hqToSave);
        return converter.toDTO(savedHQ);
    }

    private void verifyIsHqAlreadyRegistered(String name) {
        Optional<HQ> optionalHQ = repository.findByName(name);
        if (optionalHQ.isPresent()) {
            throw new RuntimeException("Esta HQ já esta registrada no sistema");
        }
    }

    @Transactional(readOnly = true)
    public Page<HQDTO> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<HQ> hqPage = repository.findAll(pageRequest);
        return hqPage.map(converter::toDTO);
    }


    @Transactional(readOnly = true)
    public HQDTO findByName(String name) {
        HQ hq = repository.findByName(name)
                .orElseThrow(() -> new RuntimeException());

        return converter.toDTO(hq);
    }

    @Transactional(readOnly = true)
    public Page<HQDTO> findHQsByPrice(Double minPrice, Double maxPrice, Pageable pageable) {
        Page<HQ> hqPage = repository.findByPriceBetween(minPrice, maxPrice, pageable);

        if (hqPage.isEmpty()) {
            throw new RuntimeException("Não encontramos produtos correspondentes a seleção.");
        }

        return hqPage.map(converter::toDTO);
    }


    @Transactional(readOnly = true)
    public Page<HQDTO> findHQsByCategory(Category category, Pageable pageable) {
        Page<HQ> hqPage = repository.findByCategory(category, pageable);

        if (hqPage.isEmpty()) {
            throw new RuntimeException("Nenhuma HQ foi encontrada nesta categoria.");
        }

        return hqPage.map(converter::toDTO);
    }

    @Transactional
    public HQDTO updateHQ(Long id, HQDTO hqdto) {
        HQ hq = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não existe nenhuma HQ com o ID fornecido: " + id));

        hq.setName(hqdto.getName());
        hq.setAuthor(hqdto.getAuthor());
        hq.setSummary(hqdto.getSummary());
        hq.setYearOfPublication(hqdto.getYearOfPublication());
        hq.setImage(hqdto.getImage());
        hq.setPrice(hqdto.getPrice());
        hq.setCategory(hqdto.getCategory());

        HQ updatedHQ = repository.save(hq);
        return converter.toDTO(updatedHQ);
    }

}
