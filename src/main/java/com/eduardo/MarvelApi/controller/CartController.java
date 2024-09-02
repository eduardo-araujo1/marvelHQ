package com.eduardo.MarvelApi.controller;

import com.eduardo.MarvelApi.model.Cart;
import com.eduardo.MarvelApi.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Tag(name = "cart", description = "API para gerenciamento de carrinhos de compras")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "Criar um novo carrinho", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Carrinho criado com sucesso.")
    })

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody Cart cartData) {
        Cart cart = cartService.createCart(cartData);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cart.getId())
                .toUri();
        return ResponseEntity.created(location).body(cart);
    }

    @Operation(summary = "Buscar carrinho pelo ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrinho encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Não existe nenhum carrinho com o ID fornecido.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCart(@PathVariable Long id) {
        Cart cart = cartService.getCartById(id);
        return ResponseEntity.ok(cart);
    }

    @Operation(summary = "Deletar carrinho pelo ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carrinho deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Não existe nenhum carrinho com o ID fornecido.")
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }
}