/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

//Import de las diversas clases 
import Conceptos.Estado;
import Conceptos.Medicos;
import Conceptos.Paciente;
import Conceptos.Servicio;
import Conceptos.Solicitud;


//Include para poder usar funciones de Java que modifiquen los files
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

//Imports propios como el array list y los builders del parser tipo DOM
import java.util.ArrayList;
import java.util.List;
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
                
                
                // Crear el medico junto con los strings de idsServicios
                Medicos medico = new Medicos(telefono, puesto, nombre_medico, id_m, "", "", 0.0); 
                
                medico.setServicios(idsServicios);
                
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
    
    //Este seria el metodo de lectura de los estados
    public static ArrayList<Estado> CargarEstado(String nombreXML) {
        
        //Inicializa el arraylist de estados
        ArrayList<Estado> estados = new ArrayList<>();
        
        //Try para la carga del xml con el tag propio
        try {
            
            //Se establece cargar documento para especificar el xml al que se va a acceder
            Document docXML = cargarDocumentoXML(nombreXML);
            
            //Mediante la lista de nodos del get value se va a buscar en el XML el tag estado
            NodeList nodos = docXML.getElementsByTagName("estado");
            
            //Ciclo for que recorre el tag especificado
            for (int k = 0; k < nodos.getLength(); k++) {
                
                //Esteblece el nodo 0 o el primero como k
                Node nodo = nodos.item(k);
                
                //Si el nodo que se va a recorrer se verifica que es de tipo nodo
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    
                    //Establece el elemento de tipo nodo 
                    Element elemento = (Element) nodo;
                    
                    //Va a recorrer el atributo de id del estado
                    String id = elemento.getAttribute("id");
                    
                    //Utiliza get value para obtener el nombre del tag estado
                    String nombre = getValue("nombre", elemento);
                    
                    //Creamos un objeto de tipo estado
                    Estado estado = new Estado(id, nombre);
                    
                    //Se anade el estado al array list
                    estados.add(estado);
                }
            }
            
        //Hacemos la excepcion en caso de que no regrese nada
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        //Return del array de estados
        return estados;
    }
    
    
    //Este seria el metodo de lectura de las solicitudes
    public static ArrayList<Solicitud> CargarSolicitud(String nombreXML) {
        
        //Inicializa el arraylist de solicitudes
        ArrayList<Solicitud> solicitudes = new ArrayList<>();
      
        try {
        
            //Se establece cargar documento para especificar el xml al que se va a acceder
            Document docXML = cargarDocumentoXML(nombreXML);
            
            //Mediante la lista de nodos del get value se va a buscar en el XML el tag estado
            NodeList nodos = docXML.getElementsByTagName("solicitud");
            
            //Ciclo for que recorre el tag especificado
            for (int k = 0; k < nodos.getLength(); k++) {
                
                //Esteblece el nodo 0 o el primero como k
                Node nodo = nodos.item(k);
                
                //Si el nodo que se va a recorrer se verifica que es de tipo nodo
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    
                    //Establece el elemento de tipo nodo 
                    Element elemento = (Element) nodo;
                    
                    //Va a recorrer el atributo de id de la solicitud
                    String id = elemento.getAttribute("id");
                    
                    // El formato de fecha y hora seria el siguiente yyyy-MM-dd'T'HH:mm:ss
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

                    // Obtener el valor de fecha_hora como String
                    String fecha_hora_string = getValue("fecha_hora", elemento);

                    // Convertir el string a localDateTime para que calce con el constructor que hicmos
                    LocalDateTime fecha_hora = null;
                        
                    //Try en caso de errores 
                    try {
                        fecha_hora = LocalDateTime.parse(fecha_hora_string, formatter);
                        
                    } catch (DateTimeParseException e) {
                        
                        // Manejo de excepciones en caso de que el formato no sea valido
                        System.err.println("Error en fecha y hora: " + e.getMessage());
                        
                    }
                    
                    //Obtenemos las observaciones que se dieron 
                    String observaciones = getValue("observaciones", elemento);
                    
                    /* La modalidad de este parser es la siguiente. Como sabemos que el constructor de solicitudes
                    se basa en otras clases y objetos necesitamos hacer el parser en base a esos objetos no en string.
                    Es por esto que por ejemplo en el XML de solicitudes todos los tag relacionados a clases pueden tener 
                    todas sus caracteristicas necesarias como nombre, id y demas porque no rompe con el esquema original solo
                    agrega partes del propio objeto cumpliendo con el requerimiento. Siendo esto asi podemos recorrer
                    estos objetos con el tag necesario y simplemente crear un objeto para posteriormente agregarlo a la solicitud
                    en especifico haciendo asi posible que se cumpla el constructor y tomemos atributos de solicitudes como paciente,
                    medicos, servicios y otros servicios. 
                    */
                    
                    //Caso de los pacientes
                    
                    // Obtener Paciente como elemento
                    Element paciente_elemento = (Element) elemento.getElementsByTagName("Paciente").item(0);
                    
                    //Inicializamos el objeto en null
                    Paciente paciente = null;
                    
                    //Si el elemento es diferente de null
                    if (paciente_elemento != null) {
                        
                        //Atributo de id
                        String id_p = paciente_elemento.getAttribute("id_p");
                        
                        //Obtenemos el valor de cada elemento
                        String nombre_p = getValue("nombre_p", paciente_elemento);
                        String telefono_p = getValue("telefono_p", paciente_elemento);
                        String email = getValue("email", paciente_elemento);
                        
                        //Creamos el paciente
                        paciente = new Paciente(id_p, nombre_p, telefono_p, email);
                    }
                    
                    //Caso del servicio
                    
                    // Obtener servicios como elemento
                    Element servicio_elemento = (Element) elemento.getElementsByTagName("servicio").item(0);
                    
                    //Inicializamos el objeto en NULL
                    Servicio servicio = null;
                    
                    //Si el elemento es diferente a null
                    if (servicio_elemento != null) {
                        
                        //Atributo de id
                        String id_s = servicio_elemento.getAttribute("id_s");
                        
                        //Obtenemos el valor de cada elemento
                        String nombre_servicio = getValue("nombre_servicio", servicio_elemento);
                        double precio = Double.parseDouble(getValue("precio", servicio_elemento));
                        
                        //Creamos el servicio
                        servicio = new Servicio(id_s, nombre_servicio, precio);
                    }
                    
                    //Caso del estado
                    
                    // Obtener estado como elemento para despues crear el objeto
                    Element estado_elemento = (Element) elemento.getElementsByTagName("estado").item(0);
                    
                    //Inicializamos el objeto en null
                    Estado estado = null;
                    
                    //Si el elemento es diferente a diferente a null
                    if (estado_elemento != null) {
                        
                        //Atributo de id
                        String id_e = estado_elemento.getAttribute("id_e");
                        
                        //Obtenemos el valor de cada elemento
                        String nombre_e = getValue("nombre_e", estado_elemento);
                        
                        //Creamos el estado
                        estado = new Estado(id_e, nombre_e);
                    }
                    
                    //Caso medico
                    
                    // Obtener medico como elemento para despues crear el objeto
                    Element medico_elemento = (Element) elemento.getElementsByTagName("Medico").item(0);
                    
                    //Inicializamos el objeto en null
                    Medicos medico = null;
                    
                    //Si el elemento es diferente a null
                    if (medico_elemento != null) {
                        
                        //Atributo de id
                        String id_m = medico_elemento.getAttribute("id_m");
                        
                        //Obtenemos el valor de cada elemento
                        String nombre_medico = getValue("nombre_medico", medico_elemento);
                        String puesto = getValue("puesto", medico_elemento);
                        String telefono_m = getValue("telefono_m", medico_elemento);
                        
                        //Llamamos a los servicios del medico
                        ArrayList<String> ids_servi_med = new ArrayList<>();
                
                        //Establecemos el tag servicios que va a recorrer
                        NodeList servicios = elemento.getElementsByTagName("servicios_m").item(0).getChildNodes();
                
                        //Establecemos un ciclo for para que recorra el nuevo tag
                        for (int j = 0; j < servicios.getLength(); j++) {
                    
                            Node id_servicio_medicos = servicios.item(j); //Se inicializa j desde la primera posicion
                    
                            //Si id_servicip_medico verdaderamente es un elemento
                            if (id_servicio_medicos.getNodeType() == Node.ELEMENT_NODE) {
                        
                                //Anade al array ese elemento
                                ids_servi_med.add(id_servicio_medicos.getTextContent());
                            }
                        }
                        
                        //Creamos el medico solo agregamos lo que ocupamos
                        medico = new Medicos(telefono_m, puesto, nombre_medico, id_m, " ", " ", 0.0);
                        
                        //Usamos el metodo set
                        medico.setServicios(ids_servi_med);
                    }
                    
                    //Inicializamos un arraylist de tipo servicio
                    ArrayList<Servicio> otros_servi = new ArrayList<>();

                    // Verifica si existe el elemento "otros_servicios"
                    Element otros_servicios_elemento = (Element) elemento.getElementsByTagName("otros_servicios").item(0);
                    
                    //Si otros servicios es diferente de null
                    if (otros_servicios_elemento != null) {
                        
                        // Obtiene todos los nodos hijos dentro de otros_servicios
                        NodeList ot_servicios_nodos = otros_servicios_elemento.getChildNodes();
                        
                        //Empezamos el ciclo for con n
                        for (int n = 0; n < ot_servicios_nodos.getLength(); n++) {
                            
                            //Establecemos el nodo principal
                            Node id_otros_servi = ot_servicios_nodos.item(n);
                            
                            //Si es del tipo nodo
                            if (id_otros_servi.getNodeType() == Node.ELEMENT_NODE) {
                                
                                // Supone que cada hijo es un elemento con información del servicio como tal
                                Element servicio_elemento2 = (Element) id_otros_servi;
                                
                                //En este caso llamo solo al que me interesa
                                String id_s = servicio_elemento2.getAttribute("id_lista_servi");
                                
                                //Usamos esto para la limpieza y que obtenga bien los servicios
                                String nombre_servicio = servicio_elemento2.getTextContent().trim(); 

                                // Crea y agrega el nuevo servicio a la lista
                                Servicio otro_servicio = new Servicio(id_s, nombre_servicio, 0.0); 
                                
                                //Lo anadimos al arraylist
                                otros_servi.add(otro_servicio);
                            }
                        }
                    }

                    //Finalmente creamos la solicitud
                    Solicitud solicitud = new Solicitud(id, servicio, paciente, fecha_hora, observaciones, medico, estado, otros_servi);
                    
                    // Asignas la lista cargada
                    solicitud.setOtros_servicios(otros_servi); 
                    
                    //Anadimos todo al arraylist de las solicitudes
                    solicitudes.add(solicitud);
                      
                }
            }
            
        //Hacemos la excepcion en caso de que no regrese nada
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        //Return del array de estados
        return solicitudes;
    }

 
}