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

@Service
@RequiredArgsConstructor
public class HQService {

    private final HQRepository repository;
    private final HQConverter converter;

    @Transactional
    public HQDTO registerHQ(HQDTO hqdto){
        HQ hqToSave = converter.toModel(hqdto);
        HQ savedHQ = repository.save(hqToSave);
        return converter.toDTO(savedHQ);
    }

    @Transactional(readOnly = true)
    public Page<HQDTO> findAll(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<HQ> hqPage = repository.findAll(pageRequest);
        return hqPage.map(converter::toDTO);
    }
}
