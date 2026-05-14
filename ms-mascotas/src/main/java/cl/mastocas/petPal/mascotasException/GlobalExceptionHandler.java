package cl.mastocas.petPal.mascotasException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //primero que codo vamos a capturar los errores de los valid
    //Sucede cuando un capo request no cumple.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage()));

        // Aqui cuando se enviaron datos incorrectos.
        return ResponseEntity.badRequest().body(errores);
    }

    // Error cuando no se encuentra la mascota buscada
    // Desde el Service lanza RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> error = new LinkedHashMap<>();
        error.put("error", ex.getMessage());

        // Por ultimo cuando no encuentra el id del dueño , error 400.
        return ResponseEntity.badRequest().body(error);
    }
}