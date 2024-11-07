/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Presentacion;

import Conceptos.Estado;
import Conceptos.Medicos;
import Conceptos.Paciente;
import Conceptos.Servicio;
import Conceptos.Solicitud;
import static Util.XMLHandler.CargarEstado;
import static Util.XMLHandler.CargarMedico;
import static Util.XMLHandler.CargarPacientes;
import static Util.XMLHandler.CargarSolicitud;
import Util.XMLWriter;
import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author INTEL
 */
public class Atender extends javax.swing.JDialog {

    /**
     * Creates new form Atender
     */
    public Atender(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        llenarcomboMedicos();
        llenarcomboEstado();
        llenarTablaOtrosServicios();
        Combo_Solicitudes();
        
    }
    
    public void llenarcomboMedicos() {
        
        //Llamamos al arraylist que carga a los medicos existentes
        ArrayList<Medicos> medicos_c = CargarMedico("Data/medicos.xml");
        
        //Utilizamos default combobox para inicializar el modelo
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        
        //Recorremos los medicos del arraylist
        for(Medicos medicos : medicos_c) {
            
            // Solo agregamos id y nombre al combo
            String item = medicos.getId_m() + " - " + medicos.getNombre_medico();
            model.addElement(item);
        }
        
        //Establecemos el modelo en el combo que ocupamos
        Combo_medico.setModel(model);
    }
    
    public void llenarcomboEstado () {
        
        //Llamamos al arraylist que carga a los estados existentes
        ArrayList<Estado> estados = CargarEstado("Data/estados.xml");
        
        //Utilizamos default combobox para inicializar el modelo
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        
        //Recorremos los estados del arraylist
        for(Estado estado : estados) {
            
            // Solo agregamos id y nombre al combo
            String item = estado.getId() + " - " + estado.getNombre();
            model.addElement(item);
        }
        
        //Establecemos el modelo en el combo que ocupamos
        Combo_estado.setModel(model);    
    }
    
    //Este seria el metodo para obtener las solicitudes del combo
    private void Combo_Solicitudes() {
        
        //Llamamos al arraylist que carga a las solicitudes existentes
        ArrayList<Solicitud> solicitudes = CargarSolicitud("Data/solicitudes.xml");
        
        //Utilizamos default combobox para inicializar el modelo
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        
        //Recorremos las solicitudes del arraylist
        for(Solicitud solicitud : solicitudes) {
            
            // Solo agregamos id de la solicitud
            String item = solicitud.getId();
            model.addElement(item);
        }
        
        //Establecemos el modelo en el combo que ocupamos
        Combo_solicitud.setModel(model); 
        
    }
    
    
    //Metodo para llenar la tabla de los servicios adicionales
    private void llenarTablaOtrosServicios() {
        
        // Definir los nombres de las columnas
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Seleccionar"); // Columna para checkbox
        columnNames.add("ID");
        columnNames.add("Nombre Servicio");
        
        // Cargar los servicios desde el archivo XML
        ArrayList<Servicio> servicios;
        File xmlFile = new File("Data/servicios.xml");
        servicios = Util.XMLHandler.CargarServicios(xmlFile.getAbsolutePath());

        // Crear la estructura de datos para las filas
        Vector<Vector<Object>> rowData = new Vector<>();

        //For para recorrer y agregar todos los servicios de la clase servicio
        for (Servicio s : servicios) {
            
            Vector<Object> row = new Vector<>();
            
            row.add(false); 
            row.addElement(s.getId());
            row.addElement(s.getNombre_servicio());
            rowData.add(row);
        }

        // Crear el modelo de tabla
        DefaultTableModel model = new DefaultTableModel(rowData, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) { // Primera columna es para checkbox entonces si se puede modificar
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // Solo la columna del checkbox es editable
            }
        };

        // Establecer el modelo en la tabla
        Tabla_servicios_adicionales.setModel(model);
    }
    
    //Metodo que nos sirve para marcar otros servicios tomamos como parametro un list
    private void marca_check_otros_servici(List<Servicio> servicios_otrosss) {
        
        //Iniciamos el try para recorrer la lista de servicios adicionales
        try {
            // Obtenemos el modelo de la tabla de dichos servicios adicionales
            DefaultTableModel model = (DefaultTableModel) Tabla_servicios_adicionales.getModel();
        
            // Primero desmarcamos todos los checkboxes para que inician como limpios
            for (int i = 0; i < model.getRowCount(); i++) {
                model.setValueAt(false, i, 0);
            }
        
            // Verificamos de con null si la lista esta vacia
            if (servicios_otrosss == null || servicios_otrosss.isEmpty()) {
                System.out.println("No hay servicios adicionales para la solicitud");
                return;
            }
        
            // Recorremos cada fila de la tabla
            for (int i = 0; i < model.getRowCount(); i++) {
                
                String id_servicio_ta = String.valueOf(model.getValueAt(i, 1));
                String nombre_servicio_otroo = String.valueOf(model.getValueAt(i, 2));
            
                // Buscamos coincidencia ya sea por ID o por nombre del servicio
                for (Servicio servicio : servicios_otrosss) {
                    
                    //Aqui hacemos un gran and que busca las coinicidencias de nombre y servicio
                    if (servicio.getId().equals(id_servicio_ta) || servicio.getNombre_servicio().equals(id_servicio_ta) ||
                        servicio.getId().equals(nombre_servicio_otroo) ||
                        servicio.getNombre_servicio().equals(nombre_servicio_otroo)) {
                    
                            // Si encontramos coincidencia, marcamos el checkbox posicionandolo como true
                            model.setValueAt(true, i, 0);
                            
                            //Salimos de la funcion
                            break;
                    }   
                }
            }
        
            // Refrescamos la tabla esto para que se vea correctamente
            Tabla_servicios_adicionales.repaint();
        
        //Esta seria una excpecion en caso de que se carge mal el metodo
        } catch (Exception e) {
            System.err.println("Error al marcar servicios adicionales: "); e.printStackTrace(); JOptionPane.showMessageDialog(this, "Error al marcar los servicios adicionales: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

        jPanel1 = new javax.swing.JPanel();
        Combo_solicitud = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        Campo_id_paciente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Combo_estado = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        Combo_medico = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        Campo_observaciones = new javax.swing.JTextField();
        Observaciones = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla_servicios_adicionales = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        Boton_salvar = new javax.swing.JButton();
        Boton_Cancelar = new javax.swing.JButton();
        fecha_hora_campo = new javax.swing.JTextField();
        servicio_solici = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Combo_solicitud.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Combo_solicitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Combo_solicitudActionPerformed(evt);
            }
        });

        jLabel1.setText("Solicitud");

        jLabel4.setText("ID Paciente");

        jLabel5.setText("Fecha/Hora");

        jLabel2.setText("Especialidad");

        Combo_estado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Combo_estado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Combo_estadoActionPerformed(evt);
            }
        });

        jLabel6.setText("Estado");

        Combo_medico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Combo_medico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Combo_medicoActionPerformed(evt);
            }
        });

        jLabel3.setText("Medico");

        Observaciones.setText("Observaciones");

        Tabla_servicios_adicionales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "", "Tipo", "Servicio"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(Tabla_servicios_adicionales);
        if (Tabla_servicios_adicionales.getColumnModel().getColumnCount() > 0) {
            Tabla_servicios_adicionales.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel7.setText("Otros servicios (Adicionales)");

        Boton_salvar.setText("Salvar");
        Boton_salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Boton_salvarActionPerformed(evt);
            }
        });

        Boton_Cancelar.setText("Cancelar");
        Boton_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salir(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(Combo_solicitud, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Combo_medico, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(servicio_solici, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGap(95, 95, 95)
                                            .addComponent(jLabel5))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addGap(122, 122, 122)
                                            .addComponent(jLabel6)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGap(98, 98, 98)
                                        .addComponent(jLabel4)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(Combo_estado, 0, 152, Short.MAX_VALUE)
                                    .addComponent(Campo_id_paciente)
                                    .addComponent(fecha_hora_campo)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(171, 171, 171)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Observaciones)
                            .addComponent(Campo_observaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(265, 265, 265)
                        .addComponent(Boton_salvar)
                        .addGap(85, 85, 85)
                        .addComponent(Boton_Cancelar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(jLabel7))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(166, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Combo_solicitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(fecha_hora_campo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(servicio_solici, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Campo_id_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Combo_medico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Combo_estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(46, 46, 46)
                .addComponent(Observaciones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Campo_observaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Boton_salvar)
                    .addComponent(Boton_Cancelar))
                .addContainerGap(62, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void salir(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salir
        dispose();
    }//GEN-LAST:event_salir

    private void Boton_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Boton_salvarActionPerformed
        // Boton para modificar la solicitud como tal
        
        //Aqui basicamente inicializamos el try para hacer el atender o modificar respectivo
        try {
            // Obtener la solicitud seleccionada del combo de solicitud
            String id_solicitud = (String) Combo_solicitud.getSelectedItem();
            
            //Si el id esta vacio o no se selecciono nada 
            if (id_solicitud == null || id_solicitud.isEmpty()) {
                
                //Vamos a tirar un mensaje de que seleccione lalgo
                JOptionPane.showMessageDialog(this, "Por favor seleccione una solicitud", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Creamos instancia del parser de escritura
            XMLWriter generador = new XMLWriter();
        
            // Cargar y verificar los xml necesarios algo similar al main
            generador.cargarXML("Data/medicos.xml");
            generador.verificacion_medicos();
            generador.cargarXML("Data/servicios.xml");
            generador.CargaServicios();
            generador.cargarXML("Data/estados.xml");
            generador.verificacion_estados();
            generador.cargarXML("Data/solicitudes.xml");

            // Crear la solicitud modificada
            Solicitud solicitud_modificada = new Solicitud();
            solicitud_modificada.setId(id_solicitud);

            // Obtener y establecer el medico seleccionado en el combo
            String medico_ha_selec = (String) Combo_medico.getSelectedItem();
            
            //Si el que se selecciono no es nulo y no esta vacio diganos
            if (medico_ha_selec != null && !medico_ha_selec.isEmpty()) {
                
                //Establecemos primero un espacio entre el id y el nombre
                String idMedico = medico_ha_selec.split(" - ")[0];
                
                //Creamos un nuevo objeto medico
                Medicos medico = new Medicos();
                
                //Le asignamos id el resto de cosas ya se pasan
                medico.setId_m(idMedico);
                
                //Establecemos dicho medico a la solicitud
                solicitud_modificada.setMedico(medico);
            }

            // Obtener y establecer el estado seleccionado
            String estado_selec = (String) Combo_estado.getSelectedItem();
            
            //Nuevamente si no es nulo ni esta vacio
            if (estado_selec != null && !estado_selec.isEmpty()) {
                
                //Establecemos un espacio
                String idEstado = estado_selec.split(" - ")[0];
                
                //Creamos el nuevo objeto estado
                Estado estado = new Estado();
                
                //Le establecemos tanto id como nombre
                estado.setId(idEstado);
                estado.setNombre(estado_selec.split(" - ")[1]);
                
                //Agregamos el estado a la solicitud
                solicitud_modificada.setEstado(estado);
            }

            // Obtener y establecer las observaciones nuevas que dictamine el medico
            String observaciones = Campo_observaciones.getText();
            solicitud_modificada.setObservaciones(observaciones);

            // Obtener los otros seleccionados directamente de la tabla
            List<Servicio> servicios_otros_lis = new ArrayList<>();
            
            //Establecemos el modelo base de la tabla
            DefaultTableModel model = (DefaultTableModel) Tabla_servicios_adicionales.getModel();
            
            //Recorremos con un ciclo for las filas de la tabla de los otros servicio
            
            for (int i = 0; i < model.getRowCount(); i++) {
                
                //Aqui definimos una variable booleano para determinar la seleccion
                Boolean seleccionado = (Boolean) model.getValueAt(i, 0);
                
                //Si seleccionado es true
                if (seleccionado) {
                    
                    //Creamos un nuevo servicio
                    Servicio servicio = new Servicio();
                    
                    //Obtenemos el valor de la tabla y lo agregamos al arraylist
                    String idServicio = model.getValueAt(i, 1).toString();
                    servicio.setId(idServicio);
                    servicios_otros_lis.add(servicio);
                }
            }
            
            //Establecemos dicha lista a la solicitu
            solicitud_modificada.setOtros_servicios(servicios_otros_lis);

            // Mantener los datos que no se modifican llamando al xml
            ArrayList<Solicitud> solicitudes_que_existen = CargarSolicitud("Data/solicitudes.xml");
            Solicitud solicitud_original = solicitudes_que_existen.stream().filter(s -> s.getId().equals(id_solicitud)).findFirst().orElse(null);
            
            //Ahora bien si la solicitud original se verifca que esta llen
            if (solicitud_original != null) {
                // Mantener datos que no se modifican 
                solicitud_modificada.setTipo_servicio(solicitud_original.getTipo_servicio());
                solicitud_modificada.setId_paciente(solicitud_original.getId_paciente());
                solicitud_modificada.setFecha_hora(solicitud_original.getFecha_hora());
            }

            // Aplicar las modificaciones llamando directamente al parser de escritura
            generador.atender_solicitud(solicitud_modificada);
            
            //Guardamos todo en el xml
            generador.guardarXML("Data/solicitudes.xml");

            // Mostrar mensaje de exito
            JOptionPane.showMessageDialog(this, "Solicitud atendida exitosamente", "Exito", JOptionPane.INFORMATION_MESSAGE);

            // Opcionalmente, cerrar la ventana
            dispose();

    } catch (Exception e) {
        System.err.println("Error al actualizar la solicitud: "); e.printStackTrace(); JOptionPane.showMessageDialog(this, "Error al actualizar la solicitud: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
        
        
    }//GEN-LAST:event_Boton_salvarActionPerformed

    private void Combo_medicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Combo_medicoActionPerformed

        // Codigo para seleccionar el medico para la solicitud
        
        //Definimos un string que almacena el estado seleccionado
        String seleccion = (String) Combo_medico.getSelectedItem();
        
        //Si verdaderamente se almaceno algo aqui
        if (seleccion != null) {
            
            //Printeamos el paciente seleccionado (esto no es practico solo lo uso para verificar)
            System.out.println("Medico seleccionado: " + seleccion);
        }
        
    }//GEN-LAST:event_Combo_medicoActionPerformed

    private void Combo_estadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Combo_estadoActionPerformed
        // Codigo para seleccionar el estado para la solicitud
        
        //Definimos un string que almacena el estado seleccionado
        String seleccion = (String) Combo_estado.getSelectedItem();
        
        //Si verdaderamente se almaceno algo aqui
        if (seleccion != null) {
            
            //Printeamos el paciente seleccionado (esto no es practico solo lo uso para verificar)
            System.out.println("Medico seleccionado: " + seleccion);
        }
        
    }//GEN-LAST:event_Combo_estadoActionPerformed

    private void Combo_solicitudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Combo_solicitudActionPerformed
        
        // Combo de la solicitud
        
        //Utilizamos un try justo aqui establecemos que se puede modificar y que no
        try {
            
        // Obtener el ID seleccionado en el combo
        String id_selecccc = (String) Combo_solicitud.getSelectedItem();
        
        //Si el id de la solicitud seleccionado es diferente de null
        if (id_selecccc != null) {
            
            // Cargar las solicitudes y buscar la seleccionada
            ArrayList<Solicitud> solicitudes = CargarSolicitud("Data/solicitudes.xml");
            
            //Almacenamos la solicitud seleccionada y verificamos que coincida con el id existente
            Solicitud solicitud_selecccc = solicitudes.stream().filter(s -> s.getId().equals(id_selecccc)).findFirst().orElse(null);
            
            //Nuevamente si la que seleccionamos es distinta de null
            if (solicitud_selecccc != null) {
                
                // El campo del id del paciente se establece como el que le pertenece pero no se puede modificar
                Campo_id_paciente.setText(solicitud_selecccc.getId());
                
                //Aqui lo bloqueamos
                Campo_id_paciente.setEnabled(false);
                
                // Aqui sucede lo mismo con la especialidad o el servicio unico
                if (solicitud_selecccc.getTipo_servicio() != null) {
                    
                    //Establecemos que solo aparezca el id del servicio y el nombre
                    servicio_solici.setText(solicitud_selecccc.getTipo_servicio().getId() + " - " + solicitud_selecccc.getTipo_servicio().getNombre_servicio());
                    
                    //Hacemos que se bloquee
                    servicio_solici.setEnabled(false);
                }
                
                // Hacemos lo mismo que con el servicio pero con el caso del paciente
                if (solicitud_selecccc.getId_paciente() != null) {
                    
                    //Nuevamente nombre y id
                    Campo_id_paciente.setText(solicitud_selecccc.getId_paciente().getId() +  " - " + solicitud_selecccc.getId_paciente().getNombre());
                    
                    //El campo lo establecemos como falso
                    Campo_id_paciente.setEnabled(false);
                }
                
                // Campo de la fecha y hora 
                if (solicitud_selecccc.getFecha_hora() != null) {
                    
                    //Aqui nada mas nos aseguramos que sea del formato que establecimos en el constructor
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    
                    //Definimos la fecha y hora como bloqueada
                    fecha_hora_campo.setText(solicitud_selecccc.getFecha_hora().format(formatter));
                    fecha_hora_campo.setEnabled(false);
                }
                
                // Combo Estado en este caso solo que hacemos que se actualice por el que esta ahora mismo
                if (solicitud_selecccc.getEstado() != null) {
                    
                    //Aqui nuevamente obtenemos id y nombre
                    String estadoActual = solicitud_selecccc.getEstado().getId() +  " - " + solicitud_selecccc.getEstado().getNombre();
                    Combo_estado.setSelectedItem(estadoActual);
                }
                
                // Combo del medico nuevamente si se edita pero se actualiza
                if (solicitud_selecccc.getMedico() != null) {
                    
                    //Aqui nuevamente solo el id y el nombre del medico
                    String medicoActual = solicitud_selecccc.getMedico().getId_m() + " - " + solicitud_selecccc.getMedico().getNombre_medico();
                    Combo_medico.setSelectedItem(medicoActual);
                }
                
                // Luego llamamos al metodo que logra marcar el checkbox en donde se seleccionan los seervicios otros de la solicitud
                marca_check_otros_servici(solicitud_selecccc.getOtros_servicios());
                    
            }
        }
        
        //Excepcion en caso de que no se carge na
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos de la solicitud: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_Combo_solicitudActionPerformed

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
            java.util.logging.Logger.getLogger(Atender.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Atender.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Atender.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Atender.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Atender dialog = new Atender(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton Boton_Cancelar;
    private javax.swing.JButton Boton_salvar;
    private javax.swing.JTextField Campo_id_paciente;
    private javax.swing.JTextField Campo_observaciones;
    private javax.swing.JComboBox<String> Combo_estado;
    private javax.swing.JComboBox<String> Combo_medico;
    private javax.swing.JComboBox<String> Combo_solicitud;
    private javax.swing.JLabel Observaciones;
    private javax.swing.JTable Tabla_servicios_adicionales;
    private javax.swing.JTextField fecha_hora_campo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField servicio_solici;
    // End of variables declaration//GEN-END:variables
}
