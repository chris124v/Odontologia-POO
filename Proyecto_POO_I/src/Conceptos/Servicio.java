/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conceptos;

/**
 *
 * @author INTEL
 */

public class Servicio {
    public String id;
    public String nombre_servicio;
    public double precio;

    
    public Servicio(String id, String nombre, double precio) {
        this.id = id;
        this.nombre_servicio = nombre;
        this.precio = precio;
    }
    
     public Servicio() {
        this.id = " ";
        this.nombre_servicio = " ";
        this.precio = 0.0;
    }
  
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre_servicio() {
        return nombre_servicio;
    }

    public void setNombre_servicio(String nombre) {
        this.nombre_servicio = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    @Override
    public String toString(){
        return "Servicio :" + this.getNombre_servicio()+ ", ID :" + this.getId() + ", Precio:" + this.getPrecio();
    }
}

    