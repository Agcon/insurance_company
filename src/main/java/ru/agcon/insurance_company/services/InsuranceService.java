package ru.agcon.insurance_company.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.agcon.insurance_company.models.Insurance;
import ru.agcon.insurance_company.repositories.InsuranceRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;

    @Autowired
    public InsuranceService(InsuranceRepository insuranceRepository) {
        this.insuranceRepository = insuranceRepository;
    }

    @Transactional(readOnly = true)
    public List<Insurance> getAll(){
        return insuranceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Insurance getOne(int id){
        Optional<Insurance> insurance = insuranceRepository.findById(id);
        return insurance.orElse(null);
    }

    @Transactional(readOnly = true)
    public Insurance getByName(String name){
        Optional<Insurance> insurance = insuranceRepository.findByName(name);
        return insurance.orElse(null);
    }

    public boolean create(Insurance insurance){
        Optional<Insurance> received_insurance = insuranceRepository.findByName(insurance.getName());
        if (received_insurance.isPresent()) return false;
        insuranceRepository.save(insurance);
        return true;
    }

    public void update(Insurance insurance){
        insuranceRepository.save(insurance);
    }

    public void delete(int id){
        insuranceRepository.deleteById(id);
    }
}
