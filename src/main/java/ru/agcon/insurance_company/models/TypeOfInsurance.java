package ru.agcon.insurance_company.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "type_of_insurances")
@NoArgsConstructor
public class TypeOfInsurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotEmpty
    @NotNull
    private String name;

    @OneToMany(mappedBy = "type", cascade = CascadeType.REMOVE)
    private List<Insurance> insurances;
}
