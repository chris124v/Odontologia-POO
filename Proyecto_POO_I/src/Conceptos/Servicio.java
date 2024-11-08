/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conceptos;

/**
 *
 * @author INTEL
 */

//Clase de servicios
public class Servicio {
    
    //Atributos de los servicios
    public String id;
    public String nombre_servicio;
    public double precio;

    //Constructor propio de servicios
    public Servicio(String id, String nombre, double precio) {
        this.id = id;
        this.nombre_servicio = nombre;
        this.precio = precio;
    }
     
    //Constructor vacio de servicios
     public Servicio() {
        this.id = " ";
        this.nombre_servicio = " ";
        this.precio = 0.0;
    }
     
    //Obtener el id del servicio
    public String getId() {
        return id;
    }
    
    //Asignar nuevo id al servicio
    public void setId(String id) {
        this.id = id;
    }
    
    //Obetener el nombre del servicio
    public String getNombre_servicio() {
        return nombre_servicio;
    }
    
    //Asignar nuevo nombre al servicio
    public void setNombre_servicio(String nombre) {
        this.nombre_servicio = nombre;
    }
    
    //Tipo double que obtiene el precio
    public double getPrecio() {
        return precio;
    }
    
    //Asignar nuevo precio al servicio
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    //Override para el output 
    @Override
    public String toString(){
        return "Servicio :" + this.getNombre_servicio()+ ", ID :" + this.getId() + ", Precio:" + this.getPrecio();
    }

    
}

    