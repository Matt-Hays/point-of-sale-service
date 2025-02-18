package com.courseproject.pointofsaleservice.services;

import com.courseproject.pointofsaleservice.models.Register;
import com.courseproject.pointofsaleservice.repositories.RegisterRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegisterService {
    private final RegisterRepository registerRepository;

    public List<Register> findAllRegisters() {
        return registerRepository.findAll();
    }

    public Register findRegisterById(UUID id) throws EntityNotFoundException {
        return registerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Register createRegister(Register register) {
        return registerRepository.save(register);
    }

    public Register updateRegister(UUID id, Register register) throws EntityNotFoundException {
        Register oldRegister = findRegisterById(id);
        if (register.getLocation() != null) oldRegister.setLocation(register.getLocation());
        return registerRepository.save(oldRegister);
    }

    public void deleteRegister(UUID id) {
        registerRepository.deleteById(id);
    }
}
