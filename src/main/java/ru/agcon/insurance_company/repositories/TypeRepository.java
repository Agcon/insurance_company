package ru.agcon.insurance_company.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.agcon.insurance_company.models.TypeOfInsurance;

import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<TypeOfInsurance, Integer> {
    Optional<TypeOfInsurance> findByName(String name);
}
