package com.eduardo.MarvelApi.services;

import com.eduardo.MarvelApi.exception.ResourceNotFoundException;
import com.eduardo.MarvelApi.model.Cart;
import com.eduardo.MarvelApi.repositories.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository repository;

    public Cart createCart(Cart cartData) {
        Cart cart = new Cart();
        cart.setItems(cartData.getItems());
        return repository.save(cart);
    }

    public Cart getCartById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with id " + id + " not found."));
    }

    public void deleteCart(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Cart with id " + id + " not found.");
        }
        repository.deleteById(id);
    }
}
