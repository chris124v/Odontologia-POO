/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

/* Esta java class de XMLWriter corresponde a la clase que modifica, agrega y elemina los pacientes,
servicios y medicos esto mediante el uso del parser DOM de modificacion de archivos por arboles binarios.
*/

//Import que maneja el documento de XML
import Conceptos.Medicos;
import Conceptos.Paciente;
import Conceptos.Servicio;
import java.io.File;
import java.util.List;

//Manejo de errores de lectura y escritura en archivos XML
import java.io.IOException;
import java.util.ArrayList;
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
    
    
    //Metodo para agregar los pacientes toma como el objeto pacientes
    public void agregarPaciente(Paciente paciente) { 
        
        
        //Tomamos un elemento del XML y establecemos una variable del tipo raiz
        Element raiz = document.getDocumentElement();
        
        //Creamos un objeto de tipo paciente con valores vacios y un getID para el atributo
        //En este caso seria la creacion de una instancia
        Paciente nuevoPaci = new Paciente(paciente.getId(), "", "","");
        
        //Utilizamos el metodo set para establecer los atributos del nuevo paciente
        nuevoPaci.setNombre(paciente.getNombre());
        nuevoPaci.setTelefono(paciente.getTelefono());
        nuevoPaci.setEmail(paciente.getEmail());
        
        //Tomamos un elemento de tipo paciente y se agrega al documento segun el tag paciente
        Element pacientexml = document.createElement("paciente");
        
        
        //Sucede lo mismo en el caso del ID como la explicacion del nombre
        pacientexml.setAttribute("id", nuevoPaci.getId());
        
        //Nuevamente un elemento de tipo nombre es creado y se agrega al documento 
        Element elemNombre = document.createElement("nombre");
       
        //El contenido del texto del elemento elemNombre se establece mediante el metodo get del objeto para establcerlo en el XML
        elemNombre.setTextContent(nuevoPaci.getNombre());
        
        //Este elemento nombre se agrega como hijo al elemento padre paciente para el xml
        pacientexml.appendChild(elemNombre);
        
        //Sucede lo mismo en el caso del telefono
        Element elemtele = document.createElement("telefono");
        elemtele.setTextContent(nuevoPaci.getTelefono());
        pacientexml.appendChild(elemtele);
        
        //Sucede lo mismo con el email
        Element elemEmail = document.createElement("email");
        elemEmail.setTextContent(nuevoPaci.getEmail());
        pacientexml.appendChild(elemEmail);
        
        //Finalmente se realiza el append del elemento de pacientes al elemento raiz que es la base del archivo xml  pasandolo como un objeto
        raiz.appendChild(pacientexml);
    }
    
    //Metodo para eliminar los pacientes toma como parametro los atributos del paciente
    public void eliminarPaciente(Paciente paciente) { 
        
        //Obtenemos los elementos del documento xml
        Element raiz = document.getDocumentElement();
        
        //Mediante una lista de nodo establecemos la raiz del arbol como pacientes el tag
        NodeList pacientes = raiz.getElementsByTagName("paciente");
        
        boolean pacienteElim = false;
        
        //Establecemos el ciclo for para recorrer los pacientes
        for (int i = 0; i < pacientes.getLength(); i++) {
            
            Element pacientexml = (Element) pacientes.item(i); //Establecemos el elemento como el primero de la lista
            
            //Si el obtenemos el atributo id y es igual que el id buscado del paciente instanciado como tal
            if (pacientexml.getAttribute("id").equals(paciente.getId())) {
                
                    //Eliminamos el paciente de la lista enlazada osea el objeto
                    raiz.removeChild(pacientexml);
                
                    pacienteElim = true;
                    
                    //Se sale de la funcion
                    break;
                
                //Esto se repite con todos los elementso cliente de la lista enlazada
            }
        }
        
        //Caso de que no se encuentre
        if (!pacienteElim) {
            
            System.out.println("Peligro: El paciente con ID " + paciente.getId() + " no existe y no se eliminara");
        }
    }
            
    //Metodo para modificar los pacientes toma como parametro los atributos del paciente
    public void modificarPaciente(Paciente paciente) {
       
        //Obtenemos los elementos del documento xml
        Element raiz = document.getDocumentElement();
        
        //Mediante una lista de nodo establecemos la raiz del arbol como pacientes el tag
        NodeList pacientes = raiz.getElementsByTagName("paciente");
        
        boolean pacienteEncontrado = false;
        
        //Establecemos el ciclo for para recorrer los pacientes
        for (int i = 0; i < pacientes.getLength(); i++) {
            
            Element pacientexml = (Element) pacientes.item(i); //Establecemos el elemento como el primero de la lista
            
            //Si el obtenemos el atributo id y es igual que el id buscado del paciente como tal
            if (pacientexml.getAttribute("id").equals(paciente.getId())) {
                
                // Modificar los elementos del paciente segun el tag
                pacientexml.getElementsByTagName("nombre").item(0).setTextContent(paciente.getNombre());
                pacientexml.getElementsByTagName("telefono").item(0).setTextContent(paciente.getTelefono());
                pacientexml.getElementsByTagName("email").item(0).setTextContent(paciente.getEmail());
                
                pacienteEncontrado = true;
                
                //Se sale de la funcion
                break;
                
                //Esto se repite con todos los elementso cliente de la lista enlazada
            }
        }
        
        if (!pacienteEncontrado) {
            System.out.println("Peligro: El paciente con ID " + paciente.getId() + " no existe y no se modificara.");
        }
    }
    
    //Fin Metodos para Pacientes: Agregar, Eliminar y Modificar
    
    
    
    //Metodos para Servicios: Agregar, Eliminar y Modificar
    
    
    
    //Metodo para agregar un servicio al XML meduiante un arbol para el xml y instancias para la creacion del objeto
    public void agregarServicio(Servicio servicio) { 
        
        
        //Tomamos un elemento del XML y establecemos una variable del tipo raiz
        Element raiz = document.getDocumentElement();
        
        //Creacion de un objeto paciente para la posteriormente ser capaz de cambiar sus atributos
        Servicio servicioNuevo = new Servicio(servicio.getId(), "", 0);
        
        //Metodos set para agregar los nuevos valores a los pacientes
        servicioNuevo.setNombre_servicio(servicio.getNombre_servicio());
        servicioNuevo.setPrecio(servicio.getPrecio());
        
        //Tomamos un elemento de tipo paciente y se agrega al documento segun el tag paciente
        Element servicioxml = document.createElement("servicio");
        
        //Sucede lo mismo en el caso del ID como la explicacion del nombre pero con el id y el get del objeto
        servicioxml.setAttribute("id", servicioNuevo.getId());
        
        //Nuevamente un elemento de tipo nombre es creado y se agrega al documento 
        Element elemNombre_S = document.createElement("nombre_servicio");
       
        //A este elemento le vamos a anadir un set content para que modifique lo de dentro
        elemNombre_S.setTextContent(servicioNuevo.getNombre_servicio());
        
        //Este elemento nombre se agrega como hijo al elemento padre paciente
        servicioxml.appendChild(elemNombre_S);
        
        //Sucede lo mismo en el caso del telefono
        Element elemPrecio = document.createElement("precio");
        elemPrecio.setTextContent(String.valueOf(servicio.getPrecio()));
        servicioxml.appendChild(elemPrecio);
        
        //Finalmente se realiza el append del elemento de pacientes al elemento raiz que es la base del archivo xml 
        raiz.appendChild(servicioxml);
    }
    
    //Metodo para eliminar los servicios toma como parametro los atributos del servicio
    public void eliminarServicio(Servicio servicio) { 
        
        //Obtenemos los elementos del documento xml
        Element raiz = document.getDocumentElement();
        
        //Mediante una lista de nodo establecemos la raiz del arbol como pacientes el tag
        NodeList servicios = raiz.getElementsByTagName("servicio");
        
        boolean servicioEncontradoElim = false; // Variable para verificar si se encontró el servicio
        
        //Establecemos el ciclo for para recorrer los pacientes
        for (int i = 0; i < servicios.getLength(); i++) {
            
            Element servicioxml = (Element) servicios.item(i); //Establecemos el elemento como el primero de la lista
            
            //Si el obtenemos el atributo id y es igual que el id buscado del servicio como tal creado en la instancia
            if (servicioxml.getAttribute("id").equals(servicio.getId())) {
                
                //Eliminamos el paciente de la lista enlazada 
                raiz.removeChild(servicioxml);
                servicioEncontradoElim = true;
                
                //Se sale de la funcion
                break;
                
                //Esto se repite con todos los elementso cliente de la lista enlazada
            }
        }
        
        if (!servicioEncontradoElim) {
            
            System.out.println("Peligro: El servicio con ID " + servicio.getId() + " no existe y no se eliminara.");
        }
    }
    
    //Metodo para modificar los servicios toma como parametro los atributos del paciente
    public void modificarServicios(Servicio servicio) {
       
        //Obtenemos los elementos del documento xml
        Element raiz = document.getDocumentElement();
        
        //Mediante una lista de nodo establecemos la raiz del arbol como servicio el tag
        NodeList servicios = raiz.getElementsByTagName("servicio");
        
        boolean servicioEncontrado = false; // Variable para verificar si se encontró el servicio
        
        //Establecemos el ciclo for para recorrer los servicios
        for (int i = 0; i < servicios.getLength(); i++) {
            Element servicioxml = (Element) servicios.item(i); //Establecemos el elemento como el primero de la lista
            
            //Si obtenemos el atributo id y es igual que el id buscado del servicio como tal
            if (servicioxml.getAttribute("id").equals(servicio.getId())) {
                
                // Modificar los elementos del servicio segun el tag
                servicioxml.getElementsByTagName("nombre_servicio").item(0).setTextContent(servicio.getNombre_servicio());
                servicioxml.getElementsByTagName("precio").item(0).setTextContent(String.valueOf(servicio.getPrecio()));
                
                servicioEncontrado = true;
                
                //Se sale de la funcion
                break;
                
                //Esto se repite con todos los elementos de servicio de la lista enlazada
            }
        }
        
        if (!servicioEncontrado) {
             System.out.println("Peligro: El servicio con ID " + servicio.getId() + " no existe y no se modificara.");
        }
    }
    
    //Fin Metodos para Servicios: Agregar, Eliminar y Modificar
    
    
    //Este metodo de cargar servicios nos ayudara a obtener los servicios ya existentes para luego darselos a los medicos
    //Esto dado a que un medico no puede crear sus propios servicios tienen que venir directamente desde la clase servicios
    public List<Servicio> CargaServicios() {
        
        // Para eso utilizaremos un hashmap que será capaz de identificar los servicios ya existentes
        serviciosactuales = new HashMap<>();

        // Aquí establecemos una raíz del árbol
        Element raiz = document.getDocumentElement();

        // Aquí se indica el tag name por el cual se van a buscar
        NodeList servicios = raiz.getElementsByTagName("servicio");

        // Lista para almacenar los servicios
        List<Servicio> listaServicios = new ArrayList<>();

        // Ciclo for que recorre los servicios existentes
        for (int i = 0; i < servicios.getLength(); i++) {
            // Se establece element en el inicio del árbol de servicios
            Element servicio = (Element) servicios.item(i);

            // Establecemos que solo vamos a indicarle al médico el ID del servicio
            String id = servicio.getAttribute("id");
        
            // Crear un objeto Servicio (asegúrate de tener un constructor adecuado)
            Servicio nuevoServicio = new Servicio();
            nuevoServicio.setId(id);
            nuevoServicio.setNombre_servicio(servicio.getTextContent()); // O cualquier otro atributo que necesites

            // Lo agregamos al hashmap
            serviciosactuales.put(id, servicio);
        
            // Agregar a la lista de servicios
            listaServicios.add(nuevoServicio);
        }

        return listaServicios; // Retornar la lista de servicios
    }
    
    //Metodos para Medicos: Agregar, Eliminar y Modificar
    //Tomamos como parametro el objeto 
    public void agregarMedico(Medicos medico, List<String> serviciosSeleccionado) { 
        
        //Tomamos un elemento del XML y establecemos una variable del tipo raiz
        Element raiz = document.getDocumentElement();
        
        //Tomamos un elemento de tipo medico y se agrega al documento segun el tag medico
        Element medicos = document.createElement("medico");
        
        //Sucede lo mismo en el caso del ID como la explicacion del nombre, usamos get para obtener el id 
        medicos.setAttribute("id_m", medico.getId_m());
        
        //Nuevamente un elemento de tipo nombre es creado y se agrega al documento 
        Element elemNombre_M = document.createElement("nombre_medico");
       
        //A este elemento le vamos a anadir un set content para que modifique lo de dentro junto con el get
        elemNombre_M.setTextContent(medico.getNombre_medico());
        
        //Este elemento nombre se agrega como hijo al elemento padre paciente
        medicos.appendChild(elemNombre_M);
        
        //Sucede lo mismo en el caso del telefono
        Element elemtele = document.createElement("telefono");
        elemtele.setTextContent(medico.getTelefono());
        medicos.appendChild(elemtele);
        
        //Sucede lo mismo en el caso del puesto
        Element elemPuesto = document.createElement("puesto");
        elemPuesto.setTextContent(medico.getPuesto());
        medicos.appendChild(elemPuesto);
        
        //Se establece para verificar que si existe
        boolean servicioExistente = false;
        
        Element servicios = document.createElement("servicios");
        
        // Recorremos la lista de IDs de servicios con un get de servicios
       for (String id : serviciosSeleccionado) {
           
            // Verificamos si el servicio con el id existe en el HashMap
            if (serviciosactuales.containsKey(id)) {
                
                servicioExistente = true; // Se encontró al menos un servicio existente
                
                //Anadimos el elemento al xml 
                Element elemID = document.createElement("id");
                elemID.setTextContent(id);
                
                //Lo agregamos a servicios
                servicios.appendChild(elemID);
            
            //Mensaje en caso de que no exista el servicio y no se agrega al medico
            } else {
                System.out.println("Peligro: El servicio con ID " + id + " no existe y no se agregara al medico.");
            }
        }
    
        // Solo agregamos el elemento servicios si se encontraron servicios válidos
        if (servicioExistente) {
            medicos.appendChild(servicios);
    
        //No se agrega nada
        } else {
            System.out.println("No se agregara el médico porque no hay servicios válidos.");
            return; // Salimos del método si no se encontraron servicios válidos
        }
    
        // Finalmente, se realiza el append del elemento de medicos al elemento raiz
        raiz.appendChild(medicos);
    }
    
    
    
    //Metodo para eliminar los medicos toma como la clase medico
    public void eliminarMedico(Medicos medico) { 
        
        //Obtenemos los elementos del documento xml
        Element raiz = document.getDocumentElement();
        
        //Mediante una lista de nodo establecemos la raiz del arbol como pacientes el tag
        NodeList medicos = raiz.getElementsByTagName("medico");
        
        boolean medicoEncontrado = false; // Variable para verificar si se encontró el medico

        // Establecemos el ciclo for para recorrer los médicos
        for (int i = 0; i < medicos.getLength(); i++) {
            Element medicosxml = (Element) medicos.item(i); // Establecemos el elemento como el primero de la lista
        
            // Si el obtenemos el atributo id y es igual que el id buscado del servicio como tal tomando en cuenta el get de idm 
            if (medicosxml.getAttribute("id_m").equals(medico.getId_m())) {
                
                // Eliminamos el médico de la lista enlazada 
                raiz.removeChild(medicosxml);
                medicoEncontrado = true; // Marcamos que se encontró y elimino el medico
                
                break; // Salimos del ciclo ya que hemos encontrado y eliminado al medico
            }
        }

        // Verificamos si se encontró el médico después del ciclo
        if (!medicoEncontrado) {
            
        System.out.println("Peligro Inminente: El medico con ID " + medico.getId_m() + " no existe en los medicos disponibles para borrar.");
        }
    }
    
    //Metodo de modificar los medicos y sus atributos 
    public void modificarMedico(Medicos medico) {
        
        // Obtenemos los elementos del documento XML
        Element raiz = document.getDocumentElement();
        
        // Obtener la lista de médicos en el XML
        NodeList medicos = raiz.getElementsByTagName("medico");
        
        //Esto nos servira en caso de que no se encuentre
        boolean medicoEncontrado = false;
        
        // Recorrer los médicos
        for (int i = 0; i < medicos.getLength(); i++) {
            Element medicoxml = (Element) medicos.item(i);
            
            // Si el id del médico coincide con el proporcionado
            if (medicoxml.getAttribute("id_m").equals(medico.getId_m())) {
                
                // Modificar los elementos del médico
                medicoxml.getElementsByTagName("nombre_medico").item(0).setTextContent(medico.getNombre_medico());
                medicoxml.getElementsByTagName("puesto").item(0).setTextContent(medico.getPuesto());
                medicoxml.getElementsByTagName("telefono").item(0).setTextContent(medico.getTelefono());
                
                //Establecemos un array para obtener los servicios existentes al medico
                String[] serviciosArray = medico.getServicios().toArray(new String[0]);
                
                //Invocamos a la funcion modificarservios_M que nos permite modificar el tag de los servicios ya existentes en la clase servicios con un arraylist de la clase medicos
                modificarServicios_M(medicoxml, serviciosArray);
                
                //Cambiamos el estado 
                medicoEncontrado = true;
       
            break;
            }
        }
        
        //Si no se encontro manda un mensaje de error
        if (!medicoEncontrado) {
            System.out.println("Peligro inminente: El medico con ID " + medico.getId_m() + "no existe y no se modifica");
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
  
