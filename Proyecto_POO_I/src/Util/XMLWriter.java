/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Util;

/* Esta java class de XMLWriter corresponde a la clase que modifica, agrega y elemina los pacientes,
servicios y medicos esto mediante el uso del parser DOM de modificacion de archivos por arboles binarios. Ademas de realizar el agregado
de las solicitudes medicas que necesiten los pacientes
*/

//Import que maneja el documento de XML
import Conceptos.Estado;
import Conceptos.Medicos;
import Conceptos.Paciente;
import Conceptos.Servicio;
import Conceptos.Solicitud;
import java.io.File;
import java.util.List;

//Manejo de errores de lectura y escritura en archivos XML
import java.io.IOException;
import java.time.format.DateTimeFormatter;
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
import org.w3c.dom.Node;
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
        
        //Creamos un objeto de tipo paciente con valores vacios y un getid para el atributo
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
    
        //No se agrega nada usamos un system out para probar
        } else {
            System.out.println("No se agregara el medico porque no hay servicios validos.");
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
                medicoEncontrado = true; // Marcamos que se encontro y elimino el medico
                
                break; // Salimos del ciclo ya que hemos encontrado y eliminado al medico
            }
        }

        // Verificamos si se encontró el medico después del ciclo
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
    
    /*Los siguientes metodos de escritura van a funcionar para solicitudes*/
    
    //Inicializamos el hashmap de paciente para guardar alli los objetos de pacientes ya existentes
    Map<String, Paciente> pacientes_existentes = new HashMap<>();
    
    //Verifiacion de los pacientes existentes
    public List<Paciente> verificacion_pacientes() {
        
        //Iniciamos el elemento raiz
        Element raiz = document.getDocumentElement();
        
        //Inicializamos el tag por el cual los va a buscar
        NodeList pacientes = raiz.getElementsByTagName("paciente");
        
        //Creamos un arraylist que los va a guardar
        List<Paciente> lista_pacientes = new ArrayList<>();
        
        //Ciclo for para recorrer el arbol
        for (int i = 0; i < pacientes.getLength(); i++) {
            
            //Inicializamos el objeto paciente
            Element paciente = (Element) pacientes.item(i);
            
            //Obtenemos el id
            String id = paciente.getAttribute("id");
            
            //Creamos el objeto
            Paciente paciente_actual = new Paciente();
            
            //Le establecemos como objeto los atributos que le pertenecen
            paciente_actual.setId(id);
            paciente_actual.setNombre(paciente.getElementsByTagName("nombre").item(0).getTextContent());
            paciente_actual.setTelefono(paciente.getElementsByTagName("telefono").item(0).getTextContent());
            paciente_actual.setEmail(paciente.getElementsByTagName("email").item(0).getTextContent());
            
            //En el hashmap agregamos el paciente
            pacientes_existentes.put(id, paciente_actual);
            
            //Agregamos el objeto al hashmap
            lista_pacientes.add(paciente_actual);
        }
        
        //Retorna la lista de los paciente
        return lista_pacientes;
    }
    
    //Creamos el hashmap necesario para recorrer los servicios
    Map<String, Servicio> servicios_existentes = new HashMap<>();
    
    //Metodo para cargar los servicios existentes
    public List<Servicio> verificacion_servicios() {
        
        //Establecemos un elemento raiz para recorrerlo
        Element raiz = document.getDocumentElement();
        
        //Establecemos un nodelist por el cual se van a recorrer los servicios existentes por el tag servicio
        NodeList servicios = raiz.getElementsByTagName("servicio");
        
        //Creamos el arraylist donde tendremos la lista de los servicios
        List<Servicio> lista_servicios = new ArrayList<>();
        
        // For que recorre el nodelist de servicios
        for (int i = 0; i < servicios.getLength(); i++) {
            
            //Establecemos el elemento inicial
            Element servicio = (Element) servicios.item(i);
            String id = servicio.getAttribute("id");

            // Crear objeto servicio y establecer los atributos
            Servicio nuevoServicio = new Servicio();
            
            //Establecemos los atributos
            nuevoServicio.setId(id);
            nuevoServicio.setNombre_servicio(servicio.getElementsByTagName("nombre_servicio").item(0).getTextContent());
            nuevoServicio.setPrecio(Double.parseDouble(servicio.getElementsByTagName("precio").item(0).getTextContent()));

            // Agregar al hashmap y a la lista
            servicios_existentes.put(id, nuevoServicio);
            lista_servicios.add(nuevoServicio);
        }
        
        //Retornamos la lista de los servicios cargados
        return lista_servicios;
    }
    
    
    //Metodo para crear una solicitud inicial
    
    /*Aqui tomamos en cuenta que inicialmente la solicitud solo tomara los objetos de "Paciente", "Servicio"
    como los ya existentes mientras que en medicos, otros_servicios y estado solo es necesario obtener 
    el tag inicial esto posteriormente se hara en el metodo de atender solicitudes, en donde si verificamos
    el estado, medicos y otros servicios. Por ende aqui para esos elementos mencionados anteriormente solo 
    crearemos su espacio en el xml
    */
    
    //Metodo para crear la solicitud
    public void crearSolicitud(Solicitud solicitud) {
        
        // Obtener el elemento raíz del XML
        Element raiz = document.getDocumentElement();

        // Crear un nuevo elemento de solicitud
        Element solicitud_nueva = document.createElement("solicitud");
        solicitud_nueva.setAttribute("id", solicitud.getId());

        // Establecer la fecha de la solicitud, para eso llamamos al formato usado en la clase
        Element fecha_hora = document.createElement("fecha_hora");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        fecha_hora.setTextContent(solicitud.getFecha_hora().format(formatter)); 
        solicitud_nueva.appendChild(fecha_hora);

        // Validar y agregar servicio usando el hashmap, mientras el get sea diferente de null
        if (solicitud.getTipo_servicio() != null) {
            
            //Establecemos un string que va a obtener directamente el id del servicio, aqui llamamos a metodos de servicios
            String servicioId = solicitud.getTipo_servicio().getId();
            
            //Si justamente el hashmap tiene ese id del servicio
            if (servicios_existentes.containsKey(servicioId)) {
                
                //Establecemos una variable de tipo servicio para obtener justamente el servicio escogido
                Servicio servicio = servicios_existentes.get(servicioId);
                
                //Creamos un elemento de servicio en el xml
                Element servicio_elemento = document.createElement("servicio");
                
                //Primero establecemos el atributo del id del servicio
                servicio_elemento.setAttribute("id_s", servicio.getId());
                
                //Posteriormente creamos los elementos respectivos como el nombre y precio
                Element nombre_s = document.createElement("nombre_servicio");
                nombre_s.setTextContent(servicio.getNombre_servicio());
                servicio_elemento.appendChild(nombre_s);
                
                //Este seria el precio
                Element precioServicioElem = document.createElement("precio");
                precioServicioElem.setTextContent(String.valueOf(servicio.getPrecio()));
                servicio_elemento.appendChild(precioServicioElem);
                
                //Agregamos el servicio directamente a la solicitud
                solicitud_nueva.appendChild(servicio_elemento);
            
            //Este else lo hacemos en caso de que dicho servicio ingresado no existe en el hashmap
            } else {
                System.out.println("Error: Servicio con ID " + servicioId + " no encontrado. Peligro Inminente");
            }
        }

        // Validar y agregar paciente funciona de manera igual a servicios en este caso dado que para crear si necesitamos el id de la persona
        if (solicitud.getId_paciente() != null) {
            
            //Establecemos una variable que guardara al paciente
            String pacienteId = solicitud.getId_paciente().getId();
            
            //Si en el hashmap se encuentra el id de esta persona
            if (pacientes_existentes.containsKey(pacienteId)) {
                
                //Establecemos un objeto de tipo paciente que obtenga la variable contenida que tiene la info del paciente
                Paciente paciente = pacientes_existentes.get(pacienteId);
                
                //Aqui creamos el tag inicial de paciente
                Element paciente_elemento = document.createElement("Paciente");
                
                //Establecemos primero como atributo el id
                paciente_elemento.setAttribute("id_p", paciente.getId());
                
                //Posteriormente creamos un elemento que cree el tag del nombre y obtenemos el nombre con get
                Element nombre_p = document.createElement("nombre_p");
                nombre_p.setTextContent(paciente.getNombre());
                paciente_elemento.appendChild(nombre_p);
                
                //Aqui hacemos lo mismo con el telefono
                Element telefono_p = document.createElement("telefono_p");
                telefono_p .setTextContent(paciente.getTelefono());
                paciente_elemento.appendChild(telefono_p );
                
                //Hacemos lo mismo con el email del paciente
                Element emailPacienteElem = document.createElement("email");
                emailPacienteElem.setTextContent(paciente.getEmail());
                paciente_elemento.appendChild(emailPacienteElem);
                
                //Agregamos el paciente a la solicitud
                solicitud_nueva.appendChild(paciente_elemento);
            
            //Else en caso de que no encontremos el paciente en el hashmap
            } else {
                System.out.println("Error: Paciente con ID " + pacienteId + " no encontrado. Peligro Inminente");
            }
        }
        
        // Caso de agregar el Medico en este caso solo vamos a agregar un espacio en blanco dado a que esto se hace en atender pero aqui por el momento dejamos el tag hecho
        Element medico_elemento = document.createElement("Medico");
        
        //Establecemos el atributo de id del medico y lo instanciamos como espacio en blanco
        medico_elemento.setAttribute("id_m", solicitud.getMedico() != null ? solicitud.getMedico().getId() : " ");
        
        //Aqui creamos el elemento del nombre del medico y mientras sea diferente de nulo ponemos un espacio en blanco y usamos el metodo get
        Element nombre_m = document.createElement("nombre_medico");
        nombre_m.setTextContent(solicitud.getMedico() != null ? solicitud.getMedico().getNombre_medico() : " ");
        medico_elemento.appendChild(nombre_m);
        
        //Sucede lo mismo para el puesto del medico
        Element puesto_m = document.createElement("puesto");
        puesto_m.setTextContent(solicitud.getMedico() != null ? solicitud.getMedico().getPuesto() : " ");
        medico_elemento.appendChild(puesto_m);
        
        //Tambien pasa lo mismo con el telefono del medico
        Element telefono_m = document.createElement("telefono_m");
        telefono_m.setTextContent(solicitud.getMedico() != null ? solicitud.getMedico().getTelefono() : " ");
        medico_elemento.appendChild(telefono_m);
        
        //Aqui viene la parte de los servicios a los que se dedica el medico
        Element servicios_me = document.createElement("servicios_m");
        
        //Mientras el medico no sea inexistente o no tenga nada y hayan servicios 
        if (solicitud.getMedico() != null && solicitud.getMedico().getServicios() != null) {
            
            //En este ciclo for obtenemos los servicios del medico pero no inicializamos nada solo dejamos el espacio hecho
            for (String id_servi : solicitud.getMedico().getServicios()) {
                Element elemento_se = document.createElement("id_sl");
                elemento_se.setTextContent(id_servi);
                servicios_me.appendChild(elemento_se);
            }
        
        //En caso de que haya medicos creamos el id de los servicios que proove por si hay algun error, nuevamente con el espacio en blanco
        } else {
            Element id_servici = document.createElement("id_sl");
            id_servici.setTextContent(" ");
            servicios_me.appendChild(id_servici);
        }
        
        //Anadimos los servicios al medico
        medico_elemento.appendChild(servicios_me);
        
        //Anadimos el medico a la solicitud
        solicitud_nueva.appendChild(medico_elemento);
        

        // Crear el estado o inicializarlo siempre en nuevo
        if (solicitud.getEstado() != null) {
            
            //Creamos el tag del estado
            Element estado_elemento = document.createElement("estado");
            
            //Establecemos primeramente el atributo del id
            estado_elemento.setAttribute("id_e", solicitud.getEstado().getId());
            
            //Creamos el tag del nombre
            Element nombre_e = document.createElement("nombre_e");
            nombre_e.setTextContent(solicitud.getEstado().getNombre());
            
            //Anadimos el nombre del estado al elemento
            estado_elemento.appendChild(nombre_e);
            
            //Este estado se pone en la solicitud
            solicitud_nueva.appendChild(estado_elemento);
        }
        
        // Agregar las observaciones de la solicitud nada mas creamos el tag
        Element observacionesElem = document.createElement("observaciones");
        observacionesElem.setTextContent(solicitud.getObservaciones() != null ? solicitud.getObservaciones() : " ");
        solicitud_nueva.appendChild(observacionesElem);

        // Crear los otros servicios solo el tag nuevamente 
        Element otros_serviciii = document.createElement("otros_servicios");
        
        //Mientras la lista no este vacia o sea diferente de null
        if (solicitud.getOtros_servicios() != null && !solicitud.getOtros_servicios().isEmpty()) {
            
            //Creamos un objeto de servicio
            for (Servicio otro_servicio : solicitud.getOtros_servicios()) {
                
                //Definimos solamente el id
                Element otrooooo = document.createElement("id_lista_servi");
                otrooooo.setTextContent(otro_servicio.getId());
                otros_serviciii.appendChild(otrooooo);
            }
        
        //En caso de que la lista no este vacia
        } else {
            
            //Tambien anadimos el id del servicio esto es en caso de error y nuevamente con espacio en blanco
            Element otrosssss = document.createElement("id_lista_servi");
            otrosssss.setTextContent(" ");
            otros_serviciii.appendChild(otrosssss);
        }
        
        //Anadimos estos tag inicializados a la solicitud
        solicitud_nueva.appendChild(otros_serviciii);

        // Finalmente agregamos toda la solicitud a la raiz
        raiz.appendChild(solicitud_nueva);
    }
    
    //Aqui iniciariamos con los metodos de atender para modificar el medico, estado, otros servicios y seleccionar la solicitud
    
    // HashMap para almacenar los medicos existentes
    Map<String, Medicos> medicos_existentes = new HashMap<>();
    
    //Metodo para verificar los medicos inicializados como objetos
    public List<Medicos> verificacion_medicos() {
        
        //Obtenemos el elemento raiz
        Element raiz = document.getDocumentElement();
        
        //Buscamos a los que tengan el tag medico
        NodeList medicos = raiz.getElementsByTagName("medico");
        
        //Creamos un arraylist para guardar alli los medicos
        List<Medicos> lista_medicos = new ArrayList<>();

        // Iterar sobre los elementos medico en el XML
        for (int i = 0; i < medicos.getLength(); i++) {
            
            //Guardamos en un elemento la iteracion
            Element medico_elemento = (Element) medicos.item(i);
            
            //Primero establecemos el atributo del id
            String id = medico_elemento.getAttribute("id_m");

            // Crear y configurar el objeto medico
            Medicos medico = new Medicos();
            
            //Le asignamos a este objeto todo los atributos que posee
            medico.setId_m(id);
            medico.setNombre_medico(medico_elemento.getElementsByTagName("nombre_medico").item(0).getTextContent());
            medico.setTelefono(medico_elemento.getElementsByTagName("telefono").item(0).getTextContent());
            medico.setPuesto(medico_elemento.getElementsByTagName("puesto").item(0).getTextContent());

            // Obtener los servicios del medico y asignarlos
            NodeList servicios = medico_elemento.getElementsByTagName("id");
            
            //Para ello los guradamos en un arrtaylist
            List<String> servicios_del_medico = new ArrayList<>();
            
            //Aqui iteramos sobre los servicios que posee el medico
            for (int j = 0; j < servicios.getLength(); j++) {
                //Agregamos el servicio a la rama del servicio como tal
                servicios_del_medico.add(servicios.item(j).getTextContent());
            }
            
            //Usamos el set servicios para asignarlos al medico
            medico.setServicios(servicios_del_medico);

            // Agregar al hashmap y a la lista
            medicos_existentes.put(id, medico);
            lista_medicos.add(medico);
        }
        
        //Retornamos la lista de medicos 
        return lista_medicos;
    }
    
    //Metodo para verificar que el estado existe
    Map<String, Estado> estados_existentes = new HashMap<>();
    
    //Metodo para verificar los estados existentes
    public List<Estado> verificacion_estados() {
        
        //Inicializamos la raiz del documento como tal
        Element raiz = document.getDocumentElement();
        
        //Buscamos en el xml lo que coincida con estado
        NodeList estados = raiz.getElementsByTagName("estado");
        
        //Creamos un arraylist que guarde todos los estados
        List<Estado> lista_estados = new ArrayList<>();
    
        // Iterar sobre los elementos estado en el XML
        for (int i = 0; i < estados.getLength(); i++) {
            
            //Establecemos el elemento donde guardamos los estados para el xml
            Element estado_elemento = (Element) estados.item(i);
            
            //Definimos el atributo ID del estado
            String id = estado_elemento.getAttribute("id");
        
            // Crear y configurar el objeto Estado
            Estado estado = new Estado();
            
            //Establecemos el id al objeto
            estado.setId(id);
        
            // Verificar que existe el estado y lo buscamos por su nombre
            NodeList nombre_elemento = estado_elemento.getElementsByTagName("nombre");
            
            //Si el nombre que existe es diferente de null (osea que no hay) y es mayor que 0
            if (nombre_elemento != null && nombre_elemento.getLength() > 0) {
                
                //Establecemos el nodo en el que se va a iterar
                Node nombre_iterarer = nombre_elemento.item(0);
                
                //Si es diferente de null entonces vamos a obtener el text que hay en el xml y se lo pasamos al objeto estado
                if (nombre_iterarer != null) {
                    estado.setNombre(nombre_iterarer.getTextContent());
                
                //Caso de else
                } else {
                    
                    // Manejar el caso cuando no hay nombre
                    estado.setNombre("Sin nombre, error. Peligro Inminente"); 
                    System.out.println("Advertencia: Estado con ID " + id + " no tiene nombre. Error maximo");
                }
            
            // Manejar el caso cuando no existe el elemento nombre
            } else {
            estado.setNombre("Sin nombre, error. Peligro Inminente"); 
            System.out.println("Advertencia: Estado con ID " + id + " no tiene elemento nombre. Error maximo");
            }   
        
            // Agregar al Hashmap y a la lista
            estados_existentes.put(id, estado);
            lista_estados.add(estado);
        }
        
        return lista_estados;
    }
    
    /*En este metodo siguiente de atender solicitud lo que logramos es cambiar el estado inicial de la solicitud del paciente
    en donde basicamente le asignamos un medico, otro servicios que requiera, mas observaciones esta vez de parte del medico y
    finalmente el cambio de estado de la solicitud en caso de que se haya completado o siga en revision*/
    
    //Metodo de atender solicitud
    public void atender_solicitud(Solicitud solicitud) {
        
        // Obtener el elemento raíz del XML
        Element raiz = document.getDocumentElement();
        
        //Buscamos y lo establecemos desde la raiz solicitud
        NodeList solicitudes = raiz.getElementsByTagName("solicitud");
        
        //Establecemos una variable booleana
        boolean solicitud_real = false;
    
        // Buscar la solicitud a modificar
        for (int i = 0; i < solicitudes.getLength(); i++) {
            
            //Establecemos un elemento llamado solicitud para que itere sobre el y busque en el xml
            Element solicitud_elemento = (Element) solicitudes.item(i);
            
            //Si el id que vamos a buscar coinicide con la solicitud existente
            if (solicitud_elemento.getAttribute("id").equals(solicitud.getId())) {
                
                //Establecemos el valor booleano como true
                solicitud_real = true;
            
                // Aqui verificamos si el el medico existe
                if (solicitud.getMedico() != null) {
                    
                    //Establecemos un valor string que va a obtener el medico y su id
                    String medico_id = solicitud.getMedico().getId_m();
                    
                    //Si dicho medico existe en el hashmap
                    if (medicos_existentes.containsKey(medico_id)) {
                        
                        //Inicializamos dichho objeto del hashmap
                        Medicos medico = medicos_existentes.get(medico_id);
                        
                        //Aqui establecemos que vamos a buscar el medico por el tag Medico
                        Element medico_elemento = (Element) solicitud_elemento.getElementsByTagName("Medico").item(0);
                        
                        // Todo esto en realidad solo funciona para establecer los datos que tiene el objeto medico al xml y inicializarlo como objeto existente
                        
                        // Actualizar atributos del medico
                        medico_elemento.setAttribute("id_m", medico.getId_m());
                    
                        // Actualizamos los elementos del medico en este caso el nombre por el cual contiene el id
                        Element nombre_med = (Element) medico_elemento.getElementsByTagName("nombre_medico").item(0);
                        nombre_med.setTextContent(medico.getNombre_medico());
                        
                        //Hacemos lo mismo pero para el pusto
                        Element puesto_med = (Element) medico_elemento.getElementsByTagName("puesto").item(0);
                        puesto_med.setTextContent(medico.getPuesto());
                        
                        //Hacemos lo mismo para el telefo
                        Element telefono_med = (Element) medico_elemento.getElementsByTagName("telefono_m").item(0);
                        telefono_med.setTextContent(medico.getTelefono());
                    
                        // Aqui actualizamos los servicios del medico osea lo que ya tiene
                        Element servicios_medico = (Element) medico_elemento.getElementsByTagName("servicios_m").item(0);
                        
                        //Aqui borramos los servicios que habian en primera instancia en caso de que se haya inicializado mal en el xml
                        while (servicios_medico.hasChildNodes()) {
                            servicios_medico.removeChild(servicios_medico.getFirstChild());
                        }
                        
                        // Agregar nuevos servicios al medico o en este caso los que ya tiene
                        for (String id_serviciii : medico.getServicios()) {
                            
                            //LO vamos a anadir al xml por el nombre id_sl
                            Element servicioId = document.createElement("id_sl");
                            servicioId.setTextContent(id_serviciii);
                            
                            //Lo anadimos a los servicios
                            servicios_medico.appendChild(servicioId);
                        }
                        
                        //Esto en caso de que haya algun error al agregar el medico
                        } else {
                            System.out.println("Error: Medico con ID " + medico_id + " no encontrado. Error maximo");
                        }
                }
            
                // Modificar estado si existe en el hashmap y sea diferente de null
                if (solicitud.getEstado() != null) {
                    
                    //Creamos un string de estado para que se le asigne el objeto y el id
                    String estado_id = solicitud.getEstado().getId();
                    
                    //Si dicho estado verdaderamente esta en el hashmap de los estados existentes
                    if (estados_existentes.containsKey(estado_id)) {
                        
                        //Llamamos a dicho objeto del hashmap
                        Estado estado = estados_existentes.get(estado_id);
                        
                        //Aqui buscaremos en el xml los tags de estado
                        Element estado_elemento = (Element) solicitud_elemento.getElementsByTagName("estado").item(0);
                    
                        // Actualizar atributos del estado como el id
                        estado_elemento.setAttribute("id_e", estado.getId());
                        
                        //actualizamos el nombre del estado al que se le tenga que asignar
                        Element nombre_stado = (Element) estado_elemento.getElementsByTagName("nombre_e").item(0);
                        nombre_stado.setTextContent(estado.getNombre());
                    
                    //Else en caso de que el estado no exista
                    } else {
                        System.out.println("Error: Estado con ID " + estado_id + " no encontrado.");
                    }
                }
            
                // Modificar observaciones ya existentes si hubo algun cambio por parte del medico
                Element observaciones_e = (Element) solicitud_elemento.getElementsByTagName("observaciones").item(0);
                observaciones_e.setTextContent(solicitud.getObservaciones() != null ? solicitud.getObservaciones() : "");
            
                // Modificar otros servicios si existen en el hashmap esto por si hay alguna cosa mas por hacer para eso llamamos al metodo
                if (solicitud.getOtros_servicios() != null) {
                    
                    //Establecemos el elemento que busca el tag de servicios
                    Element otros_servicios_elemss = (Element) solicitud_elemento.getElementsByTagName("otros_servicios").item(0);
                
                    // Limpiar servicios actuales en caso de que haya para reiniciarlo
                    while (otros_servicios_elemss.hasChildNodes()) {
                        
                        //Aca solo usamos un remove child para que se quiten los qu
                        otros_servicios_elemss.removeChild(otros_servicios_elemss.getFirstChild());
                    }
                
                    // Agregar nuevos servicios
                    for (Servicio servicio : solicitud.getOtros_servicios()) {
                        
                        //Si el servicio esta en servicios actuales osea el hashmap
                        if (serviciosactuales.containsKey(servicio.getId())) {
                            
                            //Creamos el elemento de id lista servicio 
                            Element servicioId = document.createElement("id_lista_servi");
                            
                            //Establecemos el servicio y usamos el metodo get
                            servicioId.setTextContent(servicio.getId());
                            
                            //Lo agregamos al elemento principal de otros servicio
                            otros_servicios_elemss.appendChild(servicioId);
                        
                        //Este else fuinciona en caso de que el servicio no exista en el hashmap
                        } else {
                        System.out.println("Error: Servicio con ID " + servicio.getId() + " no encontrado.");
                        }
                    }
                }
                
                //Salimos del metodo
                break;
            }
        }
    
        //Si no se encontro la solicitud indicamos que no existe
        if (!solicitud_real) {
            System.out.println("Error: Solicitud con ID " + solicitud.getId() + " no encontrada. Peligro inmininente");
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
  
