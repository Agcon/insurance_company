package ru.agcon.insurance_company.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.agcon.insurance_company.dto.InsuranceDTO;
import ru.agcon.insurance_company.models.Insurance;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @PostMapping("/add")
    public void addToCart(){
        //TODO сущность корзины и бд для неё.
    }

    @PostMapping("/delete")
    public void deleteFromCart(){
        //TODO сущность корзины и бд для неё.
    }

    @GetMapping("/get")
    public List<InsuranceDTO> getInsurances(){
        //TODO сущность корзины и бд для неё.
        return new ArrayList<>();
    }

    @PostMapping("/confirm")
    public float confirm(){
        return 0;
    }
}
