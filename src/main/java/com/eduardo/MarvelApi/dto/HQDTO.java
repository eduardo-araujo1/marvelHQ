package com.eduardo.MarvelApi.dto;

import com.eduardo.MarvelApi.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HQDTO {

    private Long id;

    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    @NotBlank
    @Size(min = 1, max = 100)
    private String author;

    @NotBlank
    @Size(min = 1, max = 1000)
    private String summary;

    @NotNull
    @Positive
    private Integer yearOfPublication;

    @NotBlank
    private String image;

    @Positive(message = "O valor não pode ser negativo")
    @NotNull
    private Double price;

    @NotNull
    private Category category;
}
