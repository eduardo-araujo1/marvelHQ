package com.eduardo.MarvelApi.controller;

import com.eduardo.MarvelApi.dto.HQDTO;
import com.eduardo.MarvelApi.model.Category;
import com.eduardo.MarvelApi.services.HQService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    @GetMapping(params = "name")
    public ResponseEntity<HQDTO> findByName(@RequestParam("name") String name){
        var hqDto = service.findByName(name);
        return ResponseEntity.ok().body(hqDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HQDTO> updateHQ(@PathVariable Long id, @RequestBody HQDTO hqdto){
        var updateHqdto = service.updateHQ(id,hqdto);
        return ResponseEntity.ok(updateHqdto);
    }

    @GetMapping("/price")
    public ResponseEntity<Page<HQDTO>> filterHQsByPrice(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice,
            Pageable pageable) {
        Page<HQDTO> hqs = service.findHQsByPrice(minPrice, maxPrice, pageable);
        return ResponseEntity.ok(hqs);
    }

    @GetMapping("/categories")
    public ResponseEntity<Page<HQDTO>> filterHQsByCategory(
            @RequestParam Category category,
            Pageable pageable) {
        Page<HQDTO> hqs = service.findHQsByCategory(category, pageable);
        return ResponseEntity.ok(hqs);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<HQDTO> deleteHQ(@PathVariable Long id){
        service.deleteHQ(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
