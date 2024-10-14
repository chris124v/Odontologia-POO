/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import Conceptos.Medicos;
import Conceptos.Paciente;
import Conceptos.Servicio;
import java.io.File;

import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLHandler {
    
     private static String getValue(String tag, Element elemento) {
        NodeList nodeList = elemento.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

  private static Document cargarDocumentoXML(String nombreXML) {
        try {
            File archivo = new File(nombreXML);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse(archivo);
            documento.getDocumentElement().normalize();
            return documento;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
  
    public static ArrayList<Paciente> CargarPacientes(String nombreXML) {
        ArrayList<Paciente> pacientes = new ArrayList<>();
        
        try {
            Document docXML = cargarDocumentoXML(nombreXML);
            NodeList nodos = docXML.getElementsByTagName("paciente");
            
            for (int k = 0; k < nodos.getLength(); k++) {
                Node nodo = nodos.item(k);
                
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;
                    String id = elemento.getAttribute("id");
                    String nombre = getValue("nombre", elemento);
                    String telefono = getValue("telefono", elemento);
                    String email = getValue("email", elemento);
                    Paciente paciente = new Paciente(id, nombre, telefono, email);
                    pacientes.add(paciente);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return pacientes;
    }

  public static ArrayList<Medicos> CargarMedico(String nombreXML) {
    ArrayList<Medicos> medicos = new ArrayList<>();
    try {
        Document docXML = cargarDocumentoXML(nombreXML);
        NodeList nodos = docXML.getElementsByTagName("medico");
        for (int k = 0; k < nodos.getLength(); k++) {
            Node nodo = nodos.item(k);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element elemento = (Element) nodo;
                
                String id_m = elemento.getAttribute("id_m"); 
                String nombre_medico = getValue("nombre_medico", elemento);
                String puesto = getValue("puesto", elemento);
                String telefono = getValue("telefono", elemento);
                
                // Obtener los IDs de los servicios
                ArrayList<String> idsServicios = new ArrayList<>();
                NodeList servicios = elemento.getElementsByTagName("servicios").item(0).getChildNodes();
                for (int j = 0; j < servicios.getLength(); j++) {
                    Node idServicioNode = servicios.item(j);
                    if (idServicioNode.getNodeType() == Node.ELEMENT_NODE) {
                        idsServicios.add(idServicioNode.getTextContent());
                    }
                }
                
                // Crear el médico 
                Medicos medico = new Medicos(telefono, puesto, nombre_medico, id_m, id_m, "", 0.0); // Ajustar según lo que necesites
                medicos.add(medico);
            }
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    return medicos;
}

    public static ArrayList<Servicio> CargarServicios(String nombreXML) {
        ArrayList<Servicio> servicios = new ArrayList<>();
        try {
            Document docXML = cargarDocumentoXML(nombreXML);
            NodeList nodos = docXML.getElementsByTagName("servicio");
            for (int k = 0; k < nodos.getLength(); k++) {
                Node nodo = nodos.item(k);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;
                    String id = elemento.getAttribute("id");
                    String nombreServicio = getValue("nombre_servicio", elemento);
                    double precio = Double.parseDouble(getValue("precio", elemento));
                    
                    Servicio servicio = new Servicio(id, nombreServicio, precio);
                    servicios.add(servicio);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return servicios;
    }

 
}