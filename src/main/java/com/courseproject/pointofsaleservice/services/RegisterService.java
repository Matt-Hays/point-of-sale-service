package com.courseproject.pointofsaleservice.services;

import com.courseproject.pointofsaleservice.models.Register;
import com.courseproject.pointofsaleservice.repositories.RegisterRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RegisterService {
    private final RegisterRepository registerRepository;
    private final RedisTemplate<Long, Register> redisTemplate;

    public List<Register> findAllRegisters() {
        return registerRepository.findAll();
    }

    public Register findRegisterById(Long id) throws EntityNotFoundException {
        Register r = redisTemplate.opsForValue().get(id);
        if (r == null) {
            r = registerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            redisTemplate.opsForValue().set(id, r);
        }
        return r;
    }

    public Register createRegister(Register register) {
        return registerRepository.save(register);
    }

    public Register updateRegister(Long id, Register register) throws EntityNotFoundException {
        Register oldRegister = findRegisterById(id);
        if (register.getLocation() != null) oldRegister.setLocation(register.getLocation());
        return registerRepository.save(oldRegister);
    }

    public void deleteRegister(Long id) {
        registerRepository.deleteById(id);
    }
}
