/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

/* Esta java class de XMLWriter corresponde a la clase que modifica, agrega y elemina los pacientes,
servicios y medicos esto mediante el uso del parser DOM de modificacion de archivos por arboles binarios.
*/

//Import que maneja el documento de XML
import java.io.File;

//Manejo de errores de lectura y escritura en archivos XML
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

//Builders del parser
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

//Imports de tipo transform que permiten la modifican del xml para ver en que partes se escribe y el manejo de excepciones
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

//Imports propios de un parser de escritura DOM
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//Clase XML Writer que permite modificar, agregar y modificar tanto pacientes, y medicos
public class XMLWriter {
    
    //Almacena el documento XML en memoria
    private Document document;
    
    private Map<String, Element> serviciosactuales;
    
    
    //Funciona para la creacion de la instancia del XML y tira una excpecion si huno un problema con el parser
    public XMLWriter() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); //Creacion del documento XML
        DocumentBuilder builder = factory.newDocumentBuilder(); //Creacion mediante el builder
        document = builder.newDocument(); //Establece el docuemnto
    }
    
    //Metodo de cargarXML con las excepciones del parser en caso de que no exista el archivo
    //En este caso modificamos el cargar xml para que se adapte a la necesidad de escritura por ende no devuelve null
    public void cargarXML(String nombreArchivo) throws ParserConfigurationException, SAXException, IOException {
        
        //Instancia para el documento que se va a emplear
        File file = new File(nombreArchivo);
        
        //Si el file existe 
        if (file.exists()) {
            
            //Utilize el builder para la creacion del documento
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(file);
         
        //Si no existe el archivo utilice IOException para especificar el nombre no encontrado
        } else {
            throw new IOException("El archivo XML no existe: " + nombreArchivo);
        }
    }
    
    //Metodos para Pacientes: Agregar, Eliminar y Modificar
    
    
    //Metodo para agregar los pacientes toma como parametro los atributos del paciente y
    public void agregarPaciente(String id, String nombre, String telefono, String email) { 
        
        
        //Tomamos un elemento del XML y establecemos una variable del tipo raiz
        Element raiz = document.getDocumentElement();
        
        //Tomamos un elemento de tipo paciente y se agrega al documento segun el tag paciente
        Element paciente = document.createElement("paciente");
        
        
        //Sucede lo mismo en el caso del ID como la explicacion del nombre
        paciente.setAttribute("id", id);
        
        //Nuevamente un elemento de tipo nombre es creado y se agrega al documento 
        Element elemNombre = document.createElement("nombre");
       
        //A este elemento le vamos a anadir un set contente para que modifique lo de dentro
        elemNombre.setTextContent(nombre);
        
        //Este elemento nombre se agrega como hijo al elemento padre paciente
        paciente.appendChild(elemNombre);
        
        //Sucede lo mismo en el caso del telefono
        Element elemtele = document.createElement("telefono");
        elemtele.setTextContent(telefono);
        paciente.appendChild(elemtele);
        
        //Sucede lo mismo con el email
        Element elemEmail = document.createElement("email");
        elemEmail.setTextContent(email);
        paciente.appendChild(elemEmail);
        
        //Finalmente se realiza el append del elemento de pacientes al elemento raiz que es la base del archivo xml 
        raiz.appendChild(paciente);
    }
    
    //Metodo para eliminar los pacientes toma como parametro los atributos del paciente
    public void eliminarPaciente(String id, String nombre, String telefono, String email) { 
        
        //Obtenemos los elementos del documento xml
        Element raiz = document.getDocumentElement();
        
        //Mediante una lista de nodo establecemos la raiz del arbol como pacientes el tag
        NodeList pacientes = raiz.getElementsByTagName("paciente");
        
        boolean pacienteElim = false;
        
        //Establecemos el ciclo for para recorrer los pacientes
        for (int i = 0; i < pacientes.getLength(); i++) {
            Element paciente = (Element) pacientes.item(i); //Establecemos el elemento como el primero de la lista
            
            //Si el obtenemos el atributo id y es igual que el id buscado del paciente como tal
            if (paciente.getAttribute("id").equals(id)) {
                
                //Eliminamos el paciente de la lista enlazada 
                raiz.removeChild(paciente);
                
                pacienteElim = true;
                //Se sale de la funcion
                break;
                
                //Esto se repite con todos los elementso cliente de la lista enlazada
            }
        }
        
        if (!pacienteElim) {
            
            System.out.println("Peligro: El paciente con ID " + id + " no existe y no se eliminara");
        }
    }
            
    //Metodo para modificar los pacientes toma como parametro los atributos del paciente
    public void modificarPaciente(String id, String nombre, String telefono, String email) {
       
        //Obtenemos los elementos del documento xml
        Element raiz = document.getDocumentElement();
        
        //Mediante una lista de nodo establecemos la raiz del arbol como pacientes el tag
        NodeList pacientes = raiz.getElementsByTagName("paciente");
        
        boolean pacienteEncontrado = false;
        
        //Establecemos el ciclo for para recorrer los pacientes
        for (int i = 0; i < pacientes.getLength(); i++) {
            Element paciente = (Element) pacientes.item(i); //Establecemos el elemento como el primero de la lista
            
            //Si el obtenemos el atributo id y es igual que el id buscado del paciente como tal
            if (paciente.getAttribute("id").equals(id)) {
                
                // Modificar los elementos del paciente segun el tag
                paciente.getElementsByTagName("nombre").item(0).setTextContent(nombre);
                paciente.getElementsByTagName("telefono").item(0).setTextContent(telefono);
                paciente.getElementsByTagName("email").item(0).setTextContent(email);
                
                pacienteEncontrado = true;
                
                //Se sale de la funcion
                break;
                
                //Esto se repite con todos los elementso cliente de la lista enlazada
            }
        }
        
        if (!pacienteEncontrado) {
            System.out.println("Peligro: El paciente con ID " + id + " no existe y no se modificara.");
        }
    }
    
    //Fin Metodos para Pacientes: Agregar, Eliminar y Modificar
    
    
    
    //Metodos para Servicios: Agregar, Eliminar y Modificar
    
    
    
    //Metodo para agregar un servicio al XML meduiante un arbol
    public void agregarServicio(String id, String nombre_servicio, double precio) { 
        
        
        //Tomamos un elemento del XML y establecemos una variable del tipo raiz
        Element raiz = document.getDocumentElement();
        
        //Tomamos un elemento de tipo paciente y se agrega al documento segun el tag paciente
        Element servicio = document.createElement("servicio");
        
        //Sucede lo mismo en el caso del ID como la explicacion del nombre
        servicio.setAttribute("id", id);
        
        //Nuevamente un elemento de tipo nombre es creado y se agrega al documento 
        Element elemNombre_S = document.createElement("nombre_servicio");
       
        //A este elemento le vamos a anadir un set content para que modifique lo de dentro
        elemNombre_S.setTextContent(nombre_servicio);
        
        //Este elemento nombre se agrega como hijo al elemento padre paciente
        servicio.appendChild(elemNombre_S);
        
        //Sucede lo mismo en el caso del telefono
        Element elemPrecio = document.createElement("precio");
        elemPrecio.setTextContent(String.valueOf(precio));
        servicio.appendChild(elemPrecio);
        
        //Finalmente se realiza el append del elemento de pacientes al elemento raiz que es la base del archivo xml 
        raiz.appendChild(servicio);
    }
    
    //Metodo para eliminar los servicios toma como parametro los atributos del servicio
    public void eliminarServicio(String id, String nombre_servicio, double precio) { 
        
        //Obtenemos los elementos del documento xml
        Element raiz = document.getDocumentElement();
        
        //Mediante una lista de nodo establecemos la raiz del arbol como pacientes el tag
        NodeList servicios = raiz.getElementsByTagName("servicio");
        
        boolean servicioEncontradoElim = false; // Variable para verificar si se encontró el servicio
        
        //Establecemos el ciclo for para recorrer los pacientes
        for (int i = 0; i < servicios.getLength(); i++) {
            Element servicio = (Element) servicios.item(i); //Establecemos el elemento como el primero de la lista
            
            //Si el obtenemos el atributo id y es igual que el id buscado del servicio como tal
            if (servicio.getAttribute("id").equals(id)) {
                
                //Eliminamos el paciente de la lista enlazada 
                raiz.removeChild(servicio);
                servicioEncontradoElim = true;
                
                //Se sale de la funcion
                break;
                
                //Esto se repite con todos los elementso cliente de la lista enlazada
            }
        }
        
        if (!servicioEncontradoElim) {
            
            System.out.println("Peligro: El servicio con ID " + id + " no existe y no se eliminara.");
        }
    }
    
    //Metodo para modificar los servicios toma como parametro los atributos del paciente
    public void modificarServicios(String id, String nombre_servicio, double precio) {
       
        //Obtenemos los elementos del documento xml
        Element raiz = document.getDocumentElement();
        
        //Mediante una lista de nodo establecemos la raiz del arbol como servicio el tag
        NodeList servicios = raiz.getElementsByTagName("servicio");
        
        boolean servicioEncontrado = false; // Variable para verificar si se encontró el servicio
        
        //Establecemos el ciclo for para recorrer los servicios
        for (int i = 0; i < servicios.getLength(); i++) {
            Element servicio = (Element) servicios.item(i); //Establecemos el elemento como el primero de la lista
            
            //Si obtenemos el atributo id y es igual que el id buscado del servicio como tal
            if (servicio.getAttribute("id").equals(id)) {
                
                // Modificar los elementos del servicio segun el tag
                servicio.getElementsByTagName("nombre_servicio").item(0).setTextContent(nombre_servicio);
                servicio.getElementsByTagName("precio").item(0).setTextContent(String.valueOf(precio));
                
                servicioEncontrado = true;
                
                //Se sale de la funcion
                break;
                
                //Esto se repite con todos los elementos de servicio de la lista enlazada
            }
        }
        
        if (!servicioEncontrado) {
             System.out.println("Peligro: El servicio con ID " + id + " no existe y no se modificara.");
        }
    }
    
    //Fin Metodos para Servicios: Agregar, Eliminar y Modificar
    
    
    //Este metodo de cargar servicios nos ayudara a obtener los servicios ya existentes para luego darselos a los medicos
    //Esto dado a que un medico no puede crear sus propios servicios tienen que venir directamente desde la clase servicios
    public void CargaServicios() {
        
        //Para eso utilizaremos un hashmap que sera capaz de identificar los servicios ya existentes
        serviciosactuales = new HashMap<>();
        
        //Aqui establecemos una raiz del arbol
        Element raiz = document.getDocumentElement();
        
        //Aqui se indica el tag name porque el cual se van a buscar
        NodeList servicios = raiz.getElementsByTagName("servicio");
        
        //Ciclo for que recorre los servicios existentes
        for (int i = 0; i < servicios.getLength(); i++) {
            
            //Se establece element en el inicio del arbol de servicios
            Element servicio = (Element) servicios.item(i);
            
            //Establecemos que solo vamos a indicarle al medico el ID del servicio
            String id = servicio.getAttribute("id");
            
            //Lo agregamos al hashmap
            serviciosactuales.put(id, servicio);
            
        }
        
    }
    
    //Metodos para Medicos: Agregar, Eliminar y Modificar
    public void agregarMedico(String telefono, String puesto, String nombre_medico, String id_m, String id_servicio) { 
        
        //Tomamos un elemento del XML y establecemos una variable del tipo raiz
        Element raiz = document.getDocumentElement();
        
        //Tomamos un elemento de tipo medico y se agrega al documento segun el tag medico
        Element medicos = document.createElement("medico");
        
        //Sucede lo mismo en el caso del ID como la explicacion del nombre
        medicos.setAttribute("id_m", id_m);
        
        //Nuevamente un elemento de tipo nombre es creado y se agrega al documento 
        Element elemNombre_M = document.createElement("nombre_medico");
       
        //A este elemento le vamos a anadir un set content para que modifique lo de dentro
        elemNombre_M.setTextContent(nombre_medico);
        
        //Este elemento nombre se agrega como hijo al elemento padre paciente
        medicos.appendChild(elemNombre_M);
        
        //Sucede lo mismo en el caso del telefono
        Element elemtele = document.createElement("telefono");
        elemtele.setTextContent(telefono);
        medicos.appendChild(elemtele);
        
        //Sucede lo mismo en el caso del puesto
        Element elemPuesto = document.createElement("puesto");
        elemPuesto.setTextContent(puesto);
        medicos.appendChild(elemPuesto);
        
        //Caso de servicios con hashmap, mediante contains key, esto busca en el hashmap los servicios existentes
        if (serviciosactuales.containsKey(id_servicio)) {
            
            //Creamos un elemento de tipo servicios que lo busca por tag name
            Element servicios = document.createElement("servicios");
            
            //Creamos una variable que busque solamente el id 
            Element elemID = document.createElement("id");
            
            //Establecemos que se pueda anadir un id valido de los servicios existentes
            elemID.setTextContent(id_servicio);
            
            //Se anade el elem id a servicios
            servicios.appendChild(elemID);
            
            //Esto se agrega al nodo grande de medicos
            medicos.appendChild(servicios);
            
         }
        
        else {
            System.out.println("Peligro: El servicio con ID " + id_servicio + " no existe y no se agregara al medico.");
            
            // Llamar a eliminarMedico con los parámetros correspondientes
            eliminarMedico(telefono, puesto, nombre_medico, id_m, id_servicio);
            
            return; // Salimos del método porque no se debe agregar el médico
        }
           
        
        //Finalmente se realiza el append del elemento de medicos al elemento raiz que es la base del archivo xml 
        raiz.appendChild(medicos);
    }
    
    
    
    //Metodo para eliminar los medicos toma como parametro los atributos del medico
    public void eliminarMedico(String telefono, String puesto, String nombre_medico, String id_m, String id_servicio) { 
        
        //Obtenemos los elementos del documento xml
        Element raiz = document.getDocumentElement();
        
        //Mediante una lista de nodo establecemos la raiz del arbol como pacientes el tag
        NodeList medicos = raiz.getElementsByTagName("medico");
        
        boolean medicoEncontrado = false; // Variable para verificar si se encontró el medico

        // Establecemos el ciclo for para recorrer los médicos
        for (int i = 0; i < medicos.getLength(); i++) {
            Element medico = (Element) medicos.item(i); // Establecemos el elemento como el primero de la lista
        
            // Si el obtenemos el atributo id y es igual que el id buscado del servicio como tal
            if (medico.getAttribute("id_m").equals(id_m)) {
                // Eliminamos el médico de la lista enlazada 
                raiz.removeChild(medico);
                medicoEncontrado = true; // Marcamos que se encontró y elimino el medico
                
                break; // Salimos del ciclo ya que hemos encontrado y eliminado al medico
            }
        }

        // Verificamos si se encontró el médico después del ciclo
        if (!medicoEncontrado) {
            
        System.out.println("Peligro Inminente: El medico con ID " + id_m + " no existe en los medicos disponibles para borrar.");
        }
    }
    
    //Metodo de modificar los medicos y sus atributos 
    public void modificarMedico(String telefono, String puesto, String nombre_medico, String id_m, String[] ids_servicios) {
        
        // Obtenemos los elementos del documento XML
        Element raiz = document.getDocumentElement();
        
        // Obtener la lista de médicos en el XML
        NodeList medicos = raiz.getElementsByTagName("medico");
        
        // Recorrer los médicos
        for (int i = 0; i < medicos.getLength(); i++) {
            Element medico = (Element) medicos.item(i);
            
            // Si el id del médico coincide con el proporcionado
            if (medico.getAttribute("id_m").equals(id_m)) {
                
                // Modificar los elementos del médico
                medico.getElementsByTagName("nombre_medico").item(0).setTextContent(nombre_medico);
                medico.getElementsByTagName("puesto").item(0).setTextContent(puesto);
                medico.getElementsByTagName("telefono").item(0).setTextContent(telefono);
                
                //Invocamos a la funcion modificarservios_M que nos permite modificar el tag de los servicios ya existentes en la clase servicios
                modificarServicios_M(medico, ids_servicios);
       
            break;
            }
        }
    }
    
    //Metodo que nos permite recorrer y modificar los servicios a los cuales tiene acceso el medico
    private void modificarServicios_M(Element medico, String[] ids_servicios) { //Utilizamos un arraylist para modificar los servicios a los que se accede
        
        // Obtener el elemento servicios del medico como tal
        Element servicios = (Element) medico.getElementsByTagName("servicios").item(0);
    
        // Si no existe el elemento servicios, lo creamos
        if (servicios == null) {
            servicios = document.createElement("servicios");
            medico.appendChild(servicios);
        }
    
        // Obtener la lista de ids de servicio existentes en la clase servicios
        NodeList ids = servicios.getElementsByTagName("id");
    
        // Modificar los ids existentes y agregar nuevos si es necesario esto solo si existen
        for (int i = 0; i < ids_servicios.length; i++) {
            
            //Utilizamos el hashmap antes utilizado para verificar que existan los servicios de la clase
            if (serviciosactuales.containsKey(ids_servicios[i])) {
                
                //Si el contador i es menor que la listas de nodos con el tag id
                if (i < ids.getLength()) {
                    
                    // Modificar id existente
                    ids.item(i).setTextContent(ids_servicios[i]);
                
                //En caso contrario cree uno nuevo que ya exista segun el hashmap
                } else {
                    
                    // Agregar nuevo id
                    Element idnuevo = document.createElement("id");
                    idnuevo.setTextContent(ids_servicios[i]);
                    servicios.appendChild(idnuevo);
                }
            
            //Si no se reconoce el id del servicio va a tirar un error
            } else {
            System.out.println("Peligro Inminente: El servicio con ID " + ids_servicios[i] + " no existe en los servicios disponibles para eliminarlo en el medico deseado.");
            
            }
        }
    
        // Eliminar ids sobrantes si hay más ids existentes que nuevos
        while (ids.getLength() > ids_servicios.length) {
            servicios.removeChild(ids.item(ids.getLength() - 1));
        }
    }

    
    
    //Metodo para guardar los cambios en el XML
    public void guardarXML(String nombreArchivo) throws TransformerException {
        
        //Inicializamos las instancias transformer para modificar el XML
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        
        // Configurar la salida XML con las propiedades del XML
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
        
        // Establecemos una identacion de 2 para que no haya espacios en el XML
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        
        // Eliminar espacios en blanco innecesarios
        document.normalize(); //Normalizamos el documento segun las propiedades del XML
        QuitarEsp(document.getDocumentElement()); //Utilizamos la funcion que borra ese espacio
        
        //DOMSource que establece la instancia del nuevo documento guardado
        DOMSource source = new DOMSource(document);
        
        //Esto se utiliza para la interpretacion del archivo de salida
        StreamResult result = new StreamResult(new File(nombreArchivo));
        
        //Transformacion que guarda el documento modificado en el archivo XML
        transformer.transform(source, result);
    }
    
    
    //Metodo privado
    private void QuitarEsp(Element element) { //Toma como parametro un elemento
        
        NodeList hijos = element.getChildNodes(); //Obtenemos todos los nodos hijos del XML
        
        //Para todos los hijos del documento XML o todos los nodos que guardan caracteres, vaya hasta que no recorre todos de abajo a arriba
        for (int i = hijos.getLength() - 1; i >= 0; i--) {
            
            //Iniciamos con la funcion dom node de manipulacion de objetos a hijos dos como el primer elemenyo de la lista
            org.w3c.dom.Node hijos2 = hijos.item(i);
            
            //Si hijos 2 es una instacia de elememtos con texto y el text no tiene nada
            if (hijos2 instanceof org.w3c.dom.Text && hijos2.getTextContent().trim().isEmpty()) {
                element.removeChild(hijos2); //Se elimna el nodo de la lista enlazada para borrar el espacio, es como si el auxiliar borrase una linea de caracteres invicibles
            
            //En caso de que verdaderamente hijos 2 sea un elemento
            } else if (hijos2 instanceof Element element1) {
                
                //No haga nada solo siga invocando a la funcion hasta que se borren todos los espacios en blanco
                QuitarEsp(element1);
            }
        }
    }

    
}
  
