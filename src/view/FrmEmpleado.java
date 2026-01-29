package view;

import dao.EmpleadoDAO;
import model.Empleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmEmpleado extends JFrame {

    private JTable tabla;
    private JTextField txtId, txtNombre, txtApellido, txtEdad, txtCargo;
    private DefaultTableModel modelo;
    private EmpleadoDAO dao = new EmpleadoDAO();

    public FrmEmpleado() {
        setTitle("crud empleados - Empresa ejemplo");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
        cargarTabla();
    }

    private void initUI() {
        modelo = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Apellido", "Edad", "Cargo"}, 0);

        tabla = new JTable(modelo);
        JScrollPane sp = new JScrollPane(tabla);

        JPanel panelDatos = new JPanel(new GridLayout(5, 2));
        txtId = new JTextField(); txtId.setEnabled(false);
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtEdad = new JTextField();
        txtCargo = new JTextField();

        panelDatos.add(new JLabel("ID")); panelDatos.add(txtId);
        panelDatos.add(new JLabel("Nombre")); panelDatos.add(txtNombre);
        panelDatos.add(new JLabel("Apellido")); panelDatos.add(txtApellido);
        panelDatos.add(new JLabel("Edad")); panelDatos.add(txtEdad);
        panelDatos.add(new JLabel("Cargo")); panelDatos.add(txtCargo);

        JPanel panelBotones = new JPanel();
        JButton btnInsertar = new JButton("Insertar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnListar = new JButton("Listar");

        panelBotones.add(btnInsertar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnListar);

        add(sp, BorderLayout.CENTER);
        add(panelDatos, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.SOUTH);

        btnListar.addActionListener(e -> cargarTabla());
        btnInsertar.addActionListener(e -> insertar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());

        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                txtId.setText(tabla.getValueAt(fila, 0).toString());
                txtNombre.setText(tabla.getValueAt(fila, 1).toString());
                txtApellido.setText(tabla.getValueAt(fila, 2).toString());
                txtEdad.setText(tabla.getValueAt(fila, 3).toString());
                txtCargo.setText(tabla.getValueAt(fila, 4).toString());
            }
        });
    }

    private void cargarTabla() {
        try {
            modelo.setRowCount(0);
            for (Empleado e : dao.listar()) {
                modelo.addRow(new Object[]{
                        e.getId(), e.getNombre(), e.getApellido(), e.getEdad(), e.getCargo()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void insertar() {
        try {
            dao.insertar(new Empleado(0,
                    txtNombre.getText(),
                    txtApellido.getText(),
                    Integer.parseInt(txtEdad.getText()),
                    txtCargo.getText()));
            cargarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void actualizar() {
        try {
            dao.actualizar(new Empleado(
                    Integer.parseInt(txtId.getText()),
                    txtNombre.getText(),
                    txtApellido.getText(),
                    Integer.parseInt(txtEdad.getText()),
                    txtCargo.getText()));
            cargarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void eliminar() {
        try {
            dao.eliminar(Integer.parseInt(txtId.getText()));
            cargarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
