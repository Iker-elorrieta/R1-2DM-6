package vista;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class PanelWorkouts extends JPanel {

	private static final long serialVersionUID = 1L;

	// Modelo de la lista
	private DefaultListModel<String> workoutListModel;

	// Lista que mostrará los workouts
	private JList<String> workoutList;
	
	private JButton btnReturn;
	private JButton btnSelectWorkouts;

	/**
	 * Create the panel.
	 */
	public PanelWorkouts() {

		setBounds(0, 0, 880, 560);
		setLayout(null);

		// Etiqueta de título
		JLabel lblWorkouts = new JLabel("Workouts");
		lblWorkouts.setHorizontalAlignment(SwingConstants.CENTER);
		lblWorkouts.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblWorkouts.setBounds(274, 55, 271, 36);
		add(lblWorkouts);

		// Inicializar el modelo de la lista
		workoutListModel = new DefaultListModel<>();

		// Agregar algunos elementos de ejemplo al modelo (puedes añadir dinámicamente más tarde)
		workoutListModel.addElement("Workout 1: Full Body");
		workoutListModel.addElement("Workout 2: Upper Body");
		workoutListModel.addElement("Workout 3: Cardio Session");

		// Inicializar la JList con el modelo
		workoutList = new JList<>(workoutListModel);
		workoutList.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		// Agregar la lista dentro de un JScrollPane
		JScrollPane scrollPane = new JScrollPane(workoutList);
		scrollPane.setBounds(217, 120, 400, 300); // Ajusta el tamaño y la posición del scroll
		add(scrollPane);
		
		btnReturn = new JButton("Atrás");
		btnReturn.setBounds(10, 513, 102, 36);
		add(btnReturn);
		
		btnSelectWorkouts = new JButton("Seleccionar Workout");
		btnSelectWorkouts.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSelectWorkouts.setBounds(309, 431, 216, 36);
		add(btnSelectWorkouts);
	}
	
	
	
	// Método para añadir un workout a la lista
	public void addWorkout(String workout) {
		workoutListModel.addElement(workout);
	}


	public JButton getBtnReturn() {
		return btnReturn;
	}

	public void setBtnReturn(JButton btnReturn) {
		this.btnReturn = btnReturn;
	}
	
	
}
