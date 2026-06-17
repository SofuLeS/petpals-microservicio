package cl.petpals.ms_reservas.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException exception){
        Map<String, String> errores = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException exception){
        Map<String, String> error = new LinkedHashMap<>();
        error.put("error", exception.getMessage());
        int status = exception.getMessage() != null && exception.getMessage().contains("no existe") ? 404 : 400;
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatch(MethodArgumentTypeMismatchException exception){
        Map<String, String> error = new LinkedHashMap<>();
        error.put("campo", exception.getName());
        error.put("valorRecibido", String.valueOf(exception.getValue()));
        error.put("estadoInvalido", "valores permitidos son: PENDIENTE, ACEPTADA, RECHAZADA, CANCELADA, FINALIZADA.");
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception exception){
        Map<String, String> error = new LinkedHashMap<>();
        error.put("error", exception.getMessage());
        error.put("motivo", "Ocurrio un error inesperado en el servidor");
        return ResponseEntity.status(500).body(error);
    }
}