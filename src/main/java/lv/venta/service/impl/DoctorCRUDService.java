package lv.venta.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import lv.venta.model.Doctor;
import lv.venta.model.enums.DoctorType;
import lv.venta.repo.IDoctorRepo;
import lv.venta.repo.IMedicalAppointmentRepo;
import lv.venta.repo.IMedicalHistoryRepo;
import lv.venta.service.IDoctorCRUDService;

@Service
public class DoctorCRUDService implements IDoctorCRUDService {

    private IDoctorRepo doctorRepo;
    private IMedicalAppointmentRepo medicalAppointmentRepo;
    private IMedicalHistoryRepo medicalHistoryRepo;

    
    public DoctorCRUDService(IDoctorRepo doctorRepoParam, IMedicalAppointmentRepo medicalAppointmentRepoParam, IMedicalHistoryRepo medicalHistoryRepoParam) {
        doctorRepo = doctorRepoParam;
        medicalAppointmentRepo = medicalAppointmentRepoParam;
        medicalHistoryRepo = medicalHistoryRepoParam;
    }


    @Override
    public List<Doctor> selectAllDoctors() {
        return doctorRepo.findAll(); // no printing here
    }

    @Override
    public Doctor selectDoctorById(long id) {
        return doctorRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
    }

    @Override
    public void deleteDoctorById(long id) {
        // Remove references from MedicalAppointment
        medicalAppointmentRepo.deleteByDoctorId(id);
        // Remove references from MedicalHistory
        medicalHistoryRepo.deleteByDoctorId(id);
        // Delete the Doctor
        doctorRepo.deleteById(id);
    }

    @Override
    public void updateDoctorById(long id, Doctor updatedDoctor) {
        Doctor doctor = doctorRepo.findById(id).orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
        
        // Update the doctor's fields with the new values
        doctor.setDoctorType(updatedDoctor.getDoctorType());
        doctor.setExperienceInYears(updatedDoctor.getExperienceInYears());
        doctor.setCertificateNumber(updatedDoctor.getCertificateNumber());
        doctor.setName(updatedDoctor.getName());
        doctor.setPersonCode(updatedDoctor.getPersonCode());
        doctor.setSurname(updatedDoctor.getSurname());

        // Save the updated doctor to the database
        doctorRepo.save(doctor);
        System.out.println("Doctor updated with id " + id + ": " + doctor);
    }


    @Override
    public void insertNewDoctor(Doctor doctor) {
    	doctorRepo.save(doctor);
    	System.out.println("New Doctor added: " + doctor);
    }
}