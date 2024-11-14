package vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import controlador.Controlador;

public class PanelHistorico extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnReturn;
	private JTable table;
	DefaultTableModel tableModel;

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}

	public PanelHistorico() {

		setBounds(0, 0, 880, 560);
		setBackground(new Color(173, 216, 230)); // Fondo azul claro
		setLayout(null);

		// Etiqueta de título
		JLabel lblWorkouts = new JLabel("Histórico de workouts");
		lblWorkouts.setHorizontalAlignment(SwingConstants.CENTER);
		lblWorkouts.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblWorkouts.setForeground(new Color(10, 75, 128)); // Azul oscuro
		lblWorkouts.setBounds(252, 20, 375, 36);
		add(lblWorkouts);

		// Botón de regresar
		btnReturn = new JButton("Atrás");
		btnReturn.setBounds(33, 480, 160, 40);
		btnReturn.setBackground(new Color(10, 75, 128)); // Azul
		btnReturn.setForeground(Color.WHITE);
		btnReturn.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnReturn.setFocusPainted(false);
		btnReturn.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		add(btnReturn);

		// Crear el modelo de tabla y cargar datos desde XML
		tableModel = new DefaultTableModel(
				new Object[] { "Nombre", "Nivel", "Tiempo previsto", "Tiempo total", "Fecha", "Porcentaje completado" },
				0);

		Controlador.cargarDatosDesdeXML(tableModel);

		// Crear la tabla con el modelo de datos
		table = new JTable(tableModel);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.setRowHeight(25);

		TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
		table.setRowSorter(sorter);

		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		sortKeys.add(new RowSorter.SortKey(4, SortOrder.DESCENDING));
		sorter.setSortKeys(sortKeys);
		sorter.sort();

		// Agregar la tabla en un JScrollPane para el desplazamiento
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(87, 85, 705, 360);
		add(scrollPane);
	}

	public JButton getBtnReturn() {
		return btnReturn;
	}

	public void setBtnReturn(JButton btnReturn) {
		this.btnReturn = btnReturn;
	}
}
