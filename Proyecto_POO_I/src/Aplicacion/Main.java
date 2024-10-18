/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Aplicacion;

import Conceptos.Medicos;
import Conceptos.Paciente;
import Conceptos.Servicio;
import Presentacion.Principal;
import Util.XMLWriter;
import java.util.ArrayList;
import javax.swing.JFrame;
import util.XMLHandler;

/**
 *
 * @author Christopher
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
            
            // Modificar pacientes existente
            generador.modificarPaciente("3000011114", "Juan Perez Modificado", "7777-1111", "juan.pm@gmail.com");
            generador.modificarPaciente("300002222", "Ana Rojas Modificada", "7777-2222", "ana.rm@gmail.com");
            generador.modificarPaciente("300003333", "Pedro Arnaez Modificado", "7777-3333", "pedro.am@gmail.com");
            
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
            
            // Modificar pacientes existente
            generador.agregarPaciente("300009999", "Christopher Vargas", "8888-1111", "jer.pm@gmail.com");
            generador.agregarPaciente("300008888", "Jervis Esquivel", "1111-3333", "chris.rm@gmail.com");
            
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
        System.out.println("\nXML de Agregar de Pacientes Exitosa");
        
        
        //Probando eliminar pacientes
        try {
            XMLWriter generador = new XMLWriter();
            
            // Cargar el XML existente
            generador.cargarXML("pacientes.xml");
            
            // Modificar pacientes existente
            generador.eliminarPaciente("3000099998", "Christopher Vargas", "8888-1111", "jer.pm@gmail.com");
            
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
            
            // Modificar servicio existente
            generador.agregarServicio("104", "Limpieza Bucal", 45000);
            generador.agregarServicio("106", "Cirugia 4", 75000);
            
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
        System.out.println("\nXML de Agregar Servicios Exitosa");
        
        
        //Probando Modificar servicios
        try {
            XMLWriter generador = new XMLWriter();
            
            // Cargar el XML existente
            generador.cargarXML("servicios.xml");
            
            // Modificar servicio existente
            generador.modificarServicios("109", "Limpieza Bucal 2", 65000);
            
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
            
            // Eliminar servicio existente
            generador.eliminarServicio("100", "Cirugia 4", 75000);
            
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
            XMLWriter generador = new XMLWriter();
            
            //Llamamos tambien a servicios porque lo tiene que recorrer
            generador.cargarXML("servicios.xml");
            generador.CargaServicios();
            
            // Cargar el XML existente
            generador.cargarXML("medicos.xml");
            
            // Agregar medico con un servicio existente
            generador.agregarMedico("7777-8899", "Odontologo 84", "Rodrigo Perez 2", "210", "100");
            
            // Guardar los cambios
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
            
            // Eliminar medico con un servicio existente
            generador.eliminarMedico("9999-77777", "Odontologo", "Pedro Blanco", "202", "101");
            
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
            
            // Eliminar medico con un servicio existente
            generador.modificarMedico("8888-9999", "Medico maxilofacial", "Maria Rojas 10", "201", new String[] {"102", "101", "100"});
            
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
    
    // Establecer el comportamiento de cierre
    principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    // Establecer el tamaño de la ventana
    principal.setSize(1074, 768); // Ancho: 400, Alto: 300
    
    // Centrar la ventana en la pantalla
    principal.setLocationRelativeTo(null);
    
    // Hacer la ventana visible
    principal.setVisible(true);
    
    
    //Prueba
}
    }
    

