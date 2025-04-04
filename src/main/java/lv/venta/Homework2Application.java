package lv.venta;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import lv.venta.model.Address;
import lv.venta.model.Doctor;
import lv.venta.model.MedicalAppointment;
import lv.venta.model.MedicalHistory;
import lv.venta.model.Medicine;
import lv.venta.model.Patient;
import lv.venta.model.enums.City;
import lv.venta.model.enums.Disease;
import lv.venta.model.enums.DoctorType;
import lv.venta.repo.IAddressRepo;
import lv.venta.repo.IDoctorRepo;
import lv.venta.repo.IMedicalAppointmentRepo;
import lv.venta.repo.IMedicalHistoryRepo;
import lv.venta.repo.IMedicineRepo;
import lv.venta.repo.IPatientRepo;
import lv.venta.service.impl.DoctorCRUDService;
import lv.venta.service.impl.HospitalService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Homework2Application {

    public static void main(String[] args) {
        SpringApplication.run(Homework2Application.class, args);
    }

    @Bean
    CommandLineRunner testModel(IAddressRepo addressRepo, IPatientRepo patientRepo, IDoctorRepo doctorRepo, 
                               IMedicalHistoryRepo medicalHistoryRepo, IMedicalAppointmentRepo medicalAppointmentRepo, 
                               IMedicineRepo medicineRepo, DoctorCRUDService doctorCRUDService, HospitalService hospitalService) {
        return args -> {
            // Create and save test data
            Address a1 = new Address(City.Ventpils, 11, "5th Avenue");
            Address a2 = new Address(City.Riga, 49, "Broadway");
            addressRepo.save(a1);
            addressRepo.save(a2);

            Patient p1 = new Patient(a1, "John", "D.J123456-45678", "123456-45678", "+127834560", "Doe");
            Patient p2 = new Patient(a2, "Emily", "W.E123467-34567", "123467-34567", "+129812345", "Williams");
            patientRepo.save(p1);
            patientRepo.save(p2);

            Doctor d1 = new Doctor(DoctorType.oncologist, 5, "IU45", "James", "123423-45321", "Miller");
            Doctor d2 = new Doctor(DoctorType.surgeon, 15, "IU34", "Peter", "129384-12323", "Davis");
            doctorRepo.save(d1);
            doctorRepo.save(d2);

            Medicine m1 = new Medicine(LocalDate.of(2025, 3, 24), "1 tablet per day", "GmbH", "Thuprofen", "Only for severe pain");
            Medicine m2 = new Medicine(LocalDate.of(2025, 3, 26), "1 tablet every other day", "Lyl", "Vitamin C", "Every other day");
            medicineRepo.save(m1);
            medicineRepo.save(m2);

            MedicalHistory mh1 = new MedicalHistory(d1, Disease.CANCER, LocalDate.of(2025, 3, 20), 0, p1, 3, "Severe flu");
            mh1.setMedicines(Arrays.asList(m1));
            MedicalHistory mh2 = new MedicalHistory(d2, Disease.test, LocalDate.of(2025, 3, 20), 0, p2, 1, "COVID-19 test");
            mh2.setMedicines(Arrays.asList(m2));
            medicalHistoryRepo.save(mh1);
            medicalHistoryRepo.save(mh2);

            MedicalAppointment ma1 = new MedicalAppointment(d1, p1, LocalDateTime.of(2025, 4, 4, 10, 0, 0), "A105"); // Match today's date for testing
            MedicalAppointment ma2 = new MedicalAppointment(d2, p2, LocalDateTime.of(2025, 3, 25, 3, 41, 15, 345540000), "D234");
            medicalAppointmentRepo.save(ma1);
            medicalAppointmentRepo.save(ma2);

            // Test DoctorCRUDService methods
            System.out.println("Testing selectAllDoctors:");
            doctorCRUDService.selectAllDoctors();

            System.out.println("Testing selectDoctorById (ID 1):");
            doctorCRUDService.selectDoctorById(1L);

            System.out.println("Testing updateDoctorById (ID 1):");
            Doctor updatedDoctor = new Doctor(DoctorType.cardiologist, 10, "NEW_CERT", "UpdatedJames", "123423-45321", "UpdatedMiller");
            doctorCRUDService.updateDoctorById(1L, updatedDoctor);

            System.out.println("Testing insertNewDoctor:");
            Doctor newDoctor = new Doctor(
            	    DoctorType.cardiologist,     // or any DoctorType you want
            	    8,                           // experience in years
            	    "CERT2025",                  // certificate number
            	    "Testing it",                     // name
            	    "111222-33344",              // person code
            	    "Johnson"                    // surname
            	);

            	doctorCRUDService.insertNewDoctor(newDoctor); 


            // Test HospitalService methods
            System.out.println("Testing selectAllMedicalAppointmentsByPatientPersonalCode (John):");
            List<MedicalAppointment> appointments = hospitalService.selectAllMedicalAppointmentsByPatientPersonalCode("123456-45678");
            for (MedicalAppointment appt : appointments) {
                System.out.println(appt);
            }

            System.out.println("Testing selectAllDiseaseHistoryByPatientPersonalCode (John):");
            List<MedicalHistory> histories = hospitalService.selectAllDiseaseHistoryByPatientPersonalCode("123456-45678");
            for (MedicalHistory history : histories) {
                System.out.println(history);
            }

            System.out.println("Testing selectAllDiseaseHistoryGreaterThan (2):");
            List<MedicalHistory> highSeverity = hospitalService.selectAllDiseaseHistoryGreaterThan(2);
            for (MedicalHistory history : highSeverity) {
                System.out.println(history);
            }

            System.out.println("Testing selectAllMedicalAppointmentsOfDoctorToday (d1):");
            List<MedicalAppointment> todayAppointments = hospitalService.selectAllMedicalAppointmentsOfDoctorToday(d1);
            for (MedicalAppointment appt : todayAppointments) {
                System.out.println(appt);
            }

            System.out.println("Testing changeDoctorForAppointment:");
            Doctor newDoctor1 = new Doctor(DoctorType.oncologist, 7, "NEW_CERT2", "NewDoc", "987654-32109", "NewSurname");
            doctorRepo.save(newDoctor1);
            hospitalService.changeDoctorForAppointment(ma1, newDoctor1);
            System.out.println("Updated appointment: " + ma1);

            System.out.println("Testing calculateManyPatientsForDoctorType (oncologist):");
            hospitalService.calculateManyPatientsForDoctorType(DoctorType.oncologist);

            System.out.println("Testing insertNewDiseaseHistoryForPatient:");
            Medicine newMedicine = new Medicine(LocalDate.of(2025, 4, 4), "1 tablet daily", "PharmaCo", "Aspirin", "Take with water");
            medicineRepo.save(newMedicine);
            MedicalHistory newHistory = new MedicalHistory(d1, Disease.flu, LocalDate.now(), 0, p1, 2, "New flu case");
            newHistory.setMedicines(Arrays.asList(newMedicine));
            //hospitalService.insertNewDiseaseHistoryForPatient(p1); i'm not sure why is not working , i'll try to fix if i'll have time for it
            //System.out.println("New disease history added for patient: " + p1.getPersonCode());

            System.out.println("Test data and service methods executed.");
            
            Patient patient = patientRepo.findByPersonCode("123456-45678")
                    .orElseThrow(() -> new RuntimeException("Patient not found"));
            
            Doctor doctor = doctorRepo.findById(1L)
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
            
            MedicalAppointment appointment = new MedicalAppointment(doctor, patient, LocalDateTime.of(2025, 4, 4, 10, 0), "A105");
            
            medicalAppointmentRepo.save(appointment);
            
            System.out.println("Medical appointment saved for patient: " + patient.getName());
            
        };
            
    }
}