package c.ResenasM.MicroServicioResenas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Manejo específico del error de "No encontrado" (WebClient / Base de datos)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> ResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("time", LocalDateTime.now());
        body.put("mensaje", ex.getMessage());
        body.put("status", HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    // Manejo limpio de errores de validación de Spring (@Min, @Max, @NotNull)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();

        // Extraemos el primer error de validación para construir un mensaje amigable
        FieldError error = ex.getBindingResult().getFieldError();
        String mensajeLimpio = (error != null) ? error.getDefaultMessage() : "El valor ingresado no es válido";
        String campo = (error != null) ? error.getField() : "campo";

        body.put("time", LocalDateTime.now());
        body.put("mensaje", "Error de validación en los datos enviados");
        body.put("detalles", "El campo '" + campo + "' no cumple las condiciones: " + mensajeLimpio);
        body.put("status", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Manejo de cualquier otro error inesperado (Error 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("time", LocalDateTime.now());
        body.put("mensaje", "Ocurrio un error interno en el servidor");
        body.put("detalles", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}