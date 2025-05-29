package com.rollerspeed.rollerspeed.repository;

import com.rollerspeed.rollerspeed.model.Aspirante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AspiranteRepository extends JpaRepository<Aspirante, Long> {

}
