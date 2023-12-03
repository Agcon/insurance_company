package ru.agcon.insurance_company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.agcon.insurance_company.dto.InsuranceDTO;
import ru.agcon.insurance_company.models.Cart;
import ru.agcon.insurance_company.models.Insurance;
import ru.agcon.insurance_company.services.CartService;
import ru.agcon.insurance_company.services.InsuranceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final InsuranceService insuranceService;

    @Autowired
    public CartController(CartService cartService, InsuranceService insuranceService) {
        this.cartService = cartService;
        this.insuranceService = insuranceService;
    }

    @PostMapping("/add")
    public void addToCart(@RequestParam("id") int id){
        String userLogin = getCurrentUserLogin();
        Optional<Cart> optionalCart = cartService.getOne(userLogin);
        Cart cart;
        if (optionalCart.isEmpty()){
            cart = new Cart();
            cart.setUserLogin(userLogin);
            cart.getInsuranceQuantities().put(id, 1);
            cartService.createCart(cart);
        }
        else {
            cart = optionalCart.orElse(null);
            Map<Integer, Integer> list = cart.getInsuranceQuantities();
            if (list.containsKey(id)){
                list.put(id, list.get(id) + 1);
            }
            else {
                list.put(id, 1);
            }
            cart.setInsuranceQuantities(list);
            cartService.createCart(cart);
        }
    }

    @PostMapping("/delete")
    public void deleteFromCart(@RequestParam("id") int id){
        String userLogin = getCurrentUserLogin();
        Cart cart = cartService.getOne(userLogin).orElse(null);
        Map<Integer, Integer> list = cart.getInsuranceQuantities();
        if (list.get(id) > 1){
            list.put(id, list.get(id) - 1);
        }
        else{
            list.remove(id);
        }
        cart.setInsuranceQuantities(list);
        cartService.createCart(cart);
    }

    @GetMapping("/get")
    public String getInsurances(Model model){
        String userLogin = getCurrentUserLogin();
        Optional<Cart> optionalCart = cartService.getOne(userLogin);
        Cart cart;
        if (optionalCart.isEmpty()){
            cart = new Cart();
            cart.setUserLogin(userLogin);
            cartService.createCart(cart);
        }
        else {
            cart = optionalCart.orElse(null);
            Map<Integer, Integer> list = cart.getInsuranceQuantities();

        }
//        model.addAttribute("")
        //TODO продумать отображение корзины.
        return "cart";
    }

    @PostMapping("/confirm")
    public void confirm(){
        String userLogin = getCurrentUserLogin();
        cartService.clearCart(userLogin);
    }

    private String getCurrentUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
