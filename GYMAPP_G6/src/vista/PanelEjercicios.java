package vista;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class PanelEjercicios extends JPanel {

	private static final long serialVersionUID = 1L;

	// Modelo de la lista
	private DefaultListModel<String> ejerListModel;

	// Lista que mostrará los workouts
	private JList<String> ejerList;
	
	private JButton btnReturn;
	private JButton btnSelectEjer;

	/**
	 * Create the panel.
	 */
	public PanelEjercicios() {

		setBounds(0, 0, 880, 560);
		setLayout(null);

		// Etiqueta de título
		JLabel lblEjercicios = new JLabel("Ejercicios");
		lblEjercicios.setHorizontalAlignment(SwingConstants.CENTER);
		lblEjercicios.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblEjercicios.setBounds(274, 55, 271, 36);
		add(lblEjercicios);

		// Inicializar el modelo de la lista
		ejerListModel = new DefaultListModel<>();

		// Agregar algunos elementos de ejemplo al modelo (puedes añadir dinámicamente más tarde)
		ejerListModel.addElement("Ejercicio 1");
		ejerListModel.addElement("Ejercicio 2");
		ejerListModel.addElement("Ejercicio 3");

		// Inicializar la JList con el modelo
		ejerList = new JList<>(ejerListModel);
		ejerList.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		// Agregar la lista dentro de un JScrollPane
		JScrollPane scrollPane = new JScrollPane(ejerList);
		scrollPane.setBounds(217, 120, 400, 300); // Ajusta el tamaño y la posición del scroll
		add(scrollPane);
		
		btnReturn = new JButton("Atrás");
		btnReturn.setBounds(10, 513, 102, 36);
		add(btnReturn);
		
		btnSelectEjer = new JButton("Seleccionar Ejercicio");
		btnSelectEjer.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSelectEjer.setBounds(309, 431, 216, 36);
		add(btnSelectEjer);
	}
	
	
	
	// Método para añadir un workout a la lista
	public void addWorkout(String workout) {
		ejerListModel.addElement(workout);
	}

	public JButton getBtnReturn() {
		return btnReturn;
	}

	public void setBtnReturn(JButton btnReturn) {
		this.btnReturn = btnReturn;
	}
		
}
