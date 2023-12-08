package ru.agcon.insurance_company.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.agcon.insurance_company.models.TypeOfInsurance;
import ru.agcon.insurance_company.models.User;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class InsuranceDTO {

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    @NotNull
    private float price;
}
