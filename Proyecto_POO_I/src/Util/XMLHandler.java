/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

/**
 *
 * @author INTEL
 */
import Conceptos.Paciente;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.SAXException;

public class XMLHandler {

    public static List<Paciente> cargarPacientes(String filename) {
        List<Paciente> pacientes = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(filename));
            NodeList nList = doc.getElementsByTagName("paciente");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String id = eElement.getAttribute("id");
                    String nombre = eElement.getElementsByTagName("nombre").item(0).getTextContent();
                    String telefono = eElement.getElementsByTagName("telefono").item(0).getTextContent();
                    String email = eElement.getElementsByTagName("email").item(0).getTextContent();
                    pacientes.add(new Paciente(id, nombre, telefono, email));
                }
            }
        } catch (IOException | ParserConfigurationException | DOMException | SAXException e) {
        }
        return pacientes;
    }

    public static void guardarPacientes(List<Paciente> pacientes, String filename) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            
            Element rootElement = doc.createElement("pacientes");
            doc.appendChild(rootElement);

            for (Paciente paciente : pacientes) {
                Element pacienteElement = doc.createElement("paciente");
                pacienteElement.setAttribute("id", paciente.getId());
                
                Element nombre = doc.createElement("nombre");
                nombre.setTextContent(paciente.getNombre());
                pacienteElement.appendChild(nombre);

                Element telefono = doc.createElement("telefono");
                telefono.setTextContent(paciente.getTelefono());
                pacienteElement.appendChild(telefono);

                Element email = doc.createElement("email");
                email.setTextContent(paciente.getEmail());
                pacienteElement.appendChild(email);

                rootElement.appendChild(pacienteElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException | DOMException e) {
        }
    }

    // Métodos similares para Servicios y Médicos...
}