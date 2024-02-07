package com.eduardo.MarvelApi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CharacterInfo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CharacterInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "hability")
    private String hability;
    @Column(name = "history")
    private String history;
}
