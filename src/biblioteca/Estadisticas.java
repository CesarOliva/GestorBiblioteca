package biblioteca;

import Conexion.Conexion;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class Estadisticas extends JPanel {
    private JTable tablaEstadisticas;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> comboFiltro;
    private JLabel lblTotalBusquedas;

    public Estadisticas() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Panel superior con controles
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panelControles.setBackground(Color.WHITE);
        
        // Filtros
        comboFiltro = new JComboBox<>(new String[]{"Todos", "Este mes", "Esta semana"});
        comboFiltro.setFont(new Font("Poppins", Font.PLAIN, 12));
        
        // Botones
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setFont(new Font("Poppins", Font.PLAIN, 12));
        btnActualizar.addActionListener(e -> cargarEstadisticas());
        
        // Estadísticas globales
        lblTotalBusquedas = new JLabel("Total de búsquedas: 0");
        lblTotalBusquedas.setFont(new Font("Poppins", Font.BOLD, 12));

        panelControles.add(new JLabel("Filtrar por:"));
        panelControles.add(comboFiltro);
        panelControles.add(btnActualizar);
        panelControles.add(Box.createHorizontalStrut(50));
        panelControles.add(lblTotalBusquedas);

        // Configurar tabla
        configurarTabla();
        
        add(panelControles, BorderLayout.NORTH);
        add(new JScrollPane(tablaEstadisticas), BorderLayout.CENTER);
        
        cargarEstadisticas();
    }

    private void configurarTabla() {
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? Integer.class : String.class;
            }
        };
        
        modeloTabla.addColumn("#");
        modeloTabla.addColumn("Título");
        modeloTabla.addColumn("Autor");
        modeloTabla.addColumn("Búsquedas");
        modeloTabla.addColumn("Última búsqueda");

        tablaEstadisticas = new JTable(modeloTabla);
        tablaEstadisticas.setFont(new Font("Poppins", Font.PLAIN, 12));
        tablaEstadisticas.setRowHeight(30);
        tablaEstadisticas.setAutoCreateRowSorter(true);
        
        // Centrar contenido de columnas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tablaEstadisticas.getColumnCount(); i++) {
            tablaEstadisticas.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private void cargarEstadisticas() {
        modeloTabla.setRowCount(0);
        int totalBusquedas = 0;

        try (Connection conexion = Conexion.getInstance().conectar()) {
            // Consulta dinámica según filtro
            String filtro = (String) comboFiltro.getSelectedItem();
            String sql = "SELECT l.Titulo, a.Nombre as Autor, l.contador_busquedas, l.ultima_busqueda " +
                         "FROM libros l JOIN autores a ON l.IdAutor = a.IdAutor ";
            
            if (filtro.equals("Este mes")) {
                sql += "WHERE l.ultima_busqueda >= '" + LocalDate.now().withDayOfMonth(1) + "' ";
            } else if (filtro.equals("Esta semana")) {
                sql += "WHERE l.ultima_busqueda >= '" + LocalDate.now().minusDays(7) + "' ";
            }
            
            sql += "ORDER BY l.contador_busquedas DESC LIMIT 10";

            try (Statement stmt = conexion.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                int posicion = 1;
                while (rs.next()) {
                    modeloTabla.addRow(new Object[]{
                        posicion++,
                        rs.getString("Titulo"),
                        rs.getString("Autor"),
                        rs.getInt("contador_busquedas"),
                        rs.getDate("ultima_busqueda") != null ? 
                            rs.getDate("ultima_busqueda").toLocalDate().toString() : "Nunca"
                    });
                    totalBusquedas += rs.getInt("contador_busquedas");
                }
            }
            
            // Consulta para total general
            try (Statement stmt = conexion.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT SUM(contador_busquedas) FROM libros")) {
                if (rs.next()) {
                    totalBusquedas = rs.getInt(1);
                }
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar estadísticas:\n" + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        lblTotalBusquedas.setText("Total de búsquedas: " + totalBusquedas);
    }
}