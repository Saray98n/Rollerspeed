package com.rollerspeed.rollerspeed.controller;

import com.rollerspeed.rollerspeed.model.Aspirante;
import com.rollerspeed.rollerspeed.model.Clase;
import com.rollerspeed.rollerspeed.repository.AspiranteRepository;
import com.rollerspeed.rollerspeed.repository.ClaseRepository;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/aspirantes")
public class AspiranteController {

    private final AspiranteRepository aspiranteRepository;
    private final ClaseRepository claseRepository;

    public AspiranteController(AspiranteRepository aspiranteRepository, ClaseRepository claseRepository) {
        this.aspiranteRepository = aspiranteRepository;
        this.claseRepository = claseRepository;
    }

    @GetMapping
    public String listarAspirantes(Model model) {
        model.addAttribute("aspirantes", aspiranteRepository.findAll());
        return "aspirantes/listar";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("aspirante", new Aspirante());
        return "aspirantes/formulario";
    }

    @PostMapping("/guardar")
    public String guardarAspirante(@ModelAttribute Aspirante aspirante) {
        aspiranteRepository.save(aspirante);
        return "redirect:/aspirantes";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Optional<Aspirante> aspiranteOpt = aspiranteRepository.findById(id);
        if (aspiranteOpt.isEmpty()) {
            return "redirect:/aspirantes?error=IDNoEncontrado";
        }
        model.addAttribute("aspirante", aspiranteOpt.get());
        return "aspirantes/formulario";
    }

    @PostMapping("/eliminar/{id}")
    @Transactional
    public String eliminarAspirante(@PathVariable Long id) {
        Optional<Aspirante> aspiranteOpt = aspiranteRepository.findById(id);
        if (aspiranteOpt.isPresent()) {
            Aspirante aspirante = aspiranteOpt.get();
            List<Clase> clases = claseRepository.findAll();

            for (Clase clase : clases) {
                clase.getAspirantes().remove(aspirante);
                clase.getEstudiantes().remove(aspirante);
            }

            claseRepository.saveAll(clases);
            aspiranteRepository.delete(aspirante);
        }
        return "redirect:/aspirantes";
    }
}
