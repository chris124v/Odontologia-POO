/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conceptos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Conceptos.Servicio;

/**
 *
 * @author Christopher
 */

public class Medicos extends Servicio {
    String telefono;
    String puesto;
    String nombre_medico;
    String id_m;
    private List<String> servicios;
    
    
    
    public Medicos(String telefono, String puesto, String nombre_medico, String id_m, String id, String nombre_servicio, double precio) {
        super(id, nombre_servicio, precio);
        this.telefono = telefono;
        this.puesto = puesto;
        this.nombre_medico = nombre_medico;
        this.id_m = id_m;
        this.servicios = new ArrayList<>();
        
    }
    
    
    public Medicos() {
        super();
        this.telefono = " ";
        this.puesto = " ";
        this.nombre_medico = " ";
        this.id_m = " ";
        this.servicios = new ArrayList<>();
        
    }
   

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getNombre_medico() {
        return nombre_medico;
    }

    public void setNombre_medico(String nombre_medico) {
        this.nombre_medico = nombre_medico;
    }

    public String getId_m() {
        return id_m;
    }

    public void setId_m(String id_m) {
        this.id_m = id_m;
    }
    
    public List<String> getServicios() {
        return servicios;
    }

    public void setServicios(List<String> servicios) {
        this.servicios = servicios;
    }

    public void addServicio(String servicio) {
        this.servicios.add(servicio);
    }

    public void removeServicio(String servicio) {
        this.servicios.remove(servicio);
    }

    
  
    @Override
    public String toString(){
        return "Medico :" + this.getNombre_medico()+ ", ID :" + this.getId_m() + ", Puesto :" + this.getPuesto() + ", Telefono :" + this.getTelefono() + ", Servicios: " + this.getId();
    }
    
    //Prueba
    
}

