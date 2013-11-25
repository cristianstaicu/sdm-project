package eit.nl.utwente.sdm.guis;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.michaelbaranov.microba.calendar.DatePicker;
import com.michaelbaranov.microba.common.Policy;

import eit.nl.utwente.sdm.DBUtils;
import eit.nl.utwente.sdm.Doctor;
import eit.nl.utwente.sdm.Employer;
import eit.nl.utwente.sdm.HealthRecord;
import eit.nl.utwente.sdm.Insurance;
import eit.nl.utwente.sdm.Mediator;
import eit.nl.utwente.sdm.Patient;
import eit.nl.utwente.sdm.TrustedAuthority;
import eit.nl.utwente.sdm.policy.Node;

public class GUIHealthClub extends JFrame {
	
	private List<Patient> patients;
	private JLabel attributes;
	private JScrollPane tableContainer;
	private JPanel mainPanel;
	private JComboBox patList;
	private JTable table;
	private final TrustedAuthority ta;
	private JPanel policyEditPanel;
	private JCheckBox shareWithDoc;
	private JCheckBox shareWithIns;
	private JCheckBox shareWithEmp;
	private List<HealthRecord> hrs;

	public GUIHealthClub(List<Patient> patients, TrustedAuthority ta) {
		super("GUI Patient");
		this.ta = ta;
		this.patients = patients;
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		List<String> patientNames = new ArrayList<String>();
		for (Patient p : patients) {
			patientNames.add(p.getName() + " " + p.getSurname());
		}
		patList = new JComboBox(patientNames.toArray());
		patList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				int patientIndex = cb.getSelectedIndex();
				updateUI(GUIHealthClub.this.patients.get(patientIndex).getId());
				
			}

		});
		// petList.addActionListener(this);
		mainPanel.add(patList, BorderLayout.NORTH);

		JButton addButton = new JButton("Add Health Record");
		addButton.addActionListener(new ActionListener() {
			
			private JPanel addPanel;

			@Override
			public void actionPerformed(ActionEvent arg0) {
//				GUIPatient.this.remove(tableContainer);
				policyEditPanel.setVisible(false);
				mainPanel.remove(tableContainer);
				tableContainer.setVisible(false);
				addPanel = new JPanel();
				addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
				addPanel.add(new JLabel("   Date"));
				final DatePicker dp = new DatePicker();
				addPanel.add(dp);
				addPanel.add(new JLabel("   Statement"));
				final JTextField statementTF = new JTextField();
				addPanel.add(statementTF);
				addPanel.add(new JLabel("   Value"));
				final JTextField valueTF = new JTextField();
				addPanel.add(valueTF);
				final JCheckBox shareDoctor = new JCheckBox("Share with doctor");
				addPanel.add(shareDoctor);
				final JCheckBox shareInsurance = new JCheckBox("Share with insurance company");
				addPanel.add(shareInsurance);
				final JCheckBox shareEmployer = new JCheckBox("Share with employer");
				addPanel.add(shareEmployer);
				JButton saveButton = new JButton("Save");
				saveButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						mainPanel.remove(addPanel);
						addPanel.setVisible(false);
						int patientIndex = patList.getSelectedIndex();
						Patient p = GUIHealthClub.this.patients.get(patientIndex);
						Node policy = Patient.getPolicy(p.getId(), shareDoctor.isSelected(), shareInsurance.isSelected(), shareEmployer.isSelected());
						HealthRecord hr = new HealthRecord(p.getId(), -1, p.getIdDoc(), -1, HealthRecord.USER_INSERTED_HR, valueTF.getText(), dp.getDate().toString(), statementTF.getText(), policy.getPolicyAsString());
						try {
							hr.persist(GUIHealthClub.this.ta.getPublicKey());
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						updateTableModel(p);
						mainPanel.add(tableContainer);
						tableContainer.setVisible(true);
						pack();
					}
				});
				addPanel.add(saveButton);
				addPanel.add(Box.createRigidArea(new Dimension(500, 300)));
				mainPanel.add(addPanel, BorderLayout.CENTER);
				mainPanel.revalidate();
				pack();
			}
		});
		mainPanel.add(addButton, BorderLayout.SOUTH);
		
		policyEditPanel = new JPanel();
		policyEditPanel.setLayout(new BoxLayout(policyEditPanel, BoxLayout.Y_AXIS));
		policyEditPanel.setBorder(new LineBorder(Color.black, 1));
		policyEditPanel.setVisible(false);
		JCheckBox shareWithYou = new JCheckBox("Share with yourself");
		shareWithYou.setSelected(true);
		shareWithYou.setEnabled(false);
		policyEditPanel.add(shareWithYou);
		shareWithDoc = new JCheckBox("Share with doctor");
		policyEditPanel.add(shareWithDoc);
		shareWithIns = new JCheckBox("Share with insurance");
		policyEditPanel.add(shareWithIns);
		shareWithEmp = new JCheckBox("Share with employer");
		policyEditPanel.add(shareWithEmp);
//		policyEditPanel.setVisible(false);
		attributes = new JLabel();
		Patient currentPatient = patients.get(patList.getSelectedIndex());
		updateUI(currentPatient.getId());
		mainPanel.add(tableContainer, BorderLayout.CENTER);
		JPanel westPanel = new JPanel(); 
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
		westPanel.add(attributes);
		westPanel.add(policyEditPanel);
		mainPanel.add(westPanel, BorderLayout.WEST);
		getContentPane().add(mainPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel.revalidate();
		pack();
		setVisible(true);
	}
	
	private void setAttributes(Patient currentPatient) {
		attributes.setText("<html><u>Attributes</u><br>");
		attributes.setText(attributes.getText() + currentPatient.getAttributesAsString() + "</html>");
	}
	
	private void updateTableModel(Patient currentPatient) {
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
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				Patient currentPatient = patients.get(patList.getSelectedIndex());
				ListSelectionModel model = table.getSelectionModel();  
				int selectionIndex = model.getLeadSelectionIndex();  
				HealthRecord hr = hrs.get(selectionIndex);
				boolean canDecrypt = currentPatient.canDecrypt(hr.getPolicy());
				if (canDecrypt) {
					policyEditPanel.setVisible(true);
					if (hr.getPolicy().contains("Doc"))
						shareWithDoc.setSelected(true);
					else 
						shareWithDoc.setSelected(false);
					if (hr.getPolicy().contains("Ins"))
						shareWithIns.setSelected(true);
					else 
						shareWithIns.setSelected(false);
					if (hr.getPolicy().contains("Emp"))
						shareWithEmp.setSelected(true);
					else 
						shareWithEmp.setSelected(false);
				} else {
					System.out.println("HIDDED");
					policyEditPanel.setVisible(false);
					GUIHealthClub.this.pack();
				}		
				
			}
		});
		table.getColumn("ID").setPreferredWidth(25);
		table.getColumn("Patient").setPreferredWidth(40);
		table.getColumn("Doctor").setPreferredWidth(40);
		table.getColumn("HealthClub").setPreferredWidth(40);
		table.getColumn("Value").setPreferredWidth(60);
		tableContainer = new JScrollPane(table);
	}

	private void updateUI(int patientId) {
		Patient currentPatient = null;
		for (Patient p : patients) {
			if (p.getId() == patientId)
				currentPatient = p;
		}
		setAttributes(currentPatient);
		Component oldTC = tableContainer;
		updateTableModel(currentPatient);
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

	public static void main(String[] args) {
		Mediator m = new Mediator();
		TrustedAuthority ta = new TrustedAuthority(m);
		List<Patient> patients = DBUtils.getPatients();
		List<String> attributes = Demo.getAttributes(patients);
		ta.setup(attributes);
		List<Doctor> doctors = DBUtils.getDoctors();
		List<Employer> employers = DBUtils.getEmployers();
		List<Insurance> insurances = DBUtils.getInsurances();
		ta.distributeKeys(patients, doctors, employers, insurances);
		List<HealthRecord> healthRecords = DBUtils.getHealthRecords();
		GUIHealthClub patientGUI = new GUIHealthClub(patients, ta);
	}

}
