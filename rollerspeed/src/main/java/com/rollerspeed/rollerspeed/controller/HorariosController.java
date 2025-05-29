package com.rollerspeed.rollerspeed.controller;

import com.rollerspeed.rollerspeed.model.Clase;
import com.rollerspeed.rollerspeed.repository.ClaseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HorariosController {

    private final ClaseRepository claseRepository;

    public HorariosController(ClaseRepository claseRepository) {
        this.claseRepository = claseRepository;
    }

    @GetMapping("/horarios/estudiantes")
    public String mostrarHorarioGeneral(Model model) {
        List<Clase> clases = claseRepository.findAll();
        model.addAttribute("clases", clases);
        return "horarios/estudiantes";
    }

    @GetMapping("/horarios/instructores")
    public String mostrarHorarioGeneralInstructores(Model model) {
        List<Clase> clases = claseRepository.findAll();
        model.addAttribute("clases", clases);
        return "horarios/instructores";
    }

}
