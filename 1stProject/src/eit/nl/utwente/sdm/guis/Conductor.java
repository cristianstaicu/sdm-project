package eit.nl.utwente.sdm.guis;

import java.util.ArrayList;
import java.util.List;

import eit.nl.utwente.sdm.DBUtils;
import eit.nl.utwente.sdm.Doctor;
import eit.nl.utwente.sdm.Employer;
import eit.nl.utwente.sdm.HealthClub;
import eit.nl.utwente.sdm.HealthRecord;
import eit.nl.utwente.sdm.Insurance;
import eit.nl.utwente.sdm.Mediator;
import eit.nl.utwente.sdm.Patient;
import eit.nl.utwente.sdm.TrustedAuthority;

public class Conductor {

	public static void main(String[] args) {
		Mediator m = new Mediator();
		TrustedAuthority ta = new TrustedAuthority(m);
		List<Patient> patients = DBUtils.getPatients();
		List<String> attributes = Conductor.getAttributes(patients);
		ta.setup(attributes);
		List<Doctor> doctors = DBUtils.getDoctors();
		List<Employer> employers = DBUtils.getEmployers();
		List<Insurance> insurances = DBUtils.getInsurances();
		List<HealthClub> hcs = DBUtils.getHealthClubs();
		ta.distributeKeys(patients, doctors, employers, insurances);
		List<HealthRecord> healthRecords = DBUtils.getHealthRecords();
		Conductor conductor = new Conductor();
		GUIPatient patientGUI = new GUIPatient(conductor, patients, ta);
		GUIDoctor guiDoc = new GUIDoctor(conductor, doctors, ta);
		GUIHealthClub guiHC =  new GUIHealthClub(conductor, hcs, patients, ta);
		conductor.addGUI(guiDoc);
		conductor.addGUI(patientGUI);
	}

	private List<IUpdatable> guis = new ArrayList<IUpdatable>();
	
	private void addGUI(IUpdatable gui) {
		guis.add(gui);
		
	}

	public static List<String> getAttributes(List<Patient> patients) {
		List<String> attributes = new ArrayList<String>();
		for (Patient patient : patients) {
			attributes.add("patient" + patient.getId() + "");
			attributes.add("patient" + patient.getId() + "'sDoc");
			attributes.add("patient" + patient.getId() + "'sInsurance");
			attributes.add("patient" + patient.getId() + "'sEmployer");
		}
		return attributes;
	}

	public void update() {
		for (IUpdatable upd : guis) {
			upd.update();
		}
	}

}
