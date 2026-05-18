package cl.petpals.ms_reservas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistorialDTO {
    private Long idReservas;
    private Long idDueño;
    private Long idCuidador;
    private Long idMascota;
    private Long idServicio;
    private LocalDateTime fechaReserva;
    private LocalDateTime fechaFinReserva;


    public Long getIdReservas() {
        return idReservas;
    }

    public void setIdReservas(Long idReservas) {
        this.idReservas = idReservas;
    }

    public Long getIdDueño() {
        return idDueño;
    }

    public void setIdDueño(Long idDueño) {
        this.idDueño = idDueño;
    }

    public Long getIdCuidador() {
        return idCuidador;
    }

    public void setIdCuidador(Long idCuidador) {
        this.idCuidador = idCuidador;
    }

    public Long getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(Long idMascota) {
        this.idMascota = idMascota;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public LocalDateTime getFechaFinReserva() {
        return fechaFinReserva;
    }

    public void setFechaFinReserva(LocalDateTime fechaFinReserva) {
        this.fechaFinReserva = fechaFinReserva;
    }
}
