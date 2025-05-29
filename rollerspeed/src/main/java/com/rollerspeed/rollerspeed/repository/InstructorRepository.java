package com.rollerspeed.rollerspeed.repository;

import com.rollerspeed.rollerspeed.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    Optional<Instructor> findByCorreo(String correo);
}
