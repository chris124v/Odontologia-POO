/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conceptos;


/**
 *
 * @author Christopher
 */
public class medicos extends Servicio {
    String telefono;
    String puesto;
    String nombre_medico;

    public medicos(String telefono, String puesto, String nombre_medico, String identificacion,String nombre_servicio, double precio) {
        super(identificacion, nombre_servicio, precio);
        this.telefono = telefono;
        this.puesto = puesto;
        this.nombre_medico = nombre_medico;
    }
    
     public medicos() {
        super();
        this.telefono = " ";
        this.puesto = " ";
        this.nombre_medico = " ";
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
   
    
}
