package cl.petpals.mscuidadores.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Errores de validacion
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException exception) {
        //Usamos linked para mantener el oredn de los errores
        Map<String, String> errores = new LinkedHashMap<>();

        //Extraemos cada campo fallido
        exception.getBindingResult().getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
        errores.put("error", "Error de base de datos: Verifica que no falten campos obligatorios o que el RUT no esté duplicado.");

        return ResponseEntity.badRequest().body(errores);
    }

    //Categoria no encontrada
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException exception) {
        Map<String, String> error = new LinkedHashMap<>();
        error.put("error", exception.getMessage());
        return ResponseEntity.status(404).body(error);
    }

    //Tipo de dato incorrecto
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex){
        Map<String, String> error = new LinkedHashMap<>();
        error.put("campo", ex.getName());
        error.put("valorRecibido", String.valueOf(ex.getValue()));
        return ResponseEntity.badRequest().body(error);
    }

    //Error generico no controlado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception exception){
        Map<String, String> error = new LinkedHashMap<>();
        error.put("error", exception.getMessage());
        error.put("motivo", "Ocurrio un error inesperado en el servidor");
        return ResponseEntity.status(500).body(error);
    }


}


