package com.eduardo.MarvelApi.services;

import com.eduardo.MarvelApi.converter.HQConverter;
import com.eduardo.MarvelApi.dto.HQDTO;
import com.eduardo.MarvelApi.model.HQ;
import com.eduardo.MarvelApi.repositories.HQRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public HQDTO updateHQ(Long id, HQDTO hqdto) {
        HQ hq = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não existe nenhuma HQ com o ID fornecido: " + id));

        hq.setName(hqdto.getName());
        hq.setAuthor(hqdto.getAuthor());
        hq.setSummary(hqdto.getSummary());
        hq.setYearOfPublication(hqdto.getYearOfPublication());
        hq.setImage(hqdto.getImage());

        HQ updatedHQ = repository.save(hq);
        return converter.toDTO(updatedHQ);
    }
}
