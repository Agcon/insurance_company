package ru.agcon.insurance_company.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.agcon.insurance_company.models.Insurance;

import java.util.List;
import java.util.Optional;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {
    List<Insurance> findByType_Name(String typeName);
    Optional<Insurance> findByName(String name);

    void deleteById(int id);
}
