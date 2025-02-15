package com.courseproject.pointofsaleservice.repositories;

import com.courseproject.pointofsaleservice.models.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RegisterRepository extends JpaRepository<Register, UUID> {
}
