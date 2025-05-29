package com.rollerspeed.rollerspeed.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clases")
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "instructor_id", nullable = false)
    private Instructor instructor;

    @ManyToMany
    @JoinTable(name = "clase_aspirantes", joinColumns = @JoinColumn(name = "clase_id"), inverseJoinColumns = @JoinColumn(name = "aspirante_id"))
    private List<Aspirante> aspirantes;

    @ManyToMany
    @JoinTable(name = "clase_estudiantes", joinColumns = @JoinColumn(name = "clase_id"), inverseJoinColumns = @JoinColumn(name = "estudiante_id"))
    private List<Aspirante> estudiantes;
}
