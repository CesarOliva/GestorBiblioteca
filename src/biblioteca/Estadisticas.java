package biblioteca;

//Se importan las librerias necesarias
import javax.swing.*;
import java.awt.*;
import java.util.*;
import org.jfree.chart.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.CategoryPlot;

import Conexion.Peticiones;
import elementos.CustomScroll;

//Clase Estadisticas extendida de JPanel. Panel personalizado
public class Estadisticas extends JPanel {
    public Estadisticas() {
        //Configuración del panel
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(650, 600));
        setBackground(Color.white);
        
        //GRAFICA DE ADMINISTRADORES MAS ACTIVOS
        DefaultCategoryDataset datosLibros = new DefaultCategoryDataset();
        
        //Obtiene los datos de los libros subidos por administradores
        ArrayList<LibrosAdministradores> admins = Peticiones.librosAdministradores();
        
        for(LibrosAdministradores dato : admins){
            //Agrega los datos al modelo de datos
            datosLibros.addValue(dato.getCantidad(), "Libros", dato.getAdministrador());
        }

        // Crear la gráfica
        JFreeChart graficaBarra = ChartFactory.createBarChart("         Administradores más Activos", "Administrador", "Libros subidos", datosLibros);

        graficaBarra.getTitle().setFont(new Font("Poppins", Font.PLAIN, 18));
        graficaBarra.getLegend().setItemFont(new Font("Poppins", Font.PLAIN, 12));
        graficaBarra.setBackgroundPaint(Color.WHITE);
        graficaBarra.getPlot().setBackgroundPaint(new Color(245, 245, 245));
        
        CategoryPlot plot = (CategoryPlot) graficaBarra.getPlot();

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLabelFont(new Font("Poppins", Font.PLAIN, 14)); // Nombre del eje
        domainAxis.setTickLabelFont(new Font("Poppins", Font.PLAIN, 12)); // Valores del eje

        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setLabelFont(new Font("Poppins", Font.PLAIN, 14));
        rangeAxis.setTickLabelFont(new Font("Poppins", Font.PLAIN, 12));

        // Crear un panel para mostrar la gráfica de Barra
        ChartPanel panelGraficaBarra = new ChartPanel(graficaBarra);
        panelGraficaBarra.setPreferredSize(new Dimension(400,400));
        panelGraficaBarra.setMaximumSize(new Dimension(400,400));
        panelGraficaBarra.setBackground(Color.white);
        panelGraficaBarra.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        
        
        //GRAFICA DE LOS GENEROS DE LOS LIBROS AGREGADOS
        DefaultPieDataset datosGeneros = new DefaultPieDataset();

        //Obtiene los datos de los generos de los libros
        ArrayList<LibrosGeneros> generos = Peticiones.obtenerGeneros();
        
        //Ordenarlo de mayor a menor
        generos.sort((a, b) -> Integer.compare(b.getCantidad(), a.getCantidad()));
        
        for(LibrosGeneros dato : generos){
            //Agrega los datos al modelo de datos
            datosGeneros.setValue(dato.getGenero(), dato.getCantidad());
        }
        
        //Crear la gráfica
        JFreeChart pieChart = ChartFactory.createPieChart("Generos mas rentados", datosGeneros, true, true, false);
        
        pieChart.getTitle().setFont(new Font("Poppins", Font.PLAIN, 18));
        pieChart.getLegend().setItemFont(new Font("Poppins", Font.PLAIN, 12));
        pieChart.setBackgroundPaint(Color.WHITE);
        pieChart.getPlot().setBackgroundPaint(new Color(245, 245, 245));
        
        //Crear el panel para mostrar la gráfica de pastel
        ChartPanel panelGraficaPastel = new ChartPanel(pieChart);
        panelGraficaPastel.setPreferredSize( new Dimension(400,400));      
        panelGraficaPastel.setMaximumSize( new Dimension(400,400));      

        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setPreferredSize(new Dimension(620, 600));
        contenedor.setBackground(Color.white);
        
        contenedor.add(panelGraficaBarra);
        contenedor.add(panelGraficaPastel);
        
        //Crea un scrollPane
        JScrollPane scroll = new CustomScroll(contenedor);
        scroll.setPreferredSize(new Dimension(620, 600));
        
        add(scroll, BorderLayout.CENTER);
    }
}