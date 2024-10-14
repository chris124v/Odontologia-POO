/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLWriter {
    private Document document;
    
    public XMLWriter() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.newDocument();
    }
    
    public void cargarXml(String nombreArchivo) throws ParserConfigurationException, SAXException, IOException {
        File file = new File(nombreArchivo);
        if (file.exists()) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(file);
        } else {
            throw new IOException("El archivo XML no existe: " + nombreArchivo);
        }
    }
    
    public void modificarPaciente(String id, String nombre, String telefono, String email) {
        Element raiz = document.getDocumentElement();
        NodeList pacientes = raiz.getElementsByTagName("paciente");
        
        for (int i = 0; i < pacientes.getLength(); i++) {
            Element paciente = (Element) pacientes.item(i);
            if (paciente.getAttribute("id").equals(id)) {
                // Modificar los elementos del paciente
                paciente.getElementsByTagName("nombre").item(0).setTextContent(nombre);
                paciente.getElementsByTagName("telefono").item(0).setTextContent(telefono);
                paciente.getElementsByTagName("email").item(0).setTextContent(email);
                break;
            }
        }
    }
    
    public void guardarXml(String nombreArchivo) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(nombreArchivo));
        
        transformer.transform(source, result);
    }
  
}