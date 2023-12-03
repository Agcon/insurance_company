package ru.agcon.insurance_company.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.agcon.insurance_company.models.Cart;
import ru.agcon.insurance_company.repositories.CartRepository;

import java.util.Optional;

@Service
@Transactional
public class CartService {
    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Cart> getOne(String userLogin){
        return cartRepository.findByLogin(userLogin);
    }

    public void createCart(Cart cart){
        cartRepository.save(cart.getUserLogin(), cart);
    }

    public void clearCart(String userLogin){
        Cart cart = new Cart();
        cart.setUserLogin(userLogin);
        cartRepository.save(userLogin, cart);
    }
}
