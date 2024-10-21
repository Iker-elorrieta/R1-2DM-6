package vista;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Color;

public class PanelWorkouts extends JPanel {

	private static final long serialVersionUID = 1L;

	// Modelo de la lista workouts
	private DefaultListModel<String> workoutListModel;

	// Lista que mostrará los workouts
	private JList<String> workoutsList;

	// Modelo de la lista ejercicios
	private DefaultListModel<String> ejersListModel;

	// Lista que mostrará los ejercicios
	private JList<String> ejersList;

	private JButton btnReturn;

	/**
	 * Create the panel.
	 */
	public PanelWorkouts() {
		setBackground(new Color(141, 204, 235));

		setBounds(0, 0, 880, 560);
		setLayout(null);

		// Etiqueta de título
		JLabel lblWorkouts = new JLabel("Workouts");
		lblWorkouts.setHorizontalAlignment(SwingConstants.CENTER);
		lblWorkouts.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblWorkouts.setBounds(274, 55, 271, 36);
		add(lblWorkouts);

		// Inicializar el modelo de la lista workouts
	    workoutListModel = new DefaultListModel<>();
	    // Inicializar la JList con el modelo
	    workoutsList = new JList<>(workoutListModel);
	    workoutsList.setBackground(new Color(208, 239, 249));
	    workoutsList.setFont(new Font("Tahoma", Font.BOLD, 14));
	    // Agregar la lista de workouts dentro de un JScrollPane
	    JScrollPane scrollPaneWorkouts = new JScrollPane(workoutsList);
	    scrollPaneWorkouts.setBounds(50, 150, 350, 300);
	    add(scrollPaneWorkouts);
		
		JLabel lblSelecWorkout = new JLabel("Selecciona un Workout:");
		lblSelecWorkout.setBackground(new Color(156, 210, 239));
		scrollPaneWorkouts.setColumnHeaderView(lblSelecWorkout);
		lblSelecWorkout.setFont(new Font("Tahoma", Font.BOLD, 14));

		
		
		// Inicializar el modelo de la lista ejercicios
		ejersListModel = new DefaultListModel<>();
		// Inicializar la JList de ejercicios con el modelo
		ejersList = new JList<>(ejersListModel);
		ejersList.setBackground(new Color(211, 235, 248));
		ejersList.setFont(new Font("Tahoma", Font.BOLD, 14));
		// Agregar la lista de ejercicios dentro de un JScrollPane
		JScrollPane scrollPaneEjers = new JScrollPane(ejersList);
		scrollPaneEjers.setBounds(450, 150, 350, 300); // Ajusta el tamaño y la posición del scroll
		add(scrollPaneEjers);
		
		JLabel lblSelecEjer = new JLabel("Selecciona un ejercicio:");
		lblSelecEjer.setBackground(new Color(156, 210, 239));
		lblSelecEjer.setFont(new Font("Tahoma", Font.BOLD, 14));
		scrollPaneEjers.setColumnHeaderView(lblSelecEjer);

		btnReturn = new JButton("Atrás");
		btnReturn.setBounds(10, 513, 102, 36);
		add(btnReturn);

	}
	
	public void addWorkout(String workout) {
	    if (workout != null && !workout.trim().isEmpty()) {
	        workoutListModel.addElement(workout);
	    }
	}


	public JButton getBtnReturn() {
		return btnReturn;
	}

	public void setBtnReturn(JButton btnReturn) {
		this.btnReturn = btnReturn;
	}


	public DefaultListModel<String> getWorkoutListModel() {
		return workoutListModel;
	}

	public void setWorkoutListModel(DefaultListModel<String> workoutListModel) {
		this.workoutListModel = workoutListModel;
	}



	public JList<String> getWorkoutList() {
		return workoutsList;
	}

	public void setWorkoutsList(JList<String> workoutsList) {
		this.workoutsList = workoutsList;
	}

	public DefaultListModel<String> getEjersListModel() {
		return ejersListModel;
	}

	public void setEjersListModel(DefaultListModel<String> ejersListModel) {
		this.ejersListModel = ejersListModel;
	}

	public JList<String> getEjersList() {
		return ejersList;
	}

	public void setEjersList(JList<String> ejersList) {
		this.ejersList = ejersList;
	}
	
	
	
}

