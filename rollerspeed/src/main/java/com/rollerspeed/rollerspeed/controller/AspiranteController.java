package com.rollerspeed.rollerspeed.controller;

import com.rollerspeed.rollerspeed.model.Aspirante;
import com.rollerspeed.rollerspeed.repository.AspiranteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/aspirantes")
public class AspiranteController {

    private final AspiranteRepository aspiranteRepository;

    public AspiranteController(AspiranteRepository aspiranteRepository) {
        this.aspiranteRepository = aspiranteRepository;
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

    @GetMapping("/eliminar/{id}")
    public String eliminarAspirante(@PathVariable Long id) {
        if (aspiranteRepository.existsById(id)) {
            aspiranteRepository.deleteById(id);
        }
        return "redirect:/aspirantes";
    }
}
