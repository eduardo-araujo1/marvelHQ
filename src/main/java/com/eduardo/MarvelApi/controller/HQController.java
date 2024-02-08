package com.eduardo.MarvelApi.controller;

import com.eduardo.MarvelApi.dto.HQDTO;
import com.eduardo.MarvelApi.services.HQService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("hq")
@RequiredArgsConstructor
public class HQController {

    private final HQService service;

    @PostMapping
    public ResponseEntity<HQDTO> registerHQ(@Valid @RequestBody HQDTO hqdto){
        var hqDTO = service.registerHQ(hqdto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(hqdto.getName()).toUri();
        return ResponseEntity.created(uri).body(hqDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<HQDTO>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<HQDTO> returnAll = service.findAll(page,size);
        return ResponseEntity.ok().body(returnAll);
    }

}
