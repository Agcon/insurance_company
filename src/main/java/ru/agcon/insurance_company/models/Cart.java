package ru.agcon.insurance_company.models;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Component;

import java.util.List;

@RedisHash("carts")
@Data
@NoArgsConstructor
@Component
public class Cart {
    @Id
    private String userLogin;
    private List<Insurance> insurances;
}
