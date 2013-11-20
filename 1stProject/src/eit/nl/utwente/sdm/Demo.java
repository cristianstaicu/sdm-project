package eit.nl.utwente.sdm;

import java.util.ArrayList;
import java.util.List;

public class Demo {

	public static void main(String[] args) {
		Mediator m = new Mediator();
		TrustedAuthority ta = new TrustedAuthority(m);
		/*
		 * Retrieve entities from DB and pass them to the TA for assigning
		 * attributes
		 */
		List<Patient> patients = DBUtils.getPatients();
		List<String> attributes = new ArrayList<String>();
		for (Patient patient : patients) {
			attributes.add(patient.getId() + "");
			attributes.add(patient.getId() + "'s Doc");
			attributes.add(patient.getId() + "'s Insurance");
			attributes.add(patient.getId() + "'s Employer");
		}
		/*
		 * Add more IDs for the patients to come
		 */
		ta.setup(attributes);

		List<Doctor> doctors = DBUtils.getDoctors();
		List<Employer> employers = DBUtils.getEmployers();
		List<Insurance> insurances = DBUtils.getInsurances();
		
		ta.distributeKeys(patients, doctors, employers, insurances);
		System.out.println("===Distributed keys===");
		System.out.println("-Patients-");
		for (Patient p : patients) {
			System.out.println(p.getId() + " " + p.getName() + " " + p.getKey());
		}
		System.out.println("-Doctors-");
		for (Doctor d : doctors) {
			System.out.println(d.getId() + " " + d.getName() + " " + d.getKey());
		}
		System.out.println("-Employers-");
		for (Employer e : employers) {
			System.out.println(e.getId() + " " + e.getName() + " " + e.getKey());
		}
		System.out.println("-Insurance Companies-");
		for (Insurance i : insurances) {
			System.out.println(i.getId() + " " + i.getName() + " " + i.getKey());
		}
	}

}
