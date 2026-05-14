package cl.petpals.mscuidadores.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Errores de validacion
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationErrors(
            MethodArgumentNotValidException exception){
        //Usamos linked para mantener el oredn de los errores
        Map<String, String> errores = new LinkedHashMap<>();

        //Extraemos cada campo fallido
        exception.getBindingResult().getFieldErrors().forEach(error ->errores.put(error.getField(), error.getDefaultMessage()));
        errores.put("error", "Error de base de datos: Verifica que no falten campos obligatorios o que el RUT no esté duplicado.");

        return ResponseEntity.badRequest().body(errores);
    }

}
