/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Aplicacion;

import Conceptos.Medicos;
import Conceptos.Paciente;
import Conceptos.Servicio;
import Util.XMLWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;
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
        
        Medicos m1 = new Medicos();
        m1.setNombre_medico("Carlos");
        System.out.println(m1.getNombre_medico());
        
        Servicio s1 = new Servicio();
        s1.setNombre_servicio("Carlos");
        System.out.println(s1.getNombre_servicio());
        
        m1.setNombre_servicio("Maxilofacial");
        m1.setId("009");
        
        System.out.println(m1.getId());
        
        // Cargar pacientes desde el archivo XML
        ArrayList<Paciente> pacientes = XMLHandler.CargarPacientes("pacientes.xml");
        
        // Mostrar los pacientes cargados
        System.out.println("\nPacientes cargados:");
        for (Paciente paciente : pacientes) {
        System.out.println(paciente);}
        
        
        ArrayList<Servicio> servicios = XMLHandler.CargarServicios("servicios.xml");
        
        // Mostrar los pacientes cargados
        System.out.println("\nServicios cargados:");
        for (Servicio servicio : servicios) {
        System.out.println(servicio);}
        
        ArrayList<Medicos> medicos = XMLHandler.CargarMedico("medicos.xml");
        
        // Mostrar los pacientes cargados
        System.out.println("\nMedicos cargados:");
        for (Medicos medico : medicos) {
        System.out.println(medico);}
        
         
        try {
            XMLWriter generador = new XMLWriter();
            
            // Cargar el XML existente
            generador.cargarXml("pacientes.xml");
            
            // Modificar pacientes existentes
            generador.modificarPaciente("300001111", "Juan Pérez Modificado 5", "7777-1111", "juan.pm@gmail.com");
            generador.modificarPaciente("300002222", "Ana Rojas Modificada 2", "7777-2222", "ana.rm@gmail.com");
            generador.modificarPaciente("300003333", "Pedro Arnaez Modificado 7", "7777-3333", "pedro.am@gmail.com");
            
            // Guardar los cambios
            generador.guardarXml("pacientes.xml");
            
            System.out.println("XML de pacientes modificado con éxito.");
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
        
        System.out.println("\nPacientes cargados:");
        for (Paciente paciente : pacientes) {
        System.out.println(paciente);}
    }
    
}
