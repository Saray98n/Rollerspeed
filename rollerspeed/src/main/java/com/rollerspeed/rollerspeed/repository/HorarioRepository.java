package com.rollerspeed.rollerspeed.repository;

import com.rollerspeed.rollerspeed.model.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

}
