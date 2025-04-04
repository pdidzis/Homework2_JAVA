package lv.venta.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lv.venta.model.Doctor;
import lv.venta.model.MedicalAppointment;
import lv.venta.model.MedicalHistory;
import lv.venta.model.Medicine;
import lv.venta.model.Patient;
import lv.venta.model.enums.City;
import lv.venta.model.enums.Disease;
import lv.venta.model.enums.DoctorType;
import lv.venta.repo.IMedicalAppointmentRepo;
import lv.venta.repo.IMedicalHistoryRepo;
import lv.venta.repo.IMedicineRepo;
import lv.venta.repo.IPatientRepo;
import lv.venta.service.IHospitalService;

@Service
public class HospitalService implements IHospitalService {

    private IMedicalAppointmentRepo medicalAppointmentRepo;
    private IMedicalHistoryRepo medicalHistoryRepo;
    private IPatientRepo patientRepo;
    private IMedicineRepo medicineRepo;

    
    @Override
    public List<Patient> selectAllPatients() {
        return patientRepo.findAll();
    }
    
    
    @Autowired
    public HospitalService(IMedicalAppointmentRepo medicalAppointmentRepoParam, IMedicalHistoryRepo medicalHistoryRepoParam, 
                          IPatientRepo patientRepoParam, IMedicineRepo medicineRepoParam) {
        medicalAppointmentRepo = medicalAppointmentRepoParam;
        medicalHistoryRepo = medicalHistoryRepoParam;
        patientRepo = patientRepoParam;
        medicineRepo = medicineRepoParam;
        
    }
    
    public void insertNewMedicalAppointment(MedicalAppointment appointment) {
        medicalAppointmentRepo.save(appointment);
    }
    
    @Override
    public List<MedicalAppointment> selectAllMedicalAppointmentsByPatientPersonalCode(String personalCode) {
        List<MedicalAppointment> allAppointments = medicalAppointmentRepo.findAll();
        List<MedicalAppointment> filteredAppointments = new ArrayList<>();
        
        for (MedicalAppointment appointment : allAppointments) {
            if (appointment.getPatient().getPersonCode().equals(personalCode)) {
                filteredAppointments.add(appointment);
            }
        }
        
        return filteredAppointments;
    }

    @Override
    public List<MedicalHistory> selectAllDiseaseHistoryByPatientPersonalCode(String personalCode) {
        List<MedicalHistory> allHistories = medicalHistoryRepo.findAll();
        List<MedicalHistory> filteredHistories = new ArrayList<>();
        
        for (MedicalHistory history : allHistories) {
            if (history.getPatient().getPersonCode().equals(personalCode)) {
                filteredHistories.add(history);
            }
        }
        
        return filteredHistories;
    }

    @Override
    public List<MedicalHistory> selectAllDiseaseHistoryGreaterThan(int threshold) {
        List<MedicalHistory> allHistories = medicalHistoryRepo.findAll();
        List<MedicalHistory> filteredHistories = new ArrayList<>();
        
        for (MedicalHistory history : allHistories) {
            if (history.getSeverity() > threshold) {
                filteredHistories.add(history);
            }
        }
        
        return filteredHistories;
    }

    @Override
    public List<MedicalAppointment> selectAllMedicalAppointmentsOfDoctorToday(Doctor doctor) {
        LocalDate today = LocalDate.now();
        List<MedicalAppointment> allAppointments = medicalAppointmentRepo.findAll();
        List<MedicalAppointment> filteredAppointments = new ArrayList<>();
        
        for (MedicalAppointment appointment : allAppointments) {
            if (appointment.getDoctor().equals(doctor) && appointment.getDateTime().toLocalDate().equals(today)) {
                filteredAppointments.add(appointment);
            }
        }
        
        return filteredAppointments;
    }

    @Override
    public void changeDoctorForAppointment(MedicalAppointment appointment, Doctor newDoctor) {
        if (appointment.getDoctor().getDoctorType() != newDoctor.getDoctorType()) {
            throw new RuntimeException("New doctor must have the same DoctorType as the original doctor");
        }
        appointment.setDoctor(newDoctor);
        medicalAppointmentRepo.save(appointment);
    }

    @Override
    public void calculateManyPatientsForDoctorType(DoctorType doctorType) {
        List<MedicalHistory> allHistories = medicalHistoryRepo.findAll();
        List<String> uniquePatientCodes = new ArrayList<>();
        
        for (MedicalHistory history : allHistories) {
            if (history.getDoctor().getDoctorType() == doctorType) {
                String patientCode = history.getPatient().getPersonCode();
                if (!uniquePatientCodes.contains(patientCode)) {
                    uniquePatientCodes.add(patientCode);
                }
            }
        }
        
        System.out.println("Total unique patients for DoctorType " + doctorType + ": " + uniquePatientCodes.size());
    }

    @Override
    public void insertNewDiseaseHistoryForPatient(Patient patient) {
        Medicine medicine = new Medicine(LocalDate.of(2025, 4, 4), "1 tablet daily", "PharmaCo", "Aspirin", "Take with water");
        medicineRepo.save(medicine);
        MedicalHistory medicalHistory = new MedicalHistory(
            new Doctor(DoctorType.oncologist, 5, "TEMP_CERT", "TempDoc", "123456-78901", "TempSurname"), // Temporary doctor
            Disease.flu, // Valid disease
            LocalDate.now(),
            0,
            patient,
            2,
            "New flu case"
        );
        medicalHistory.setMedicines(Arrays.asList(medicine));
        medicalHistoryRepo.save(medicalHistory);
        System.out.println("New disease history added for patient: " + patient.getPersonCode());
        
    }
    @Override
    public List<Patient> selectAllPatientsFromCity(City city) {
        List<Patient> allPatients = patientRepo.findAll();
        List<Patient> filteredPatients = new ArrayList<>();
        
        for (Patient patient : allPatients) {
            if (patient.getAddress() != null && patient.getAddress().getCity() == city) {
                filteredPatients.add(patient);
            }
        }
        
        return filteredPatients;
    }
}