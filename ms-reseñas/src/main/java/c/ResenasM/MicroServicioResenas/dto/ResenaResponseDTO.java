package c.ResenasM.MicroServicioResenas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResenaResponseDTO {
    private Long idResena;
    private Integer estrellas;
    private String comentarios;
    private Long idReserva;
    private Long idDueno;
    private Long idCuidador;
    private LocalDate fechaResena;
    //año,mes,dia
}