/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Aplicacion;

import Conceptos.Medicos;
import Conceptos.Servicio;

/**
 *
 * @author Christopher
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Medicos m1 = new Medicos();
        m1.setNombre_medico("Carlos");
        System.out.println(m1.getNombre_medico());
        
        Servicio s1 = new Servicio();
        s1.setNombre_servicio("Carlos");
        System.out.println(s1.getNombre_servicio());
        
        m1.setNombre_servicio("Maxilofacial");
        m1.setId("009");
        
        System.out.println(m1.getId());
        
        

    }
    
}
