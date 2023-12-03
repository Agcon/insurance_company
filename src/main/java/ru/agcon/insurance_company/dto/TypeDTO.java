package ru.agcon.insurance_company.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TypeDTO {
    @Column(name = "name")
    @NotEmpty
    @NotNull
    private String name;
}