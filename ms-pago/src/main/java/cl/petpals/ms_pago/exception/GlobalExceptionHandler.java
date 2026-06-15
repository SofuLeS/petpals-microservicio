package cl.petpals.ms_pago.exception;

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
        exception.getBindingResult().getFieldErrors().forEach(error-> errores.put(error.getField(), error.getDefaultMessage()));
        errores.put("error", "Verifica los campos obligatorios.");
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex){
        Map<String, String> error = new LinkedHashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(404).body(error);
    }

    //Estado de pago o metodos de pago invalido
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    private ResponseEntity<Map<String, String>> handleTypeMismatch(MethodArgumentTypeMismatchException exception){
        Map<String, String> error = new LinkedHashMap<>();
        error.put("campo", exception.getName());
        error.put("valorRecibido", String.valueOf(exception.getValue()));
        error.put("valorInvalido", "EstadoPago válidos: PENDIENTE, COMPLETADO, FALLIDO, REEMBOLSADO. MetodoPago válidos: TARJETA_DEBITO, TARJETA_CREDITO, TRANSFERENCIA, EFECTIVO.");
        return ResponseEntity.badRequest().body(error);
    }

    //Error generico
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception exception){
        Map<String, String> error = new LinkedHashMap<>();
        error.put("error", exception.getMessage());
        error.put("motivo", "Ocurrio un error inesperado en el servidor");
        return ResponseEntity.status(500).body(error);
    }
}
