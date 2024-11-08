/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Presentacion;

import Conceptos.Paciente;
import Conceptos.Servicio;
import Conceptos.Solicitud;
import static Util.XMLHandler.CargarServicios;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author INTEL
 */

//Clase para consultar las solicitudes existentes
public class Consultar extends javax.swing.JDialog {
    
    //Usamos esto para hacer el buscado de la fecha
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    

    /**
     * Creates new form Consultar
     */
    public Consultar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        configurarTabla();
        llenarComboServicio();
        this.setLocation(400, 200);
        
    }
    
    //Nos ayuda a llenar la tabla con todo lo requerido
    private void configurarTabla() {
        
        //Try para configurar la tabla 
        try {
            
            // Cargar las solicitudes desde el archivo XML
            ArrayList<Solicitud> solicitudes;
            File xmlFile = new File("Data/solicitudes.xml");
            solicitudes = Util.XMLHandler.CargarSolicitud(xmlFile.getAbsolutePath());

            // Definir los nombres de las columnas para la tabla
            Vector<String> columnNames = new Vector<>();
            columnNames.addElement("ID Solicitud");
            columnNames.addElement("ID Paciente");
            columnNames.addElement("Nombre Paciente");
            columnNames.addElement("Telefono Paciente");
            columnNames.addElement("Email Paciente"); 
            columnNames.addElement("Fecha");
            columnNames.addElement("Servicio");
            columnNames.addElement("Estado");

            // Crear la estructura de datos para las filas
            Vector<Vector<String>> rowData = new Vector<>();

            // Rellenar la tabla con los datos de las solicitudes
            for (Solicitud s : solicitudes) {
                Vector<String> row = new Vector<>();
            
                // Agregar ID Solicitud con verificación de nulidad
                row.addElement(s.getId() != null ? s.getId() : "");
            
                // Agregar datos del paciente con verificación de nulidad
                if (s.getId_paciente() != null) {
                    row.addElement(s.getId_paciente().getId());
                    row.addElement(s.getId_paciente().getNombre());
                    row.addElement(s.getId_paciente().getTelefono());
                    row.addElement(s.getId_paciente().getEmail());
                
                } else {
                    row.addElement("");
                    row.addElement("");
                    row.addElement("");
                    row.addElement("");
                }
            
                // Formatear y agregar fecha y hora
                if (s.getFecha_hora() != null) {
                    row.addElement(DATETIME_FORMATTER.format(s.getFecha_hora()));
                } else {
                    row.addElement("");
                }
            
                // Agregar servicio con verificacion de que exista
                if (s.getTipo_servicio() != null) {
                    row.addElement(s.getTipo_servicio().getNombre_servicio());
                } else {
                    row.addElement("");
                }
            
                // Agregar estado y verificar que existe
                if (s.getEstado() != null) {
                    row.addElement(s.getEstado().getNombre());
                } else {
                    row.addElement("");
                }
                
                //Anade los elemenntos a las filas
                rowData.addElement(row);
            }

            // Crear un modelo de tabla no editable
            DefaultTableModel model = new DefaultTableModel(rowData, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            // Establecer el modelo de la tabla
            Tabla_solicitudes.setModel(model);
        

            // Configurar el tamano de las columnas por que por algunas razon no se cambian normalmente en design
            Tabla_solicitudes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
             // Configurar el ancho de cada columna
             if (Tabla_solicitudes.getColumnModel().getColumnCount() > 0) {
                Tabla_solicitudes.getColumnModel().getColumn(0).setPreferredWidth(120);  // ID Solicitud
                Tabla_solicitudes.getColumnModel().getColumn(1).setPreferredWidth(120);  // ID Paciente
                Tabla_solicitudes.getColumnModel().getColumn(2).setPreferredWidth(150);  // Nombre
                Tabla_solicitudes.getColumnModel().getColumn(3).setPreferredWidth(120);  // Telefono
                Tabla_solicitudes.getColumnModel().getColumn(4).setPreferredWidth(180);  // Email
                Tabla_solicitudes.getColumnModel().getColumn(5).setPreferredWidth(150);  // Fecha
                Tabla_solicitudes.getColumnModel().getColumn(6).setPreferredWidth(200);  // Servicio
                Tabla_solicitudes.getColumnModel().getColumn(7).setPreferredWidth(160);  // Estado
            }
        
        //Catch en caso de error
        } catch (Exception e) {
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage(), "Error Maximo", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //Metodo de llenarcomboservicoo que funciona para llenar el combo de los servicios
    public void llenarComboServicio() {
        
        //Llamamos al arraylist de carga los servicios o en este caso al metodo del handler
        ArrayList<Servicio> servicios = CargarServicios("Data/servicios.xml");
        
        //Establecemos el modelo del comboboxo
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        
        //Recorremos todos los servicios existentes
        for(Servicio servicio : servicios) {
            
            // Solo agregamos id y nombre al combo
            String item = servicio.getId() + " - " + servicio.getNombre_servicio();
            
            //Se anade al modelo
            model.addElement(item);
        }
        
        //Llamamos al combo de servicio 
        Combo_servicio.setModel(model);
    }
    
    // Método para actualizar la tabla con los resultados del buscado
    private void actualizar_tabla(ArrayList<Solicitud> solicitudes) {
        
        // Obtener el modelo actual de la tabla de las solicitudes
        DefaultTableModel model = (DefaultTableModel) Tabla_solicitudes.getModel();
    
        // Limpiar la tabla
        model.setRowCount(0);
    
        // Anadir las solicitudes filtradas
        for (Solicitud s : solicitudes) {
            
            //Iniciamos los vectores
            Vector<String> row = new Vector<>();
            
            //Anadimos solamente el id
            row.addElement(s.getId());
            
            //Mientras el paciente no sea diferente de nulo
            if (s.getId_paciente() != null) {
                
                //Vamos a anadir los elementos de paciente que tiene la tabla
                row.addElement(s.getId_paciente().getId());
                row.addElement(s.getId_paciente().getNombre());
                row.addElement(s.getId_paciente().getTelefono());
                row.addElement(s.getId_paciente().getEmail());
            
            //Si no hay nada lo ponemos como espacio en blanco
            } else {
                row.addElement("");
                row.addElement("");
                row.addElement("");
                row.addElement("");
            }
            
            //Para obtener la fecha y la hora usamos el formatter 
            if (s.getFecha_hora() != null) {
                row.addElement(DATETIME_FORMATTER.format(s.getFecha_hora()));
            } else {
                row.addElement("");
            }
            
            //Aqui nada mas anadimos el nombre del servicio a la tabla
            if (s.getTipo_servicio() != null) {
                row.addElement(s.getTipo_servicio().getNombre_servicio());
            } else {
                row.addElement("");
            }
            
            //Aqui sucede lo mismo con el estado
            if (s.getEstado() != null) {
                row.addElement(s.getEstado().getNombre());
            } else {
                row.addElement("");
            }
            
            //Todo se anade a las filas
            model.addRow(row);
        }
    
        // Mantener el formato de las columnas para que haya un scroll y no este todo junto
        if (Tabla_solicitudes.getColumnModel().getColumnCount() > 0) {
            Tabla_solicitudes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            Tabla_solicitudes.getColumnModel().getColumn(0).setPreferredWidth(100);  // ID
            Tabla_solicitudes.getColumnModel().getColumn(1).setPreferredWidth(100);  // ID Paciente
            Tabla_solicitudes.getColumnModel().getColumn(2).setPreferredWidth(200);  // Nombre
            Tabla_solicitudes.getColumnModel().getColumn(3).setPreferredWidth(120);  // Teléfono
            Tabla_solicitudes.getColumnModel().getColumn(4).setPreferredWidth(200);  // Email
            Tabla_solicitudes.getColumnModel().getColumn(5).setPreferredWidth(150);  // Fecha
            Tabla_solicitudes.getColumnModel().getColumn(6).setPreferredWidth(150);  // Servicio
            Tabla_solicitudes.getColumnModel().getColumn(7).setPreferredWidth(100);  // Estado
        }
    
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panelfondo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Boton_buscar = new javax.swing.JButton();
        Bsalir = new javax.swing.JButton();
        Campo_nombre_p = new javax.swing.JTextField();
        Campo_email = new javax.swing.JTextField();
        Campo_telefono = new javax.swing.JTextField();
        Fecha_campo = new javax.swing.JTextField();
        Combo_servicio = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        Campo_Id_solicitud = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla_solicitudes = new javax.swing.JTable();
        Campo_id_paciente = new javax.swing.JTextField();
        Tabla_llenar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Panelfondo.setBackground(new java.awt.Color(235, 250, 255));

        jLabel1.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 12)); // NOI18N
        jLabel1.setText("Paciente");

        jLabel2.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 12)); // NOI18N
        jLabel2.setText("ID");

        jLabel3.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 12)); // NOI18N
        jLabel3.setText("Email");

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 12)); // NOI18N
        jLabel4.setText("Telefono");

        jLabel5.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 12)); // NOI18N
        jLabel5.setText("Servicios");

        jLabel6.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 12)); // NOI18N
        jLabel6.setText("Especialidad");

        jLabel7.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 12)); // NOI18N
        jLabel7.setText("Fechas");

        Boton_buscar.setBackground(new java.awt.Color(153, 215, 235));
        Boton_buscar.setFont(new java.awt.Font("Lucida Sans Unicode", 1, 14)); // NOI18N
        Boton_buscar.setText("Buscar");
        Boton_buscar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(0, 0, 0), null, null));
        Boton_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Boton_buscarActionPerformed(evt);
            }
        });

        Bsalir.setBackground(new java.awt.Color(153, 215, 235));
        Bsalir.setFont(new java.awt.Font("Lucida Sans Unicode", 1, 12)); // NOI18N
        Bsalir.setText("Salir");
        Bsalir.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(0, 0, 0), null, null));
        Bsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BsalirActionPerformed(evt);
            }
        });

        Campo_nombre_p.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 12)); // NOI18N

        Campo_email.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 12)); // NOI18N

        Campo_telefono.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 12)); // NOI18N

        Fecha_campo.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 12)); // NOI18N
        Fecha_campo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Fecha_campoActionPerformed(evt);
            }
        });

        Combo_servicio.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 12)); // NOI18N
        Combo_servicio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Combo_servicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Combo_servicioActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 12)); // NOI18N
        jLabel9.setText("Nombre");

        Campo_Id_solicitud.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 12)); // NOI18N
        Campo_Id_solicitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Campo_Id_solicitudActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 12)); // NOI18N
        jLabel10.setText("ID Solicitud");

        Tabla_solicitudes.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 12)); // NOI18N
        Tabla_solicitudes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Solicitud", "ID Paciente", "Nombre", "Telefono", "Email", "Fecha", "Servicio", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla_solicitudes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        Tabla_solicitudes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla_solicitudesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla_solicitudes);
        if (Tabla_solicitudes.getColumnModel().getColumnCount() > 0) {
            Tabla_solicitudes.getColumnModel().getColumn(0).setResizable(false);
            Tabla_solicitudes.getColumnModel().getColumn(0).setPreferredWidth(100);
            Tabla_solicitudes.getColumnModel().getColumn(1).setResizable(false);
            Tabla_solicitudes.getColumnModel().getColumn(1).setPreferredWidth(100);
            Tabla_solicitudes.getColumnModel().getColumn(2).setResizable(false);
            Tabla_solicitudes.getColumnModel().getColumn(2).setPreferredWidth(200);
            Tabla_solicitudes.getColumnModel().getColumn(3).setResizable(false);
            Tabla_solicitudes.getColumnModel().getColumn(3).setPreferredWidth(120);
            Tabla_solicitudes.getColumnModel().getColumn(4).setResizable(false);
            Tabla_solicitudes.getColumnModel().getColumn(4).setPreferredWidth(200);
            Tabla_solicitudes.getColumnModel().getColumn(5).setResizable(false);
            Tabla_solicitudes.getColumnModel().getColumn(5).setPreferredWidth(150);
            Tabla_solicitudes.getColumnModel().getColumn(6).setResizable(false);
            Tabla_solicitudes.getColumnModel().getColumn(6).setPreferredWidth(150);
            Tabla_solicitudes.getColumnModel().getColumn(7).setResizable(false);
            Tabla_solicitudes.getColumnModel().getColumn(7).setPreferredWidth(100);
        }

        jScrollPane2.setViewportView(jScrollPane1);

        Campo_id_paciente.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 12)); // NOI18N

        Tabla_llenar.setBackground(new java.awt.Color(153, 215, 235));
        Tabla_llenar.setFont(new java.awt.Font("Lucida Sans Unicode", 1, 12)); // NOI18N
        Tabla_llenar.setText("Llenar Tabla");
        Tabla_llenar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(0, 0, 0), null, null));
        Tabla_llenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tabla_llenarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelfondoLayout = new javax.swing.GroupLayout(Panelfondo);
        Panelfondo.setLayout(PanelfondoLayout);
        PanelfondoLayout.setHorizontalGroup(
            PanelfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelfondoLayout.createSequentialGroup()
                .addGap(0, 141, Short.MAX_VALUE)
                .addGroup(PanelfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 805, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelfondoLayout.createSequentialGroup()
                        .addComponent(Tabla_llenar, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Bsalir, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(185, 185, 185))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelfondoLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(PanelfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelfondoLayout.createSequentialGroup()
                        .addGroup(PanelfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PanelfondoLayout.createSequentialGroup()
                                .addComponent(Campo_email, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7))
                            .addGroup(PanelfondoLayout.createSequentialGroup()
                                .addGroup(PanelfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(Campo_nombre_p)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelfondoLayout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(Campo_id_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Campo_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(PanelfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(18, 18, 18))
                    .addGroup(PanelfondoLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel1)
                        .addGap(445, 445, 445)))
                .addGroup(PanelfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelfondoLayout.createSequentialGroup()
                        .addGroup(PanelfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Campo_Id_solicitud, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Combo_servicio, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(67, 67, 67)
                        .addComponent(Boton_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Fecha_campo, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelfondoLayout.setVerticalGroup(
            PanelfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelfondoLayout.createSequentialGroup()
                .addGroup(PanelfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelfondoLayout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(jLabel5))
                    .addGroup(PanelfondoLayout.createSequentialGroup()
                        .addGroup(PanelfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelfondoLayout.createSequentialGroup()
                                .addGap(99, 99, 99)
                                .addGroup(PanelfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Campo_Id_solicitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10)))
                            .addGroup(PanelfondoLayout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(jLabel1)
                                .addGap(27, 27, 27)
                                .addGroup(PanelfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Campo_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel2)
                                    .addComponent(Campo_id_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(PanelfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Campo_nombre_p, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel6)
                                    .addComponent(Combo_servicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Boton_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(9, 9, 9)
                        .addGroup(PanelfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Campo_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(Fecha_campo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))))
                .addGap(69, 69, 69)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                .addGap(46, 46, 46)
                .addGroup(PanelfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Bsalir)
                    .addComponent(Tabla_llenar))
                .addGap(38, 38, 38))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panelfondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panelfondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Boton_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Boton_buscarActionPerformed
        //Este es el boton para filtrar y buscar la solicitud que uno requiera
        
        //Try para el uso del boton
        try {
            
            // Obtener valores de busqueda y eliminar espacios en blanco de los campos de texto porque estaban dando errores 
            String id_solicitud_b = Campo_Id_solicitud.getText().trim();
            String id_paciente_b = Campo_id_paciente.getText().trim();
            String nombre_p_b = Campo_nombre_p.getText().trim();
            String email_p_b = Campo_email.getText().trim();
            String telefono_p_b = Campo_telefono.getText().trim();
            String fecha_b_s = Fecha_campo.getText().trim();
            String servicio_u_b = (String) Combo_servicio.getSelectedItem();
        
            // Cargar todas las solicitudes existentes del xml como un arraylist
            ArrayList<Solicitud> existentes_solicitudes = Util.XMLHandler.CargarSolicitud("Data/solicitudes.xml");
            ArrayList<Solicitud> solicitudes_bien = new ArrayList<>();
        
            // Si no hay ningun campo activo muestre todas las solicitudes existentes
            if (id_solicitud_b.isEmpty() && id_paciente_b.isEmpty() && nombre_p_b.isEmpty() && email_p_b.isEmpty() && telefono_p_b.isEmpty() && fecha_b_s .isEmpty() && (servicio_u_b == null || servicio_u_b.equals("Seleccione un servicio valido."))) {
                
                //Llamamos a configurar tabla
                configurarTabla();
                return;
            }

            // Aplicamos filtros para todas las solicitudes por eso las recorremos
            for (Solicitud solicitud : existentes_solicitudes) {
                
                //Establecemos una variable llamada se logro que si siempre esta en  true va a mostrar la solicitud deseada
                boolean se_logrooo = true;
            
                // Verificar id de la solicitud
                if (!id_solicitud_b.isEmpty() && !solicitud.getId().equals(id_solicitud_b)) {
                    se_logrooo = false; //Esto siempre pasa en caso de que se rompa el if
                }
            
                // Verificar campos del paciente que es el mas extenso
                if (solicitud.getId_paciente() != null) {
                    
                    // Verificar id paciente
                    if (!id_paciente_b.isEmpty() && !solicitud.getId_paciente().getId().equals(id_paciente_b)) {
                        se_logrooo = false; //Nuevamente si no se cumple se pone en false
                    }
                
                    // Verificar nombre paciente por cierto usamos lowercase para que reconozca todas las letras sin importar si son mayusculas o minusculas
                    if (!nombre_p_b.isEmpty() && !solicitud.getId_paciente().getNombre().toLowerCase().contains(nombre_p_b.toLowerCase())) {
                        se_logrooo = false;
                    }
                
                    // Verificar email nuevamente del paciente
                    if (!email_p_b.isEmpty() && !solicitud.getId_paciente().getEmail().toLowerCase().contains(email_p_b.toLowerCase())) {
                        se_logrooo = false;
                    }
                
                    // Verificar telefono otra vez del paciente
                    if (!telefono_p_b.isEmpty() && !solicitud.getId_paciente().getTelefono().contains(telefono_p_b)) {
                        se_logrooo = false;
                    }
                    
                // Si no se cumple todo no se cumple nada basicamente
                } else if (!id_paciente_b.isEmpty() || !nombre_p_b.isEmpty() ||!email_p_b.isEmpty() || !telefono_p_b.isEmpty()) {
                
                    se_logrooo = false;
                }
                
                // Verificar fecha y hora con el formato debido
                if (!fecha_b_s .isEmpty() && solicitud.getFecha_hora() != null) {
                    
                    //Aqui llamamos al formato
                    DateTimeFormatter formato_debido = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    String fechaSolicitud = solicitud.getFecha_hora().format(formato_debido);
                    
                    //Si no es igual al del get de fecha hora esta malo 
                    if (!fecha_b_s .equals(fechaSolicitud)) {
                        se_logrooo = false;
                    }
                }
            
                // Verificar servicio
                if (servicio_u_b != null && !servicio_u_b.equals("Seleccione un servicio porfavor") && solicitud.getTipo_servicio() != null) {
                    
                    //Definimos una variable que reconoce el formato del combo
                    String id_combo_servici = servicio_u_b.split(" - ")[0].trim();
                    
                    //Si no calza con el id no cumple el criterio de se logro
                    if (!solicitud.getTipo_servicio().getId().equals(id_combo_servici)) {
                        se_logrooo = false;
                    }
                }
            
                // Si cumple todos los filtros o ands agregar a resultados bueno o un arraylist del que se busca 
                if (se_logrooo) {
                    solicitudes_bien.add(solicitud);
                }
            }
        
        // Mostrar resultados o mensaje si no hay coincidencias osea esta todo malo
        if (solicitudes_bien.isEmpty()) {
            
            JOptionPane.showMessageDialog(this, "No se encontraron solicitudes con los textos puestos", "Sin Resultados. Porfavor busque la solicitud especificamente", JOptionPane.INFORMATION_MESSAGE);
            
            // Mostrar todas las solicitudes y limpiar campos nuevamente llenamos la tabla para que aparezca todo
            actualizar_tabla(existentes_solicitudes);
            Campo_Id_solicitud.setText("");
            Campo_id_paciente.setText("");
            Campo_nombre_p.setText("");
            Campo_email.setText("");
            Campo_telefono.setText("");
            Fecha_campo.setText("");
            Combo_servicio.setSelectedIndex(0);
        
        //Si si se encontro la solicitud
        } else {
            // Mostrar solicitud que se tenia que buscar
            actualizar_tabla(solicitudes_bien);
        }
    
    //Ultima excepcion 
    } catch (Exception e) {
        e.printStackTrace(); JOptionPane.showMessageDialog(this, "Error al realizar la busqueda: " + e.getMessage(), "Error Maximo", JOptionPane.ERROR_MESSAGE);
    }
        
    }//GEN-LAST:event_Boton_buscarActionPerformed

    private void Fecha_campoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Fecha_campoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Fecha_campoActionPerformed

    private void BsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BsalirActionPerformed
        // Salir de consultar
        
        dispose();
    }//GEN-LAST:event_BsalirActionPerformed

    private void Campo_Id_solicitudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Campo_Id_solicitudActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Campo_Id_solicitudActionPerformed

    private void Combo_servicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Combo_servicioActionPerformed
        // funciona para seleccionar el servicio a buscar
         String seleccion = (String) Combo_servicio.getSelectedItem();
         
    }//GEN-LAST:event_Combo_servicioActionPerformed

    private void Tabla_solicitudesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla_solicitudesMouseClicked
     //Pasa la solicitud a los campos de texto segun la tabla
        
    // Obtener la fila seleccionada
    int filaSeleccionada = Tabla_solicitudes.getSelectedRow();
    
    if (filaSeleccionada != -1) {

        // Llenar los campos de texto con los datos de la fila seleccionada
        Campo_Id_solicitud.setText(Tabla_solicitudes.getValueAt(filaSeleccionada, 0).toString());
        Campo_id_paciente.setText(Tabla_solicitudes.getValueAt(filaSeleccionada, 1).toString());
        Campo_nombre_p.setText(Tabla_solicitudes.getValueAt(filaSeleccionada, 2).toString());
        Campo_telefono.setText(Tabla_solicitudes.getValueAt(filaSeleccionada, 3).toString());
        Campo_email.setText(Tabla_solicitudes.getValueAt(filaSeleccionada, 4).toString());
        Fecha_campo.setText(Tabla_solicitudes.getValueAt(filaSeleccionada, 5).toString());
        
        // Obtener el servicio de la tabla desde la columna 6
        String servicio_selecioo = Tabla_solicitudes.getValueAt(filaSeleccionada, 6).toString();
    
        // Buscar y seleccionar el servicio en el combo
        for (int i = 0; i < Combo_servicio.getItemCount(); i++) {
            
            String item_del_combo = Combo_servicio.getItemAt(i).toString();
            
            // Verificar si el item del combo contiene el servicio 
            if (item_del_combo.contains(servicio_selecioo)) {
                Combo_servicio.setSelectedIndex(i);
                break; //Salimos del id
            }
        }
    }
    
    }//GEN-LAST:event_Tabla_solicitudesMouseClicked

    private void Tabla_llenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tabla_llenarActionPerformed
        // Volver a llenar tabla
        
        configurarTabla();
        
    }//GEN-LAST:event_Tabla_llenarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Consultar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Consultar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Consultar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Consultar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Consultar dialog = new Consultar(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Boton_buscar;
    private javax.swing.JButton Bsalir;
    private javax.swing.JTextField Campo_Id_solicitud;
    private javax.swing.JTextField Campo_email;
    private javax.swing.JTextField Campo_id_paciente;
    private javax.swing.JTextField Campo_nombre_p;
    private javax.swing.JTextField Campo_telefono;
    private javax.swing.JComboBox<String> Combo_servicio;
    private javax.swing.JTextField Fecha_campo;
    private javax.swing.JPanel Panelfondo;
    private javax.swing.JButton Tabla_llenar;
    private javax.swing.JTable Tabla_solicitudes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
