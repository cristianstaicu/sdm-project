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
import eit.nl.utwente.sdm.HealthClub;
import eit.nl.utwente.sdm.HealthRecord;
import eit.nl.utwente.sdm.Insurance;
import eit.nl.utwente.sdm.Mediator;
import eit.nl.utwente.sdm.HealthClub;
import eit.nl.utwente.sdm.Patient;
import eit.nl.utwente.sdm.TrustedAuthority;
import eit.nl.utwente.sdm.policy.Node;

public class GUIHealthClub extends JFrame {

	private List<HealthClub> healthClubs;
	private JPanel mainPanel;
	private JComboBox hcList;
	private final TrustedAuthority ta;
	private List<Patient> patients;
	private JComboBox patList;
	private Conductor conductor;

	public GUIHealthClub(Conductor conductor, List<HealthClub> hcs, List<Patient> patients,
			TrustedAuthority ta) {
		super("GUI Health Club");
		this.conductor = conductor;
		setBounds(new Rectangle(600, 400, 400, 100));
		this.patients = patients;
		this.ta = ta;
		this.healthClubs = hcs;
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		List<String> hcNames = new ArrayList<String>();
		for (HealthClub hc : hcs) {
			hcNames.add(hc.getName() + " ");
		}
		hcList = new JComboBox(hcNames.toArray());
		hcList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				int hcIndex = cb.getSelectedIndex();
			}

		});
		// petList.addActionListener(this);
		mainPanel.add(hcList, BorderLayout.NORTH);

		JPanel addPanel = new JPanel();
		addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
		List<String> patNames = new ArrayList<String>();
		for (Patient pat : patients) {
			patNames.add(pat.getName() + " " + pat.getSurname());
		}
		patList = new JComboBox(patNames.toArray());
		addPanel.add(new JLabel("   Patient"));
		addPanel.add(patList);
		addPanel.add(new JLabel("   Date"));
		final DatePicker dp = new DatePicker();
		addPanel.add(dp);
		addPanel.add(new JLabel("   Statement"));
		final JTextField statementTF = new JTextField();
		addPanel.add(statementTF);
		addPanel.add(new JLabel("   Value"));
		final JTextField valueTF = new JTextField();
		addPanel.add(valueTF);

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int patIndex = patList.getSelectedIndex();
				int patientID = GUIHealthClub.this.patients.get(patIndex)
						.getId();
				Node policy = Patient.getPolicy(patientID, false, false, false);
				int hcIndex = hcList.getSelectedIndex();
				HealthClub hc = GUIHealthClub.this.healthClubs.get(hcIndex);
				HealthRecord hr = new HealthRecord(patientID, -1, -1, hc
						.getId(), HealthRecord.HEALTH_CLUB_INSERTED_HR, valueTF
						.getText(), dp.getDate().toString(), statementTF
						.getText(), policy.getPolicyAsString());
				try {
					hr.persist(GUIHealthClub.this.ta.getPublicKey());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				GUIHealthClub.this.conductor.update();
				pack();
			}
		});
		addPanel.add(saveButton);
		addPanel.add(Box.createRigidArea(new Dimension(500, 300)));
		mainPanel.add(addPanel, BorderLayout.CENTER);
		mainPanel.revalidate();
		pack();

		HealthClub currentPatient = hcs.get(hcList.getSelectedIndex());

		JPanel westPanel = new JPanel();
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
		mainPanel.add(westPanel, BorderLayout.WEST);
		getContentPane().add(mainPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel.revalidate();
		pack();
		setVisible(true);
	}

	private static final long serialVersionUID = -2418655130994391637L;

}
