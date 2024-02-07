package com.eduardo.MarvelApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HQDTO {

    private String name;
    private String author;
    private String summary;
    private Integer yearOfPublication;
    private String image;
}
