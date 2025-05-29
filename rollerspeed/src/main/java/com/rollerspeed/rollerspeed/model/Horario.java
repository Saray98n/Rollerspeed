package com.rollerspeed.rollerspeed.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "horarios")
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dia;
    private String horaInicio;
    private String horaFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aspirante_id")
    private Aspirante aspirante;

    public Horario(String dia, String horaInicio, String horaFin, Aspirante aspirante) {
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.aspirante = aspirante;
    }
}
