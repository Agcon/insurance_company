package ru.agcon.insurance_company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.agcon.insurance_company.models.Cart;
import ru.agcon.insurance_company.models.Insurance;
import ru.agcon.insurance_company.services.CartService;
import ru.agcon.insurance_company.services.InsuranceService;

import java.util.*;

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
    public ResponseEntity<Void> addToCart(@RequestParam("id") int id){
        String userLogin = getCurrentUserLogin();
        Optional<Cart> optionalCart = cartService.getOne(userLogin);
        Cart cart;
        if (optionalCart.isEmpty()){
            cart = new Cart();
            cart.setUserLogin(userLogin);
            Map<Integer, Integer> insuranceQuantities = new HashMap<>();
            insuranceQuantities.put(id, 1);
            cart.setInsuranceQuantities(insuranceQuantities);
            cartService.createCart(cart);
        }
        else {
            cart = optionalCart.get();
            Map<Integer, Integer> list = cart.getInsuranceQuantities();
            list.put(id, list.getOrDefault(id, 0) + 1);
            cart.setInsuranceQuantities(list);
            cartService.createCart(cart);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteFromCart(@RequestParam("id") int id){
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
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get")
    public String getInsurances(Model model){
        String userLogin = getCurrentUserLogin();
        Optional<Cart> optionalCart = cartService.getOne(userLogin);
        Cart cart;
        Map<Integer, Integer> list = new HashMap<>();
        if (optionalCart.isEmpty()){
            cart = new Cart();
            cart.setUserLogin(userLogin);
            cart.setInsuranceQuantities(list);
            cartService.createCart(cart);
        }
        else {
            cart = optionalCart.orElse(null);
            list = cart.getInsuranceQuantities();
        }
        int summa = 0;
        List<Insurance> insuranceList = new ArrayList<>();
        for(Map.Entry<Integer, Integer> entry: list.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            summa += (insuranceService.getOne(key).getPrice() * value);
            insuranceList.add(insuranceService.getOne(key));
        }
        model.addAttribute("insurances", insuranceList);
        model.addAttribute("quantities", list);
        model.addAttribute("result", summa);
        return "insurance/cart";
    }

    @PostMapping("/confirm")
    public String confirm(){
        String userLogin = getCurrentUserLogin();
        cartService.clearCart(userLogin);
        return "redirect:/cart/get";
    }

    private String getCurrentUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
