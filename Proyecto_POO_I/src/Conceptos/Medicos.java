/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conceptos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Christopher
 */

//Medicos tiene como extends servicio que pasara los servicios existentes al medico
public class Medicos extends Servicio {
    //Atrbutos publicos de Medico
    String telefono;
    String puesto;
    String nombre_medico;
    String id_m;
    
    //Atributo de lista que nos servira para establecer los ids del medico 
    private List<String> servicios;
    
    
    //Constructor del medico con la clase servicios
    public Medicos(String telefono, String puesto, String nombre_medico, String id_m, String id, String nombre_servicio, double precio) {
        super(id, nombre_servicio, precio);
        this.telefono = telefono;
        this.puesto = puesto;
        this.nombre_medico = nombre_medico;
        this.id_m = id_m;
        this.servicios = new ArrayList<>();
        
    }
    
    //Constructor vacio de medicos 
    public Medicos() {
        super();
        this.telefono = " ";
        this.puesto = " ";
        this.nombre_medico = " ";
        this.id_m = " ";
        this.servicios = new ArrayList<>();
        
    }
   
    //Get del telefono del medico 
    public String getTelefono() {
        return telefono;
    }
    
    //Establecer nuevo telefono
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    //Obtener el puesto
    public String getPuesto() {
        return puesto;
    }
    
    //Asignar un nuevo puesto 
    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
    
    //Obtener el nombre
    public String getNombre_medico() {
        return nombre_medico;
    }
    
    //Determinar nuevo nombre del medico
    public void setNombre_medico(String nombre_medico) {
        this.nombre_medico = nombre_medico;
    }
    
    //Obtener id del medico 
    public String getId_m() {
        return id_m;
    }
    
    //Asignar nuevo id al medico 
    public void setId_m(String id_m) {
        this.id_m = id_m;
    }
    
    // Lista que obtiene los servicios existentes
    public List<String> getServicios() {
        return servicios;
    }

    // Set de los servicios que va a tener el médico
    public void setServicios(List<String> servicios) {
        this.servicios = servicios;
    }

    // Añadir servicio a la lista
    public void addServicio(String servicio) {
        if (!servicios.contains(servicio)) { // Evitar duplicados
            this.servicios.add(servicio);
        }
    }

    // Quitar servicio de la lista
    public void removeServicio(String servicio) {
        this.servicios.remove(servicio);
    }

    
    //Override del parser de escritura.
    @Override
    public String toString(){
        return "Medico :" + this.getNombre_medico()+ ", ID :" + this.getId_m() + ", Puesto :" + this.getPuesto() + ", Telefono :" + this.getTelefono() + ", Servicios: " + String.join(", ", servicios);
    }
    
    
}

