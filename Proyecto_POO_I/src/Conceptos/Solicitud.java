/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conceptos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Christopher
 */

//Clase de solicitudes nos ayudara a verificar las solicitudes pendientes de cada paciente
public class Solicitud {
    
    //Atributos para la creacion del objeto
    String id;
    Servicio tipo_servicio;
    Paciente id_paciente;
    LocalDateTime fecha_hora;
    String observaciones;
    Medicos medico;
    Estado estado;
    List<Servicio> otros_servicios;
    
    //Constructor principal de las solicitudes
    public Solicitud(String id, Servicio tipo_servicio, Paciente id_paciente, LocalDateTime fecha_hora, String observaciones, Medicos medico, Estado estado, List<Servicio> otros_servicios) {
        this.id = id;
        this.tipo_servicio = tipo_servicio;
        this.id_paciente = id_paciente;
        this.fecha_hora = fecha_hora;
        this.observaciones = observaciones;
        this.medico = medico;
        this.estado = estado;
        this.otros_servicios = otros_servicios;
    }
    
    //Constructor vacio de la solicitudes, en este caso usamos null dado a que son objetos preexistentes
    public Solicitud () {
        this.id = " ";
        this.tipo_servicio = null;
        this.id_paciente = null;
        this.fecha_hora = LocalDateTime.now();
        this.observaciones = " ";
        this.medico = null;
        this.estado = null;
        this.otros_servicios = new ArrayList <>();
    }
    
    //Obtenemos el id de la solicitud
    public String getId() {
        return id;
    }
    
    //Establecemos un nuevo ID para la solicitud
    public void setId(String id) {
        this.id = id;
    }
    
    //Obtenemos el tipo de servicio asignado
    public Servicio getTipo_servicio() {
        return tipo_servicio;
    }
    
    //Cambiamos o asignos un nuevo tipo de servicio 
    public void setTipo_servicio(Servicio tipo_servicio) {
        this.tipo_servicio = tipo_servicio;
    }
    
    //Obtenemos el id del paciente como tal
    public Paciente getId_paciente() {
        return id_paciente;
    }
    
    //Establecemos un nuevo id para el paciente (sujeto a cambios)
    public void setId_paciente(Paciente id_paciente) {
        this.id_paciente = id_paciente;
    }

    //Obtenemos la fecha 
    public LocalDateTime getFecha_hora() {
        return fecha_hora;
    }
    
    //Asignamos una hora especifica
    public void setFecha_hora(LocalDateTime fecha_hora) {
        this.fecha_hora = fecha_hora;
    }
    
    //Obtenemos las observaciones designadas
    public String getObservaciones() {
        return observaciones;
    }
    
    //Asignamos nuevas observaciones
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    //Obtenemos el medico en especifico
    public Medicos getMedico() {
        return medico;
    }
    
    //Determinamos un nuevo medico (sujeto a cambios)
    public void setMedico(Medicos medico) {
        this.medico = medico;
    }
    
    //Obtenemos el estado de la solicitud
    public Estado getEstado() {
        return estado;
    }
    
    //Asignamos un nuevo estado a la solicitud 
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    //Obtenemos una lista con los servicios que se le van agregar si es necesario
    public List<Servicio> getOtros_servicios() {
        return otros_servicios;
    }
    
    //Establecemos los nuevos servicios que va a tener 
    public void setOtros_servicios(List<Servicio> otros_servicios) {
        this.otros_servicios = otros_servicios;
    }
    
    //Override para la impresion de la solicitud
    @Override
    public String toString() {
        
        //Aqui basicamente lo que hace es que mediante un punteros recorre la lista y los guarda en otros_servicios_crea
        String otros_servicios_crea = otros_servicios.isEmpty() ? "Ninguno" : otros_servicios.stream().map(servicio -> servicio.getNombre_servicio()).reduce((s1, s2) -> s1 + ", " + s2).orElse("No hay nada");
        
        //Aqui nada mas juntamos todos los atributos y sino son nulos los imprime segun los objetos se hayan creado
        return String.format("Solicitud:\n" + "ID: %s\n" + "Paciente: %s\n" + "Tipo de Servicio: %s\n" + "Fecha: %s\n" + "Medico: %s\n" + "Estado de la Solicitud: %s\n" + "Observaciones: %s\n" +"Otros Servicios: %s",
            id, id_paciente != null ? id_paciente.getNombre() : "No hay nada", tipo_servicio != null ? tipo_servicio.getNombre_servicio() : "No hay nada", fecha_hora != null ? fecha_hora.toString() : "No hay nada",
            medico != null ? medico.getNombre_medico() : "No hay ", estado != null ? estado.getNombre() : "No hay", observaciones != null ? observaciones : "No hay nada", otros_servicios_crea);
    }
   
    
}
