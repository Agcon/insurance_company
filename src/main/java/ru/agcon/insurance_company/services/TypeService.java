package ru.agcon.insurance_company.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.agcon.insurance_company.models.TypeOfInsurance;
import ru.agcon.insurance_company.repositories.TypeRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TypeService {
    private final TypeRepository typeRepository;

    @Autowired
    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Transactional(readOnly = true)
    public List<TypeOfInsurance> getAll(){
        return typeRepository.findAll();
    }

    public boolean create(TypeOfInsurance type){
        Optional<TypeOfInsurance> received_type = typeRepository.findByName(type.getName());
        if (received_type.isPresent()) return false;
        typeRepository.save(type);
        return true;
    }

    public void delete(int id){
        typeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public TypeOfInsurance getOne(int id) {
        Optional<TypeOfInsurance> type = typeRepository.findById(id);
        return type.orElse(null);
    }
}
