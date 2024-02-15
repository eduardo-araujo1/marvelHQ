package com.eduardo.MarvelApi.services;

import com.eduardo.MarvelApi.converter.HQConverter;
import com.eduardo.MarvelApi.dto.HQDTO;
import com.eduardo.MarvelApi.exception.HQAlreadyRegistered;
import com.eduardo.MarvelApi.exception.ResourceNotFoundException;
import com.eduardo.MarvelApi.enums.Category;
import com.eduardo.MarvelApi.model.HQ;
import com.eduardo.MarvelApi.repositories.HQRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HQService {

    private final HQRepository repository;
    private final HQConverter converter;

    public HQDTO registerHQ(HQDTO hqdto) {
        verifyIsHqAlreadyRegistered(hqdto.getName());
        HQ hqToSave = converter.toModel(hqdto);
        HQ savedHQ = repository.save(hqToSave);
        return converter.toDTO(savedHQ);
    }

    private void verifyIsHqAlreadyRegistered(String name) {
        Optional<HQ> optionalHQ = repository.findByName(name);
        if (optionalHQ.isPresent()) {
            throw new HQAlreadyRegistered("Esta HQ já esta registrada no sistema");
        }
    }

    public Page<HQDTO> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<HQ> hqPage = repository.findAll(pageRequest);
        return hqPage.map(converter::toDTO);
    }

    public HQDTO findByName(String name) {
        HQ hq = repository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Infelizmente não temos esta HQ."));

        return converter.toDTO(hq);
    }

    public Page<HQDTO> findHQsByPrice(Double minPrice, Double maxPrice, Pageable pageable) {
        Page<HQ> hqPage = repository.findByPriceBetween(minPrice, maxPrice, pageable);

        if (hqPage.isEmpty()) {
            throw new ResourceNotFoundException("Não encontramos produtos correspondentes a seleção.");
        }
        return hqPage.map(converter::toDTO);
    }


    public Page<HQDTO> findHQsByCategory(Category category, Pageable pageable) {
        Page<HQ> hqPage = repository.findByCategory(category, pageable);

        if (hqPage.isEmpty()) {
            throw new ResourceNotFoundException("Nenhuma HQ foi encontrada nesta categoria.");
        }
        return hqPage.map(converter::toDTO);
    }


    public HQDTO updateHQ(Long id, HQDTO hqdto) {
        HQ hq = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não existe nenhuma HQ com o ID fornecido: " + id));

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

    public void deleteHQ(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("HQ não encontrada.");
        }
        repository.deleteById(id);
    }
}
