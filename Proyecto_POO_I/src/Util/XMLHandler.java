/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

//Import de las diversas clases 
import Conceptos.Medicos;
import Conceptos.Paciente;
import Conceptos.Servicio;

//Include para poder usar funciones de Java que modifiquen los files
import java.io.File;

//Imports propios como el array list y los builders del parser tipo DOM
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* Este Handler corresponde al lector de XML de tipo DOM
*/

//Clase del XmlHandler que permite hacer una lectura del xml
public class XMLHandler {
    
    //Getvalue representa a un nodo auxiliar que recorre la lista enlazada o arbol
     private static String getValue(String tag, Element elemento) {
        NodeList nodeList = elemento.getElementsByTagName(tag).item(0).getChildNodes(); //Aqui obtenemos la lista de nodos
        Node node = nodeList.item(0); //El nodo primerizo va a apuntar al item 0 o el primero de la lista enlazada
        
        //Realiza el return del nodo con los valores del mismo o los atributos del objeto
        return node.getNodeValue();
    }
  
  //Este metodo de tipo document va a ser encargo del cargar el documento xml propiamente con parametro el nombre del archivo
  private static Document cargarDocumentoXML(String nombreXML) {
        
        //Try del propio cargado del archivo
        try {
            
            //Instancia del archivo para el reconocimiento del nombre del archivo 
            File archivo = new File(nombreXML);
            
            //Constructores propios del documento XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse(archivo);
            
            //Este normalize se utiliza en caso de que haya problemas con el reconocimiento de archivos
            documento.getDocumentElement().normalize();
            
            //Retorna el documento
            return documento;
        
        //Exception en caso de que no se encuentre al archivo o el tag que devuelve null
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    //Este corresponde al metodo que hace la lectura de los pacientes desde el XML
    public static ArrayList<Paciente> CargarPacientes(String nombreXML) {
        
        //Inicializa el arraylist de pacientes
        ArrayList<Paciente> pacientes = new ArrayList<>();
        
        //Try para la carga del xml con el tag propio
        try {
            
            //Se establece cargar documento para especificar el xml al que se va a acceder
            Document docXML = cargarDocumentoXML(nombreXML);
            
            //Mediante la lista de nodos del get value se va a buscar en el XML el tag pacientes
            NodeList nodos = docXML.getElementsByTagName("paciente");
            
            //Ciclo for que recorre el tag especificado
            for (int k = 0; k < nodos.getLength(); k++) {
                
                //Esteblece el nodo 0 o el primero como k
                Node nodo = nodos.item(k);
                
                //Si el nodo que se va a recorrer se verifica que es de tipo nodo
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    
                    //Establece el elemento de tipo nodo 
                    Element elemento = (Element) nodo;
                    
                    //Va a recorrer el atributo de id del paciente
                    String id = elemento.getAttribute("id");
                    
                    //Utiliza get value para obtener todos los elementos del tag pacientes
                    String nombre = getValue("nombre", elemento);
                    String telefono = getValue("telefono", elemento);
                    String email = getValue("email", elemento);
                    
                    //Creamos un objeto de tipo pacientes 
                    Paciente paciente = new Paciente(id, nombre, telefono, email);
                    
                    //Se añade el paciente al array list
                    pacientes.add(paciente);
                }
            }
            
        //Hacemos la excepcion en caso de que no regrese nada
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        //Return del array de pacientes
        return pacientes;
    }
  
  //Este seria el metodo para cargar los medicos 
  public static ArrayList<Medicos> CargarMedico(String nombreXML) {
    
    //Creamos el array list de los medicos 
    ArrayList<Medicos> medicos = new ArrayList<>();
    
    //Try del metodo para recorrer el XML de Medicos
    try {
        
        //Cargamos el documento con el metodo cargar documento
        Document docXML = cargarDocumentoXML(nombreXML);
        
        //Vamos a buscar en la lista enlazada 
        NodeList nodos = docXML.getElementsByTagName("medico");
        
        //Ciclo for que recorre toda la lista enlazada
        for (int k = 0; k < nodos.getLength(); k++) {
            
            //Establecemos el nodo en la posicion inicial
            Node nodo = nodos.item(k);
            
            //Si el elemento verdaderamente es del tipo Nodo
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                
                //Establecemos el tipo elemento para que lo reconozca como tal segun encuentra el tag
                Element elemento = (Element) nodo;
                
                //Va a encontrar primero el atributo del id del medico
                String id_m = elemento.getAttribute("id_m"); 
                
                //Finalmente recorre el resto de elementos del medico presentado
                String nombre_medico = getValue("nombre_medico", elemento);
                String puesto = getValue("puesto", elemento);
                String telefono = getValue("telefono", elemento);
                
                /* Al medicos tener un extend de la clase Servicios 
                necesitamos recorrer un nuevo tag llamado servicios
                para ello creamos un array list que lo recorre
                */
                ArrayList<String> idsServicios = new ArrayList<>();
                
                //Establecemos el tag servicios que va a recorrer
                NodeList servicios = elemento.getElementsByTagName("servicios").item(0).getChildNodes();
                
                //Establecemos un ciclo for para que recorra el nuevo tag
                for (int j = 0; j < servicios.getLength(); j++) {
                    Node idServicioNode = servicios.item(j); //Se inicializa j desde la primera posicion
                    
                    //Si idsServicios verdaderamente es un elemento
                    if (idServicioNode.getNodeType() == Node.ELEMENT_NODE) {
                        
                        //Anade al array ese elemento
                        idsServicios.add(idServicioNode.getTextContent());
                    }
                }
                
                //Convertimos el arraylist en un tipo string que pase como parametro en medicos
                String idServicioString = String.join(", ", idsServicios);
                
                // Crear el médico junto con los strings de idsServicios
                Medicos medico = new Medicos(telefono, puesto, nombre_medico, id_m, idServicioString, "", 0.0); // Ajustar según lo que necesites
                medicos.add(medico);
            }
        }
    
    //Exception en caso de problemas con los tags
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    
    //Retorna la lista con los medicos
    return medicos;
}
    
    //Metodo final para el caso de xml de servicios
    public static ArrayList<Servicio> CargarServicios(String nombreXML) {
        
        //Iniciamos el arraylist de los servicios
        ArrayList<Servicio> servicios = new ArrayList<>();
        
        //Try para recorrer el XML de servicios
        try {
            
            //Acceder al documento con el nombre respectivo
            Document docXML = cargarDocumentoXML(nombreXML);
            
            //Vamos a recorrer en la lista enlazada los nodos con el nombre servicio
            NodeList nodos = docXML.getElementsByTagName("servicio");
            
            //Ciclo for para recorrer el xml de servicios
            for (int k = 0; k < nodos.getLength(); k++) {
                Node nodo = nodos.item(k); //Iniciamos el nodo en el primer elemento de la lista enlazada
                
                //Si el nodo es de tipo elemento
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    
                    //Iniciamos el elemento en el tipo nodo 
                    Element elemento = (Element) nodo;
                    
                    //Recorremos el atributo id de servicios
                    String id = elemento.getAttribute("id");
                    
                    //Recorremos los elementos de servicio con getvalue
                    String nombreServicio = getValue("nombre_servicio", elemento);
                    double precio = Double.parseDouble(getValue("precio", elemento));
                    
                    Servicio servicio = new Servicio(id, nombreServicio, precio);
                    servicios.add(servicio);
                }
            }
            
        //Exception en caso de problemas con los tags
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        //Retornamos los servicios
        return servicios;
    }

 
}