package com.eduardo.MarvelApi.converter;

import com.eduardo.MarvelApi.dto.HQDTO;
import com.eduardo.MarvelApi.model.HQ;
import org.springframework.stereotype.Component;

@Component
public class HQConverter {

    public HQ toModel(HQDTO dto) {
        HQ hq = new HQ();
        hq.setId(dto.getId());
        hq.setName(dto.getName());
        hq.setAuthor(dto.getAuthor());
        hq.setSummary(dto.getSummary());
        hq.setYearOfPublication(dto.getYearOfPublication());
        hq.setImage(dto.getImage());
        hq.setCategory(dto.getCategory());
        hq.setPrice(dto.getPrice());
        hq.setQuantity(dto.getQuantity());
        return hq;
    }

    public HQDTO toDTO(HQ hq) {
        HQDTO dto = new HQDTO();
        dto.setId(hq.getId());
        dto.setName(hq.getName());
        dto.setAuthor(hq.getAuthor());
        dto.setSummary(hq.getSummary());
        dto.setYearOfPublication(hq.getYearOfPublication());
        dto.setImage(hq.getImage());
        dto.setCategory(hq.getCategory());
        dto.setPrice(hq.getPrice());
        dto.setQuantity(hq.getQuantity());
        return dto;
    }
}