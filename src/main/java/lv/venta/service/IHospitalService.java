package lv.venta.service;

import java.util.List;
import lv.venta.model.Doctor;
import lv.venta.model.MedicalAppointment;
import lv.venta.model.MedicalHistory;
import lv.venta.model.Patient;
import lv.venta.model.enums.City;
import lv.venta.model.enums.DoctorType;

public interface IHospitalService {
	
	//Creating appointment
	void insertNewMedicalAppointment(MedicalAppointment appointment);
	List<Patient> selectAllPatients();
	List<Patient> selectAllPatientsFromCity(City city);
	
    // a) Finds and returns all medical appointments for a specific patient using their person code
    List<MedicalAppointment> selectAllMedicalAppointmentsByPatientPersonalCode(String personalCode);
    // b) Finds and returns all disease history records for a patient using their person code
    List<MedicalHistory> selectAllDiseaseHistoryByPatientPersonalCode(String personalCode);
    // c) Finds and returns all disease history records where the severity level is greater than the given threshold
    List<MedicalHistory> selectAllDiseaseHistoryGreaterThan(int threshold);
    // d) Finds and returns all medical appointments scheduled for a specific doctor today
    List<MedicalAppointment> selectAllMedicalAppointmentsOfDoctorToday(Doctor doctor);
    // e) Changes the assigned doctor for a specific appointment, checks if the new doctor has the same DoctorType
    void changeDoctorForAppointment(MedicalAppointment appointment, Doctor newDoctor);
    // f) Calculates and returns the total number of unique patients for a given doctor type
    void calculateManyPatientsForDoctorType(DoctorType doctorType);
    // g) Adds a new entry to a patient’s disease history, ensures at least one prescribed medication is provided
    void insertNewDiseaseHistoryForPatient(Patient patient);
}