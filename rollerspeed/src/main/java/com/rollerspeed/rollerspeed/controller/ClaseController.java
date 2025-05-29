package com.rollerspeed.rollerspeed.controller;

import com.rollerspeed.rollerspeed.model.Clase;
import com.rollerspeed.rollerspeed.model.Aspirante;
import com.rollerspeed.rollerspeed.model.Instructor;
import com.rollerspeed.rollerspeed.repository.ClaseRepository;
import com.rollerspeed.rollerspeed.repository.AspiranteRepository;
import com.rollerspeed.rollerspeed.repository.InstructorRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clases")
public class ClaseController {

    private final ClaseRepository claseRepository;
    private final AspiranteRepository aspiranteRepository;
    private final InstructorRepository instructorRepository;

    public ClaseController(ClaseRepository claseRepository,
            AspiranteRepository aspiranteRepository,
            InstructorRepository instructorRepository) {
        this.claseRepository = claseRepository;
        this.aspiranteRepository = aspiranteRepository;
        this.instructorRepository = instructorRepository;
    }

    @GetMapping
    public String listarClases(Model model) {
        model.addAttribute("clases", claseRepository.findAll());
        return "clases/listar";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNuevaClase(Model model) {
        model.addAttribute("clase", new Clase());
        model.addAttribute("aspirantes", aspiranteRepository.findAll()); // Para aspirante único
        model.addAttribute("estudiantes", aspiranteRepository.findAll()); // Para múltiples estudiantes
        model.addAttribute("instructores", instructorRepository.findAll());
        return "clases/formulario";
    }

    @PostMapping("/guardar")
    public String guardarClase(@ModelAttribute Clase clase,
            @RequestParam(value = "estudiantesSeleccionados", required = false) List<Long> estudiantesSeleccionados) {

        // Asignar instructor
        Long instructorId = clase.getInstructor().getId();
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor no encontrado con ID: " + instructorId));
        clase.setInstructor(instructor);

        // Asignar lista de aspirantes a la clase
        List<Aspirante> aspirantes = new ArrayList<>();
        if (clase.getAspirantes() != null) {
            for (Aspirante a : clase.getAspirantes()) {
                Long id = a.getId();
                Aspirante aspirante = aspiranteRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Aspirante no encontrado con ID: " + id));
                aspirantes.add(aspirante);
            }
        }
        clase.setAspirantes(aspirantes);

        // Asignar estudiantes seleccionados
        if (estudiantesSeleccionados != null && !estudiantesSeleccionados.isEmpty()) {
            List<Aspirante> estudiantes = aspiranteRepository.findAllById(estudiantesSeleccionados);
            clase.setEstudiantes(estudiantes);
        } else {
            clase.setEstudiantes(null); // O una lista vacía si prefieres
        }

        claseRepository.save(clase);
        return "redirect:/clases";
    }

    @GetMapping("/editar/{id}")
    public String editarClase(@PathVariable Long id, Model model) {
        Optional<Clase> claseOpt = claseRepository.findById(id);
        if (claseOpt.isEmpty()) {
            return "redirect:/clases?error=IDNoEncontrado";
        }

        model.addAttribute("clase", claseOpt.get());
        model.addAttribute("aspirantes", aspiranteRepository.findAll());
        model.addAttribute("estudiantes", aspiranteRepository.findAll());
        model.addAttribute("instructores", instructorRepository.findAll());

        return "clases/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarClase(@PathVariable Long id) {
        if (claseRepository.existsById(id)) {
            claseRepository.deleteById(id);
        }
        return "redirect:/clases";
    }
}
