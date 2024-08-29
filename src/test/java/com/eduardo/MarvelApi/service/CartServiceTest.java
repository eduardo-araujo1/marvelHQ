package com.eduardo.MarvelApi.service;

import com.eduardo.MarvelApi.exception.ResourceNotFoundException;
import com.eduardo.MarvelApi.model.Cart;
import com.eduardo.MarvelApi.model.CartItem;
import com.eduardo.MarvelApi.repositories.CartRepository;
import com.eduardo.MarvelApi.services.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository repository;

    @InjectMocks
    private CartService cartService;

    @Test
    public void testCreateCart_Success() {
        Cart inputCart = new Cart();
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(1L, "Product 1", "image1.jpg", 10.0, 2));
        inputCart.setItems(items);

        Cart savedCart = new Cart();
        savedCart.setId(1L);
        savedCart.setItems(items);

        when(repository.save(any(Cart.class))).thenReturn(savedCart);


        Cart result = cartService.createCart(inputCart);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1, result.getItems().size());
        verify(repository).save(any(Cart.class));
    }

    @Test
    public void testGetCartById_Success() {
        Long cartId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setItems(new ArrayList<>());

        when(repository.findById(cartId)).thenReturn(Optional.of(cart));
        Cart result = cartService.getCartById(cartId);

        assertNotNull(result);
        assertEquals(cartId, result.getId());

        verify(repository).findById(cartId);
    }

    @Test
    public void testGetCartById_NotFound() {
        Long cartId = 1L;
        when(repository.findById(cartId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.getCartById(cartId));

        verify(repository, times(1)).findById(cartId);
    }

    @Test
    public void testDeleteCart_Success() {
        Long cartId = 1L;
        when(repository.existsById(cartId)).thenReturn(true);

        cartService.deleteCart(cartId);

        verify(repository).existsById(cartId);
        verify(repository).deleteById(cartId);
    }

    @Test
    public void testDeleteCart_NotFound() {
        Long cartId = 1L;
        when(repository.existsById(cartId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> cartService.deleteCart(cartId));

        verify(repository).existsById(cartId);
        verify(repository, never()).deleteById(cartId);
    }
}
