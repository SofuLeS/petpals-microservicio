package cl.petpals.mscuidadores.modelo;

//El cliente nunca debe de tocar el jpa

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Genera automaticamento todos los metodos (setNombre, etc etc etc) para todos los atributos de tu clase
//ademas de crear metodos como toString()
//este ayuda a encapsular datos, pero que aun asi permitir que otras capas puedan leer y modificar esa info
//usando getter and setter
@Data
//crea un contructor vacio, este ayuda ya que, cuando JPA va a la base de datos  y trae la info primero crea
//los objetos en blanco y luego empiceza a llenarlo con setter, si no lo ocupas este se cae
@NoArgsConstructor
@AllArgsConstructor

//PARA HABLAR CON LA BASE DE DATOS

//Marca tu clase en java
@Entity
//Permite dejar de maner explicita el nombre de la tabla
@Table(name = "cuidadores")
public class Cuidador {
    //Este esta definiendo la primary Keys
    @Id
    //Esta diciendo como se asigna un valor, y lo siguente esta hecho para que la base de dato lo incremente
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //se usa long en vez de int para garantizar estabilidad a largo plazo pq int posee un limite y long aguante billores
    private Long id;
    //La columa obvio crea la base de datos
    //nullable = signifca que no puede estar vacio
    //le..= maximo de caracteres
    //uniq..= que no pueden haber dos iguales en la tabla de datos
    @Column(nullable = false, length = 12, unique = true)
    private String rut;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false, length = 150)
    private String apellidos;

    @Column(nullable = false, length = 11, unique = true)
    private String telefono;

    @Column(nullable = false, length = 254, unique = true)
    private String correoElectronico;

}
