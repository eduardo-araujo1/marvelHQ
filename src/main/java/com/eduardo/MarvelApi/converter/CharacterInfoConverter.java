package com.eduardo.MarvelApi.converter;

import com.eduardo.MarvelApi.dto.CharacterInfoDTO;
import com.eduardo.MarvelApi.model.CharacterInfo;
import org.springframework.stereotype.Component;

@Component
public class CharacterInfoConverter {

    public CharacterInfo toModel(CharacterInfoDTO dto){
        CharacterInfo characterInfo = new CharacterInfo();
        characterInfo.setName(dto.getName());
        characterInfo.setDescription(dto.getDescription());
        characterInfo.setHability(dto.getHability());
        characterInfo.setHistory(dto.getHistory());
        characterInfo.setImage(dto.getImage());
        return characterInfo;
    }

    public CharacterInfoDTO toDto(CharacterInfo characterInfo){
        CharacterInfoDTO dto = new CharacterInfoDTO();
        dto.setName(characterInfo.getName());
        dto.setDescription(dto.getDescription());
        dto.setHability(dto.getHability());
        dto.setHistory(dto.getHistory());
        dto.setImage(dto.getImage());
        return dto;
    }
}
