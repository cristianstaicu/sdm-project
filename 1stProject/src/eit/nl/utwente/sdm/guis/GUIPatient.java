package eit.nl.utwente.sdm.guis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import eit.nl.utwente.sdm.DBUtils;
import eit.nl.utwente.sdm.HealthRecord;
import eit.nl.utwente.sdm.Patient;

public class GUIPatient extends JFrame {

	public GUIPatient(List<Patient> patients, final List<HealthRecord> hrs) {
		super("GUI Patient");
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		List<String> patientNames = new ArrayList<String>();
		for (Patient p : patients) {
			patientNames.add(p.getName() + " " + p.getSurname());
		}
		JComboBox patList = new JComboBox(patientNames.toArray());
		patList.setSelectedIndex(4);
		// petList.addActionListener(this);
		panel.add(patList, BorderLayout.NORTH);

		List<String> columns = new ArrayList<String>();
		List<String[]> values = new ArrayList<String[]>();

		columns.add("ID");
		columns.add("Patient");
		columns.add("Doctor");
		columns.add("HealthClub");
		columns.add("Value");
		columns.add("Date");
		columns.add("Statement");

		for (HealthRecord hr : hrs) {
			System.out.println(hr);
			values.add(new String[] {hr.getId() + "", hr.getIdPatient() + "",
					hr.getIdDoctor() + "", hr.getIdHealthClub() + "",
					hr.getValue() + "", hr.getDate() + "",
					hr.getStatement() + "", hr.getMeasurementType() + ""});
		}
		JTable table = new JTable(new DefaultTableModel(values.toArray(new Object[][]{}), columns.toArray())) {
			
			public Component prepareRenderer(TableCellRenderer r, int data, int columns){
	            final Component c = super.prepareRenderer(r, data, columns);
	            int measurementType = hrs.get(data).getMeasurementType();
	            
	            if (measurementType == 1){	           
	                c.setBackground(Color.WHITE);
	            } else if (measurementType == 2){
	                c.setBackground(Color.LIGHT_GRAY);
	            } else {
	            	c.setBackground(Color.GRAY);
	            }

	            if (isCellSelected(data, columns)){
	                c.setBackground(Color.ORANGE);
	            }                

	            return c;
	        }
			
		};
		JScrollPane tableContainer = new JScrollPane(table);
		panel.add(tableContainer, BorderLayout.CENTER);

		JButton addButton = new JButton("Add Health Record");
		panel.add(addButton, BorderLayout.SOUTH);

		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2418655130994391637L;

	public static void main(String[] args) {
		List<Patient> patients = DBUtils.getPatients();
		List<HealthRecord> healthRecords = DBUtils.getHealthRecords();
		GUIPatient patientGUI = new GUIPatient(patients, healthRecords);
	}

}
