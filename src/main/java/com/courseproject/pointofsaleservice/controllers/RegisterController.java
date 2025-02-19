package com.courseproject.pointofsaleservice.controllers;

import com.courseproject.pointofsaleservice.models.Register;
import com.courseproject.pointofsaleservice.services.RegisterService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.lang.Long;

@RestController
@AllArgsConstructor
@RequestMapping("v1/register")
public class RegisterController {
    private final RegisterService registerService;

    @GetMapping
    public List<Register> getRegisters() {
        return registerService.findAllRegisters();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Register> getRegisterById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(registerService.findRegisterById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Register createRegister(@RequestBody @Valid Register register) {
        return registerService.createRegister(register);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Register> updateRegister(@PathVariable Long id, @RequestBody @Valid Register register) {
        try {
            return ResponseEntity.ok(registerService.updateRegister(id, register));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteRegister(@PathVariable Long id) {
        registerService.deleteRegister(id);
    }
}
