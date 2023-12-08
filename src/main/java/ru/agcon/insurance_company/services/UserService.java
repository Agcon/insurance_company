package ru.agcon.insurance_company.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.agcon.insurance_company.models.User;
import ru.agcon.insurance_company.repositories.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
@Transactional()
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<User> getAll(){
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getOne(int id){
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Transactional(readOnly = true)
    public User getByLogin(String login){
        Optional<User> user = userRepository.findByLogin(login);
        return user.orElse(null);
    }

    public boolean create(User user){
        String login = user.getLogin();
        if (this.getByLogin(login) != null) return false;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public void update(User user){
        userRepository.save(user);
    }

    public void delete(int id){
        userRepository.deleteById(id);
    }

}
