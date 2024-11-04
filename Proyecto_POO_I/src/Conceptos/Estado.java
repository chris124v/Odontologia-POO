/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conceptos;

/**
 *
 * @author Christopher
 */

//Clase de estado para su numero en cuestion y el nombre
public class Estado {
    
    //Atributos de un estado
    String id;
    String nombre;
    
    //Constructor del estado
    public Estado(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    
    //Constructor vacio del estado
    public Estado() {
        this.id = "";
        this.nombre = "";
    }
    
    //Obtenemos el id del estado 
    public String getId() {
        return id;
    }
    
    //Asignamos un nuevo id para el estado
    public void setId(String id) {
        this.id = id;
    }
    
    //Obtenemos el nombre del estado
    public String getNombre() {
        return nombre;
    }
    
    //Asignamos un nuevo nombre al estado
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    //Override utilizado por el parser de lectura para el output
    @Override
    public String toString(){
        return "ID: " + this.getId() + ", Nombre: " + this.getNombre();
    }
    
}
