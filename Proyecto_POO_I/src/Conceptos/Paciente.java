/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conceptos;

/**
 *
 * @author INTEL
 */

//Clase del paciente
public class Paciente {
    
    //Atributos del paciente
    public String id;
    public String nombre;
    public String telefono;
    public String email;

    //Constructor normal del paciente 
    public Paciente(String id, String nombre, String telefono, String email) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }
    
    //Constructor vacio del paciente 
     public Paciente() {
        this.id = " ";
        this.nombre = "";
        this.telefono = "";
        this.email = "";
    }
    
    //Para obtener el id del paciente
    public String getId() {
        return id;
    }
    
    //Para cambiar el id del paciente 
    public void setId(String id) {
        this.id = id;
    }
    
    //Lo utilizamos para obtenener el nombre del paciente 
    public String getNombre() {
        return nombre;
    }
    
    //Asignamos un nuevo nombre al paciente 
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    //Lo utilizamos para obtener el telefono 
    public String getTelefono() {
        return telefono;
    }
    
    //Asignamos un nuevo telefono
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    //Lo utilizamos para obtener el email.
    public String getEmail() {
        return email;
    }
    
    //Asignar nuevo mail
    public void setEmail(String email) {
        this.email = email;
    }
    
    //Override utilizado por el parser de lectura para el output
    @Override
    public String toString(){
        return "Paciente :" + this.getNombre() + ", ID :" + this.getId() + ", Tel:" + this.getTelefono() + ", Email: " + this.getEmail();
    }
}