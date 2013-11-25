package eit.nl.utwente.sdm.guis;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.michaelbaranov.microba.calendar.DatePicker;

import eit.nl.utwente.sdm.DBUtils;
import eit.nl.utwente.sdm.Doctor;
import eit.nl.utwente.sdm.HealthRecord;
import eit.nl.utwente.sdm.TrustedAuthority;

public class GUIDoctor extends JFrame implements IUpdatable {
	
	private List<Doctor> doctors;
	private JLabel attributes;
	private JScrollPane tableContainer;
	private JPanel mainPanel;
	private JComboBox docList;
	private JTable table;
	private final TrustedAuthority ta;
	private List<HealthRecord> hrs;
	private Conductor conductor;

	public GUIDoctor(Conductor conductor, List<Doctor> doctors, TrustedAuthority ta) {
		super("GUI Doctor");
		this.conductor = conductor;
		setBounds(new Rectangle(600, 20, 200, 100));
		this.ta = ta;
		this.doctors = doctors;
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		List<String> patientNames = new ArrayList<String>();
		for (Doctor d : doctors) {
			patientNames.add(d.getName() + " " + d.getSurname());
		}
		docList = new JComboBox(patientNames.toArray());
		docList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				int patientIndex = cb.getSelectedIndex();
				updateUI(GUIDoctor.this.doctors.get(patientIndex).getId());
				
			}

		});
		mainPanel.add(docList, BorderLayout.NORTH);		
		
		attributes = new JLabel();
		Doctor currentPatient = doctors.get(docList.getSelectedIndex());
		updateUI(currentPatient.getId());
		mainPanel.add(tableContainer, BorderLayout.CENTER);
		JPanel westPanel = new JPanel(); 
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
		westPanel.add(attributes);
		mainPanel.add(westPanel, BorderLayout.WEST);
		getContentPane().add(mainPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel.revalidate();
		pack();
		setVisible(true);
	}
	
	private void setAttributes(Doctor currentDoctor) {
		attributes.setText("<html><u>Attributes</u><br>");
		attributes.setText(attributes.getText() +  currentDoctor.getAttributesAsString() + "</html>");
	}
	
	private void updateTableModel(Doctor currentPatient) {
		final List<HealthRecord> hrs = DBUtils.getHealthRecords();
		this.hrs = hrs;
		List<String> columns = new ArrayList<String>();
		List<String[]> values = new ArrayList<String[]>();

		columns.add("ID");
		columns.add("Patient");
		columns.add("Doctor");
		columns.add("HealthClub");
		columns.add("Value");
		columns.add("Date");
		columns.add("Statement");
		columns.add("Policy");
		for (HealthRecord hr : hrs) {
			/* Check if can decrypt */
			String value, date, statement;
			value = hr.getValue();
			date = hr.getDate();
			statement = hr.getStatement();
			boolean canDecrypt = currentPatient.canDecrypt(hr.getPolicy());
			if (canDecrypt) {
				value = currentPatient.decrypt(value, hr.getPolicy(), ta.getPublicKey());
				date = currentPatient.decrypt(date, hr.getPolicy(), ta.getPublicKey());
				statement = currentPatient.decrypt(statement, hr.getPolicy(), ta.getPublicKey());
			}
			values.add(new String[] { hr.getId() + "", hr.getIdPatient() + "",
					hr.getIdDoctor() + "", hr.getIdHealthClub() + "",
					value + "", date + "",
					statement + "", hr.getPolicy() });
		}

		table = new JTable() {
			

			public boolean isCellEditable(int row, int column) {
				return false;
			}

			public Component prepareRenderer(TableCellRenderer r, int data,
					int columns) {
				final Component c = super.prepareRenderer(r, data, columns);
				int measurementType = hrs.get(data).getMeasurementType();

				if (measurementType == 1) {
					c.setBackground(Color.WHITE);
				} else if (measurementType == 2) {
					c.setBackground(Color.LIGHT_GRAY);
				} else {
					c.setBackground(Color.GRAY);
				}

				if (isCellSelected(data, columns)) {
					c.setBackground(Color.ORANGE);
				}

				return c;
			}
			
		};
		table.setModel(new DefaultTableModel(
				values.toArray(new Object[][] {}), columns.toArray()));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		table.getColumn("ID").setPreferredWidth(25);
		table.getColumn("Patient").setPreferredWidth(40);
		table.getColumn("Doctor").setPreferredWidth(40);
		table.getColumn("HealthClub").setPreferredWidth(40);
		table.getColumn("Value").setPreferredWidth(60);
		tableContainer = new JScrollPane(table);
	}

	private void updateUI(int patientId) {
		Doctor currentDoctor = null;
		for (Doctor d : doctors) {
			if (d.getId() == patientId)
				currentDoctor = d;
		}
		setAttributes(currentDoctor);
		Component oldTC = tableContainer;
		updateTableModel(currentDoctor);
		if (oldTC != null)
			mainPanel.remove(oldTC);
		mainPanel.add(tableContainer);
		tableContainer.setVisible(true);
		pack();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2418655130994391637L;

	@Override
	public void update() {
		Doctor currentPatient = doctors.get(docList.getSelectedIndex());
		updateUI(currentPatient.getId());
	}

}
