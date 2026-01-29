package dao;

import model.Empleado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    private final String URL = "jdbc:oracle:thin:@//localhost:1521/orcl";
    private final String USER = "system";
    private final String PASS = "Tapiero123";

    private Connection getConexion() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public List<Empleado> listar() throws Exception {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM EMPLEADOS ORDER BY ID";

        try (Connection cn = getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Empleado(
                        rs.getInt("ID"),
                        rs.getString("NOMBRE"),
                        rs.getString("APELLIDO"),
                        rs.getInt("EDAD"),
                        rs.getString("CARGO")
                ));
            }
        }
        return lista;
    }

    public void insertar(Empleado e) throws Exception {
        String sql = "INSERT INTO EMPLEADOS (NOMBRE, APELLIDO, EDAD, CARGO) VALUES (?, ?, ?, ?)";
        try (Connection cn = getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, e.getNombre());
            ps.setString(2, e.getApellido());
            ps.setInt(3, e.getEdad());
            ps.setString(4, e.getCargo());
            ps.executeUpdate();
        }
    }

    public void actualizar(Empleado e) throws Exception {
        String sql = "UPDATE EMPLEADOS SET NOMBRE=?, APELLIDO=?, EDAD=?, CARGO=? WHERE ID=?";
        try (Connection cn = getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, e.getNombre());
            ps.setString(2, e.getApellido());
            ps.setInt(3, e.getEdad());
            ps.setString(4, e.getCargo());
            ps.setInt(5, e.getId());
            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM EMPLEADOS WHERE ID=?";
        try (Connection cn = getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}      
