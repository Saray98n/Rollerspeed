package com.rollerspeed.rollerspeed.controller;

import com.rollerspeed.rollerspeed.model.Instructor;
import com.rollerspeed.rollerspeed.repository.InstructorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/instructores")
public class InstructorController {

    private final InstructorRepository instructorRepository;

    public InstructorController(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    // Mostrar formulario de registro
    @GetMapping("/nuevo")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("instructor", new Instructor());
        return "instructores/formulario";
    }

    // Listar instructores
    @GetMapping
    public String listarInstructores(Model model) {
        model.addAttribute("instructores", instructorRepository.findAll());
        return "instructores/listar";
    }

    // Mostrar formulario de edición
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Optional<Instructor> instructorOpt = instructorRepository.findById(id);
        if (instructorOpt.isEmpty()) {
            return "redirect:/instructores?error=IDNoEncontrado";
        }
        model.addAttribute("instructor", instructorOpt.get());
        return "instructores/formulario";
    }

    // Guardar instructor (nuevo o editado)
    @PostMapping("/guardar")
    public String guardarInstructor(@ModelAttribute Instructor instructor) {
        instructorRepository.save(instructor);
        return "redirect:/instructores";
    }

    // Eliminar instructor
    @GetMapping("/eliminar/{id}")
    public String eliminarInstructor(@PathVariable Long id) {
        if (instructorRepository.existsById(id)) {
            instructorRepository.deleteById(id);
        }
        return "redirect:/instructores";
    }
}
