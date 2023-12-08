package ru.agcon.insurance_company.models;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

@RedisHash("carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Cart implements Serializable {
    @Id
    private String userLogin;
    private Map<Integer, Integer> insuranceQuantities;
}
