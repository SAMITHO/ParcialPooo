/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectomundial.DAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import proyectomundial.model.Resultado;
import proyectomundial.util.BasedeDatos;

public class ResultadosDAO {

    public ResultadosDAO() {
        BasedeDatos.conectar();
    }

    public boolean registrarResultados(Resultado resultado) {

        String sql = "INSERT INTO s_mercado.resultados (grupo, localE, visitante, continente_local, continente_visitante, goles_local, goles_visitante) values("
                + "'" + resultado.getGrupo() + "', "
                + "'" + resultado.getLocalE() + "', "
                + "'" + resultado.getVisitante() + "', "
                + "'" + resultado.getContinente_local() + "', "
                + "'" + resultado.getContinente_visitante() + "', "
                + "'" + Integer.parseInt(resultado.getGoles_local()) + "', "
                + "'" + Integer.parseInt(resultado.getGoles_visitante()) + "')";

        //BasedeDatos.conectar();
        boolean registro = BasedeDatos.ejecutarActualizacionSQL(sql);
        //BasedeDatos.desconectar();
        return registro;
    }

    public List<Resultado> getResultados() {

        String sql = "SELECT DISTINCT grupo, localE, visitante, continente_local, continente_visitante, goles_local, goles_visitante FROM s_mercado.resultados";
        List<Resultado> resultados = new ArrayList<Resultado>();

        try {
            ResultSet result = BasedeDatos.ejecutarSQL(sql);

            if (result != null) {

                while (result.next()) {
                    Resultado resultado = new Resultado(result.getString("grupo"), result.getString("localE"), result.getString("visitante"), result.getString("continente_local"), result.getString("continente_visitante"), result.getInt("goles_local") + "", result.getInt("goles_visitante") + "");
                    resultados.add(resultado);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error consultando selecciones");
        }

        return resultados;
    }

    public String[][] getResultadosMatriz() {

        String[][] matrizResultados = null;
        List<Resultado> resultados = getResultados();

        if (resultados != null && resultados.size() > 0) {

            matrizResultados = new String[resultados.size()][7];

            int x = 0;
            for (Resultado resultado : resultados) {

                matrizResultados[x][0] = resultado.getGrupo();
                matrizResultados[x][1] = resultado.getLocalE();
                matrizResultados[x][2] = resultado.getVisitante();
                matrizResultados[x][3] = resultado.getContinente_local();
                matrizResultados[x][4] = resultado.getContinente_visitante();
                matrizResultados[x][5] = resultado.getGoles_local();
                matrizResultados[x][6] = resultado.getGoles_visitante();
                x++;
            }
        }

        return matrizResultados;
    }

    public List<Resultado> getResultadosBuscar(String equipo) {

        String sql = "SELECT DISTINCT  grupo, localE, visitante, continente_local, continente_visitante, goles_local, goles_visitante FROM s_mercado.resultados WHERE LOWER(visitante) LIKE LOWER('%" + equipo + "%') or LOWER(localE) LIKE LOWER('%" + equipo + "%')";
        List<Resultado> resultados = new ArrayList<Resultado>();

        try {
            ResultSet result = BasedeDatos.ejecutarSQL(sql);

            if (result != null) {

                while (result.next()) {
                    Resultado resultado = new Resultado(result.getString("grupo"), result.getString("localE"), result.getString("visitante"), result.getString("continente_local"), result.getString("continente_visitante"), result.getString("goles_local"), result.getString("goles_visitante"));
                    resultados.add(resultado);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error consultando resultados");
        }

        return resultados;
    }

    public String[][] getResultadosMatrizBuscar(String equipo) {

        String[][] matrizSelecciones = null;
        List<Resultado> resultados = getResultadosBuscar(equipo);

        if (resultados != null && resultados.size() > 0) {

            matrizSelecciones = new String[resultados.size()][7];

            int x = 0;
            for (Resultado resultado : resultados) {

                matrizSelecciones[x][0] = resultado.getGrupo();
                matrizSelecciones[x][1] = resultado.getLocalE();
                matrizSelecciones[x][2] = resultado.getVisitante();
                matrizSelecciones[x][3] = resultado.getContinente_local();
                matrizSelecciones[x][4] = resultado.getContinente_visitante();
                matrizSelecciones[x][5] = resultado.getGoles_local();
                matrizSelecciones[x][6] = resultado.getGoles_visitante();
                x++;
            }
        }

        return matrizSelecciones;

    }

    public String getPartidos() {

        String sql = "SELECT count( grupo) as cantidad FROM s_mercado.resultados";
        int salida = 0;

        try {
            ResultSet result = BasedeDatos.ejecutarSQL(sql);

            if (result != null) {
                while (result.next()) {
                    String res = result.getString("cantidad");
                    salida = Integer.parseInt(res);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error consultando selecciones");
        }

        return salida + "";
    }

    public int getGolesLocal() {

        String sql = "SELECT sum(goles_local) as cantidad FROM s_mercado.resultados";
        int salida = 0;

        try {
            ResultSet result = BasedeDatos.ejecutarSQL(sql);

            if (result != null) {
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

    public int getGolesVisitante() {

        String sql = "SELECT sum(goles_visitante) as cantidad FROM s_mercado.resultados";
        int salida = 0;

        try {
            ResultSet result = BasedeDatos.ejecutarSQL(sql);

            if (result != null) {
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

    public String calcularPromGoles() {
        int golLoc = getGolesLocal();
        int golVis = getGolesVisitante();
        int part = Integer.parseInt(getPartidos());
        double promedio = (double) (golLoc + golVis) / (double) part;
        promedio = Math.round(promedio*100);
        promedio = promedio/100;
        return promedio + "";
    }

    public String masGoles() {
        String sql = "select (goles_visitante)+(goles_local) as golesP FROM s_mercado.resultados order by golesP desc";
        int salida = 0;

        try {
            ResultSet result = BasedeDatos.ejecutarSQL(sql);

            if (result != null) {
                result.next();
                String res = result.getString("golesP");
                salida = Integer.parseInt(res);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error consultando selecciones");
        }

        return salida + "";
    }

    
    public String menosGoles() {
        String sql = "select (goles_visitante)+(goles_local) as golesP FROM s_mercado.resultados order by golesP asc";
        int salida = 0;

        try {
            ResultSet result = BasedeDatos.ejecutarSQL(sql);

            if (result != null) {
                result.next();
                String res = result.getString("golesP");
                salida = Integer.parseInt(res);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error consultando selecciones");
        }

        return salida + "";
    }
    
    public String empates() {
        String sql = "select count(grupo) as cantidad from s_mercado.resultados where goles_visitante=goles_local";
        int salida = 0;

        try {
            ResultSet result = BasedeDatos.ejecutarSQL(sql);

            if (result != null) {
                result.next();
                String res = result.getString("cantidad");
                salida = Integer.parseInt(res);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error consultando selecciones");
        }

        return salida+"";
    }
    
    public String ganados() {
        String sql = "select count(grupo) as cantidad from s_mercado.resultados where goles_visitante=goles_local";
        int salida = 0;

        try {
            ResultSet result = BasedeDatos.ejecutarSQL(sql);

            if (result != null) {
                result.next();
                String res = result.getString("cantidad");
                salida = Integer.parseInt(res);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error consultando selecciones");
        }
        int part = Integer.parseInt(getPartidos());
        salida = part - salida;
        return salida+"";
    }
    
    

}
