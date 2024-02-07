package com.eduardo.MarvelApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CharacterInfoDTO {

    private String name;
    private String description;
    private String hability;
    private String history;
    private String image;

}
