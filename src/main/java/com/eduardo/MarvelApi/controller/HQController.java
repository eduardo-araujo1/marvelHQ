package com.eduardo.MarvelApi.controller;

import com.eduardo.MarvelApi.dto.HQDTO;
import com.eduardo.MarvelApi.enums.Category;
import com.eduardo.MarvelApi.services.HQService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;

@RestController
@RequestMapping("hq")
@RequiredArgsConstructor
@Tag(name = "hqs", description = "API para gerenciamento de produtos")
public class HQController {

    private final HQService service;

    @Operation(summary = "Adicionar um produto", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso."),
            @ApiResponse(responseCode = "409", description = "Já existe um produto igual este cadastrado.")
    })

    @PostMapping
    public ResponseEntity<HQDTO> registerHQ(@Valid @RequestBody HQDTO hqdto){
        var hqDTO = service.registerHQ(hqdto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(hqdto.getName()).toUri();
        return ResponseEntity.created(uri).body(hqDTO);
    }

    @Operation(summary = "Listar todos os produtos", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca páginada de produtos.")
    })

    @GetMapping("/list")
    public ResponseEntity<Page<HQDTO>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "9") int size){
        Page<HQDTO> returnAll = service.findAll(page,size);
        return ResponseEntity.ok().body(returnAll);
    }

    @Operation(summary = "Buscar produto com nomes parecidos", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Faz uma busca puxando os nomes parecidos e com paginação.")
    })

    @GetMapping(params = "name")
    public ResponseEntity<Page<HQDTO>> findByName(@RequestParam("name") String name,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "9") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<HQDTO> hqDto = service.searchByName(name, pageable);
        return ResponseEntity.ok().body(hqDto);
    }

    @Operation(summary = "Buscar produto pelo ID dele.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca o produto pelo ID dele."),
            @ApiResponse(responseCode = "404", description = "Não existe nenhum produto com o ID fornecido.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<HQDTO> findById(@PathVariable("id") Long id){
        var hqDto = service.findById(id);
        return ResponseEntity.ok().body(hqDto);
    }

    @Operation(summary = "Atualizar dados do produto", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso."),
            @ApiResponse(responseCode = "404", description = "Não existe nenhum produto com o ID fornecido.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<HQDTO> updateHQ(@PathVariable Long id, @RequestBody HQDTO hqdto){
        var updateHqdto = service.updateHQ(id,hqdto);
        return ResponseEntity.ok(updateHqdto);
    }

    @Operation(summary = "Filtrar produto por preço.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos encontrados."),
            @ApiResponse(responseCode = "404", description = "Não encontramos produtos correspondentes a seleção.")
    })

    @GetMapping("/price")
    public ResponseEntity<Page<HQDTO>> filterHQsByPrice(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<HQDTO> hqs = service.findHQsByPrice(minPrice, maxPrice, pageable);
        return ResponseEntity.ok(hqs);
    }
    @Operation(summary = "Filtrar produto por categoria", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos encontrados."),
            @ApiResponse(responseCode = "404", description = "Nenhum produto foi encontrado nesta categoria.")
    })
    @GetMapping("/categories")
    public ResponseEntity<Page<HQDTO>> filterHQsByCategory(
            @RequestParam Category category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<HQDTO> hqs = service.findHQsByCategory(category, pageable);
        return ResponseEntity.ok(hqs);
    }

    @Operation(summary = "Deletar produto pelo ID.", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto deletado."),
            @ApiResponse(responseCode = "404", description = "Não existe nenhum produto com o ID fornecido.")
    })
    @DeleteMapping("delete/{id}")
    public ResponseEntity<HQDTO> deleteHQ(@PathVariable Long id){
        service.deleteHQ(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
