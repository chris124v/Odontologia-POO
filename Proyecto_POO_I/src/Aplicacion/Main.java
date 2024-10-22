/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Aplicacion;

import Conceptos.Medicos;
import Conceptos.Paciente;
import Conceptos.Servicio;
import Presentacion.Principal;
import Util.XMLHandler;
import Util.XMLWriter;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JFrame;
import Util.XMLHandler;
import java.util.Arrays;

/**
 *  Integrantes de la pareja:
 *  Christopher Daniel Vargas Villalta, Carnet: 2024108443
 *  Jervis Esquivel Quiros, Carnet: 2024155599
 * 
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Probando el parser lectura de todas las clases
        
        
        // Cargar pacientes desde el archivo XML
        ArrayList<Paciente> pacientes = XMLHandler.CargarPacientes("pacientes.xml");
        
        // Mostrar los pacientes cargados con parser lectura
        System.out.println("\nPacientes cargados:");
        for (Paciente paciente : pacientes) {
        System.out.println(paciente);}
        
        //Cargar lista de servicios al XML
        ArrayList<Servicio> servicios = XMLHandler.CargarServicios("servicios.xml");
        
        // Mostrar los pacientes cargados con parser lectura
        System.out.println("\nServicios cargados:");
        for (Servicio servicio : servicios) {
        System.out.println(servicio);}
        
        //Cargar la lista de medicos al XML
        ArrayList<Medicos> medicos = XMLHandler.CargarMedico("medicos.xml");
        
        // Mostrar los pacientes cargados con parser lectura
        System.out.println("\nMedicos cargados:");
        for (Medicos medico : medicos) {
        System.out.println(medico);}
        
        
        //Final pruebas parser lectura
        
        
        //Main del Pacientes Parser Escritura
        
        
        //Probando Modificar Pacientes
        try {
            XMLWriter generador = new XMLWriter();
            
            // Cargar el XML existente
            generador.cargarXML("pacientes.xml");
            
            // Crear instancia del paciente que se desea modificar si existe lo modifca
            Paciente pacienteModificar1 = new Paciente("300001111", "Juan Perez Modificado", "7777-1111", "juan.pm@gmail.com");
            Paciente pacienteModificar2 = new Paciente("300002222", "Ana Rojas Modificada", "7777-2222", "ana.rm@gmail.com");
            Paciente pacienteModificar3 = new Paciente("300003333", "Pedro Arnaez Modificado", "7777-3333", "pedro.am@gmail.com");
            
            // Modificar pacientes existente
            generador.modificarPaciente(pacienteModificar1);
            generador.modificarPaciente(pacienteModificar2);
            generador.modificarPaciente(pacienteModificar3);
            
            // Guardar los cambios
            generador.guardarXML("pacientes.xml");
           
        //Excepcion
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //Lista de los modificados
        System.out.println("\nPacientes cargados modificados:");
        
        //Imprime los pacientes modificados
        for (Paciente paciente : pacientes) {
        System.out.println(paciente);}
        
        //Dice que fue exitoso
        System.out.println("\nXML de Modificacion de Pacientes Exitosa\n");
        
        
        //Probando agregar pacientes
        try {
            XMLWriter generador = new XMLWriter();
            
            // Cargar el XML existente
            generador.cargarXML("pacientes.xml");
            
            // Crear pacientes
            Paciente paciente1 = new Paciente("300009999", "Christopher Vargas", "8888-1111", "jer.pm@gmail.com");
            Paciente paciente2 = new Paciente("300008888", "Jervis Esquivel", "1111-3333", "chris.rm@gmail.com");
            
            // Agregar pacientes usando el objeto
            generador.agregarPaciente(paciente1);
            generador.agregarPaciente(paciente2);
            
            // Guardar los cambios
            generador.guardarXML("pacientes.xml");
        
        //Excepcion
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //Ensena los pacientes agregados
        System.out.println("\nPacientes agregados:");
        
        //Print de todos los pacientes junto a agregados
        for (Paciente paciente : pacientes) {
        System.out.println(paciente);}
        
        //Menciona que el agregar fue exitoso.
        System.out.println("\nXML de Agregar de Pacientes Exitosa\n");
        
        
        //Probando eliminar pacientes
        try {
            XMLWriter generador = new XMLWriter();
            
            // Cargar el XML existente
            generador.cargarXML("pacientes.xml");
            
            // Paciente a eliminar
            
            Paciente pacienteEliminado = new Paciente("300009999", "Christopher Vargas", "8888-1111", "jer.pm@gmail.com");
            
            // Eliminar pacientes existente
            generador.eliminarPaciente(pacienteEliminado);
            
            // Guardar los cambios
            generador.guardarXML("pacientes.xml");
        
        //Excepcion
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //Ensena los pacientes eliminados
        System.out.println("\nPacientes eliminados:");
        
        //Print de todos los pacientes junto y no se muestra el eliminado porque ya no existe
        for (Paciente paciente : pacientes) {
        System.out.println(paciente);}
        
        //Menciona que el agregar fue exitoso.
        System.out.println("\nXML de Eliminar de Pacientes Exitosa\n");
        
        
        //Fin  del Main del Pacientes Parser Escritura
        
        
        //Main del Servicios Parser Escritura
        
        //Probando Agregar servicios
        try {
            XMLWriter generador = new XMLWriter();
            
            // Cargar el XML existente
            generador.cargarXML("servicios.xml");
            
            Servicio nuevoservi1 = new Servicio("104", "Limpieza Bucal", 45000);
            Servicio nuevoservi2 = new Servicio("106", "Cirugia 4", 75000);
            
            // Modificar servicio existente
            generador.agregarServicio(nuevoservi1);
            generador.agregarServicio(nuevoservi2);
            
            // Guardar los cambios
            generador.guardarXML("servicios.xml");
        
        //Excepcion
        } catch (Exception e) {
            e.printStackTrace();
        }
        
         //Ensena los servicios agregados
        System.out.println("\nServicios Agregados:");
        
        //Print de todos los servicios junto el nuevo
        for (Servicio servicio : servicios) {
        System.out.println(servicio);}
        
        //Menciona que el agregar fue exitoso.
        System.out.println("\nXML de Agregar Servicios Exitosa\n");
        
        
        //Probando Modificar servicios
        try {
            XMLWriter generador = new XMLWriter();
            
            // Cargar el XML existente
            generador.cargarXML("servicios.xml");
            
            Servicio elimservi = new Servicio ("101", "Limpieza Bucal 23", 65000);
            
            // Modificar servicio existente
            generador.modificarServicios(elimservi);
            
            // Guardar los cambios
            generador.guardarXML("servicios.xml");
        
        //Excepcion
        } catch (Exception e) {
            e.printStackTrace();
        }
        
         //Ensena los servicios modificados
        System.out.println("\nServicios Modificados:");
        
        //Print de todos los servicios junto al modificado
        for (Servicio servicio : servicios) {
        System.out.println(servicio);}
        
        //Menciona que el agregar fue exitoso.
        System.out.println("\nXML de Modificar Servicios Exitosa");
        
        //Probando Eliminar servicios
        try {
            XMLWriter generador = new XMLWriter();
            
            // Cargar el XML existente
            generador.cargarXML("servicios.xml");
            
            Servicio servicioElim = new Servicio ("107", "Cirugia 4", 75000);
            
            // Eliminar servicio existente
            generador.eliminarServicio(servicioElim);
            
            // Guardar los cambios
            generador.guardarXML("servicios.xml");
        
        //Excepcion
        } catch (Exception e) {
            e.printStackTrace();
        }
        
         //Ensena los servicios sin el eliminado
        System.out.println("\nServicios eliminados:");
        
        //Print de todos los servicios 
        for (Servicio servicio : servicios) {
        System.out.println(servicio);}
        
        //Menciona que el eliminar fue exitoso.
        System.out.println("\nXML de Eliminar Servicios Exitosa\n");
        
        
        //Fin del Main del Servicios Parser Escritura
        
        
        
        //Main de Medicos Parser Escritura
        
        
        //Probando Agregar medicos
        try {
           //Llamamos a la clase
           XMLWriter generador = new XMLWriter();
            
           //Llamamos tambien a servicios porque lo tiene que recorrer
           generador.cargarXML("servicios.xml");
           generador.CargaServicios();
            
           // Cargar el XML existente
           generador.cargarXML("medicos.xml");
           
           Medicos nuevoMedico = new Medicos();
           nuevoMedico.setId_m("210");
           nuevoMedico.setNombre_medico("Dr. Perez 89");
           nuevoMedico.setTelefono("12345678");
           nuevoMedico.setPuesto("Dentista");

           // Agregar servicios al médico deseados
           List<String> serviciosSeleccionados = new ArrayList<>();
           serviciosSeleccionados.add("100");
           serviciosSeleccionados.add("101");
           serviciosSeleccionados.add("102");

           // Agregar el médico al XML
           generador.agregarMedico(nuevoMedico, serviciosSeleccionados);
            
           // Guardar los cambios en el archivo XML
           generador.guardarXML("medicos.xml");
        
        //Excepcion
        } catch (Exception e) {
            e.printStackTrace();
        }
        
         //Ensena los medicos agregados
        System.out.println("\nMedicos Agregados:");
        
        //Print de todos los medicos
        for (Medicos medico : medicos) {
        System.out.println(medico);}
        
        //Menciona que el agregador
        System.out.println("\nXML de Agregar Medicos Exitosa\n");
        
        
        
        //Probando Eliminar medicos
        try {
            XMLWriter generador = new XMLWriter();
            
            // Cargar el XML existente
            generador.cargarXML("medicos.xml");
            
            Medicos elimmedico = new Medicos ("9999-77777", "Odontologo", "Pedro Blanco", "202", "101", "", 0);
            
            // Eliminar medico con un servicio existente
            generador.eliminarMedico(elimmedico);
            
            // Guardar los cambios
            generador.guardarXML("medicos.xml");
        
        //Excepcion
        } catch (Exception e) {
            e.printStackTrace();
        }
        
         //Ensena los medicos
        System.out.println("\nMedicos elimando:");
        
        //Print de todos los medicos
        for (Medicos medico : medicos) {
        System.out.println(medico);}
        
        //Menciona que el eliminar fue exitoso.
        System.out.println("\nXML de Eliminar Medicos Exitosa\n");
        
        
        //Probando Modificar medicos
        try {
            XMLWriter generador = new XMLWriter();
            
            //Llamamos tambien a servicios porque lo tiene que recorrer
            generador.cargarXML("servicios.xml");
            generador.CargaServicios();
            
            // Cargar el XML existente
            generador.cargarXML("medicos.xml");
            
            Medicos medicoModi = new Medicos();
            
            medicoModi.setId_m("201");
            medicoModi.setNombre_medico("Maria Rojas 11");
            medicoModi.setTelefono("9999-876---");
            medicoModi.setPuesto("Becaria");
            medicoModi.setServicios(Arrays.asList("100"));
          
            generador.modificarMedico(medicoModi);
                    
            // Guardar los cambios
            generador.guardarXML("medicos.xml");
        
        //Excepcion
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //Ensena los medicos
        System.out.println("\nMedicos Modificados:");
        
        //Print de todos los medicos
        for (Medicos medico : medicos) {
        System.out.println(medico);}
        
        //Menciona que el modificar fue exitoso.
        System.out.println("\nXML de Modificar medicos Exitosa");
        
        
    // Inicializar la ventana principal
    Principal principal = new Principal();
    
    // Hacer la ventana visible
    principal.setVisible(true);
    
    
    //Prueba
}
    }
    

