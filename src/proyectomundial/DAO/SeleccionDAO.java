/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectomundial.DAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import proyectomundial.model.Auditoria;
import proyectomundial.model.Seleccion;
import proyectomundial.util.BasedeDatos;
import static proyectomundial.util.BasedeDatos.ejecutarSQL;

/**
 *
 * @author miguelropero
 */
public class SeleccionDAO {

    public SeleccionDAO() {
        BasedeDatos.conectar();
    }
    
    public boolean registrarSeleccion(Seleccion seleccion) {
        
        String sql = "INSERT INTO s_mercado.seleccion (nombre, continente, dt, nacionalidad) values("
                + "'" + seleccion.getNombre() + "', " 
                + "'" + seleccion.getContinente() + "', " 
                + "'" + seleccion.getDt() + "', " 
                + "'" + seleccion.getNacionalidad() + "')";
        
        //BasedeDatos.conectar();
        boolean registro = BasedeDatos.ejecutarActualizacionSQL(sql);
        //BasedeDatos.desconectar();
        return registro;
    }
    
    public List<Seleccion> getSelecciones() {
        
        String sql = "SELECT DISTINCT nombre, continente, dt, nacionalidad FROM s_mercado.seleccion";
        List<Seleccion> selecciones = new ArrayList<Seleccion>();
        
        try {
            ResultSet result = BasedeDatos.ejecutarSQL(sql);
            
            if(result != null) {
            
                while (result.next()) { 
                    Seleccion seleccion = new Seleccion(result.getString("nombre"), result.getString("continente"), result.getString("dt"), result.getString("nacionalidad"));
                    selecciones.add(seleccion);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error consultando selecciones");
        }
        
        return selecciones;
    }
    
    
    
   public List<Seleccion> getSeleccionesBuscar(String equipo) {
        
        String sql = "SELECT DISTINCT nombre, continente, dt, nacionalidad FROM s_mercado.seleccion WHERE LOWER(nombre) LIKE LOWER('%" + equipo + "%') or LOWER(continente) LIKE LOWER('%" + equipo +"%')";
        List<Seleccion> selecciones = new ArrayList<Seleccion>();
        
        try {
            ResultSet result = BasedeDatos.ejecutarSQL(sql);
            
            if(result != null) {
            
                while (result.next()) { 
                    Seleccion seleccion = new Seleccion(result.getString("nombre"), result.getString("continente"), result.getString("dt"), result.getString("nacionalidad"));
                    selecciones.add(seleccion);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error consultando selecciones");
        }
        
        return selecciones;
    }
   
    
    public int getCantEquipo() {
        
        String sql = "SELECT count(DISTINCT nombre) as cantidad FROM c_gomez11.seleccion";
        int salida = 0;
        
        try {
            ResultSet result = BasedeDatos.ejecutarSQL(sql);
            
            if(result != null) {
                while (result.next()) { 
                    String res = result.getString("cantidad");
                    salida = Integer.parseInt(res);
                } 
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error consultando selecciones");
        }
        
        return salida;
    }
    
    public int getCantNacionalidad() {
        
        String sql = "select count(distinct nacionalidad) from s_mercado.equipo e";
        int salida = 0;
        
        try {
            ResultSet result = BasedeDatos.ejecutarSQL(sql);
            
            if(result != null) {
                while (result.next()) { 
                    String res = result.getString("cantidad");
                    salida = Integer.parseInt(res);
                } 
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error consultando selecciones");
        }
        
        return salida;
    }
    
    public String[][] getSeleccionesMatriz() {
        
        String[][] matrizSelecciones = null;
        List<Seleccion> selecciones = getSelecciones();
        
        
        if(selecciones != null && selecciones.size() > 0) {
            
        
            matrizSelecciones = new String[selecciones.size()][4];

            int x = 0;
            for (Seleccion seleccion : selecciones) {

                matrizSelecciones[x][0] = seleccion.getNombre();
                matrizSelecciones[x][1] = seleccion.getContinente();
                matrizSelecciones[x][2] = seleccion.getDt();
                matrizSelecciones[x][3] = seleccion.getNacionalidad();
                x++;
            }
        }
        
        return matrizSelecciones;
    }
    
     public String[][] getSeleccionesMatrizBuscar(String equipo) {
        
        String[][] matrizSelecciones = null;
        List<Seleccion> selecciones = getSeleccionesBuscar(equipo);
        
        
        if(selecciones != null && selecciones.size() > 0) {
            
        
            matrizSelecciones = new String[selecciones.size()][4];

            int x = 0;
            for (Seleccion seleccion : selecciones) {

                matrizSelecciones[x][0] = seleccion.getNombre();
                matrizSelecciones[x][1] = seleccion.getContinente();
                matrizSelecciones[x][2] = seleccion.getDt();
                matrizSelecciones[x][3] = seleccion.getNacionalidad();
                x++;
            }
        }
        
        return matrizSelecciones;
    }
      
    public boolean contarHome() {
        
        String sql = "update c_gomez11.auditoria set contador = contador + 1 where pagina = 'Home'";
        //BasedeDatos.conectar();
        boolean registro = BasedeDatos.ejecutarActualizacionSQL(sql);
        //BasedeDatos.desconectar();
        return registro;
    }
    
    public boolean contarSelecciones() {
        
        String sql = "update c_gomez11.auditoria set contador = contador + 1 where pagina = 'Selecciones'";
        //BasedeDatos.conectar();
        boolean registro = BasedeDatos.ejecutarActualizacionSQL(sql);
        //BasedeDatos.desconectar();
        return registro;
    }
    
    public boolean contarResultados() {
        
        String sql = "update c_gomez11.auditoria set contador = contador + 1 where pagina = 'Resultados'";
        //BasedeDatos.conectar();
        boolean registro = BasedeDatos.ejecutarActualizacionSQL(sql);
        //BasedeDatos.desconectar();
        return registro;
    }
    
    public boolean contarDashSelecciones() {
        
        String sql = "update c_gomez11.auditoria set contador = contador + 1 where pagina = 'Dash Selecciones'";
        //BasedeDatos.conectar();
        boolean registro = BasedeDatos.ejecutarActualizacionSQL(sql);
        //BasedeDatos.desconectar();
        return registro;
    }
    
    public boolean contarDashResultados() {
        
        String sql = "update c_gomez11.auditoria set contador = contador + 1 where pagina = 'Dash Resultados'";
        //BasedeDatos.conectar();
        boolean registro = BasedeDatos.ejecutarActualizacionSQL(sql);
        //BasedeDatos.desconectar();
        return registro;
    }
    
    public List<Auditoria> getAuditoria() {
        
        String sql = "SELECT pagina, contador FROM c_gomez11.auditoria";
        List<Auditoria> audi = new ArrayList<Auditoria>();
        
        try {
            ResultSet result = BasedeDatos.ejecutarSQL(sql);
            
            if(result != null) {
            
                while (result.next()) { 
                    Auditoria auditoria = new Auditoria(result.getString("pagina"), result.getString("contador"));
                    audi.add(auditoria);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error consultando auditoria");
        }
        
        return audi;
    }
    
    public String[][] getAuditoriaMatriz() {
        
        String[][] matrizAuditoria = null;
        List<Auditoria> auditoria = getAuditoria();
        
        
        if(auditoria != null && auditoria.size() > 0) {
            
        
            matrizAuditoria = new String[auditoria.size()][4];

            int x = 0;
            for (Auditoria audi : auditoria) {

                matrizAuditoria[x][0] = audi.getPagina();
                matrizAuditoria[x][1] = audi.getContador();
                x++;
            }
        }
        
        return matrizAuditoria;
    }
    
    
    
}
