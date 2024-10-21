package vista;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class PanelWorkouts extends JPanel {

	private static final long serialVersionUID = 1L;

	private DefaultListModel<String> workoutListModel;

	private JList<String> workoutList;
	
	private JButton btnReturn;
	private JButton btnSelectWorkouts;

	/**
	 * Create the panel.
	 */
	public PanelWorkouts() {

		setBounds(0, 0, 880, 560);
		setLayout(null);

		JLabel lblWorkouts = new JLabel("Workouts");
		lblWorkouts.setHorizontalAlignment(SwingConstants.CENTER);
		lblWorkouts.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblWorkouts.setBounds(274, 55, 271, 36);
		add(lblWorkouts);

		workoutListModel = new DefaultListModel<>();

		workoutList = new JList<>(workoutListModel);
		workoutList.setFont(new Font("Tahoma", Font.BOLD, 14));
		
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
	
	public void addWorkout(String workout) {
	    if (workout != null && !workout.trim().isEmpty()) {
	        workoutListModel.addElement(workout);
	    }
	}

	public void setWorkoutListModel(DefaultListModel<String> workoutListModel) {
		this.workoutListModel = workoutListModel;
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

	public JList<String> getWorkoutList() {
		return workoutList;
	}

	public void setWorkoutList(JList<String> workoutList) {
		this.workoutList = workoutList;
	}

	public JButton getBtnSelectWorkouts() {
		return btnSelectWorkouts;
	}

	public void setBtnSelectWorkouts(JButton btnSelectWorkouts) {
		this.btnSelectWorkouts = btnSelectWorkouts;
	}
	
}
