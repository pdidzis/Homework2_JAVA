package lv.venta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import lv.venta.model.Doctor;
import lv.venta.model.MedicalAppointment;
import lv.venta.model.MedicalHistory;
import lv.venta.model.Patient;
import lv.venta.model.enums.City;
import lv.venta.model.enums.DoctorType;
import lv.venta.service.impl.HospitalService;

@Controller
@RequestMapping("/hospital")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    // a. GET - /hospital/appointments/patient/{personalCode}
    @GetMapping("/appointments/patient/{personalCode}") //not working
    public String getControllerSelectAppointmentsByPatientPersonalCode(@PathVariable(name = "personalCode") String personalCode, Model model) {
        try {
            model.addAttribute("box", hospitalService.selectAllMedicalAppointmentsByPatientPersonalCode(personalCode));
            return "show-appointments-page";
        } catch (Exception e) {
            model.addAttribute("box", e.getMessage());
            return "error-page";
        }
    }

    // b. GET - /hospital/history/patient/{personalCode}  example > http://localhost:8080/hospital/history/patient/123456-45678
    @GetMapping("/history/patient/{personalCode}")
    public String getControllerSelectDiseaseHistoryByPatientPersonalCode(@PathVariable(name = "personalCode") String personalCode, Model model) {
        try {
            model.addAttribute("box", hospitalService.selectAllDiseaseHistoryByPatientPersonalCode(personalCode));
            return "show-history-page";
        } catch (Exception e) {
            model.addAttribute("box", e.getMessage());
            return "error-page";
        }
    }

    // c. GET - /hospital/history/severity/{threshold} working
    @GetMapping("/history/severity/{threshold}")
    public String getControllerSelectDiseaseHistorySeverityLargerThan(@PathVariable(name = "threshold") int threshold, Model model) {
        try {
            model.addAttribute("box", hospitalService.selectAllDiseaseHistoryGreaterThan(threshold));
            return "show-history-page";
        } catch (Exception e) {
            model.addAttribute("box", e.getMessage());
            return "error-page";
        }
    }

    // d. GET - /hospital/patients/city/{city}  // not working
    @GetMapping("/patients/city/{city}")
    public String getControllerSelectPatientsFromCity(@PathVariable(name = "city") String city, Model model) {
        try {
            model.addAttribute("box", hospitalService.selectAllPatientsFromCity(City.valueOf(city)));
            return "show-patients-page";
        } catch (Exception e) {
            model.addAttribute("box", e.getMessage());
            return "error-page";
        }
    }

    //im not sure if rest of these are working , when i was debuging/cleaning the code. Suddenly the code started to not work anymore :/
    
    // e. GET - /hospital/appointments/today/{doctorId}
    @GetMapping("/appointments/today/{doctorId}")
    public String getControllerSelectAppointmentsForDoctorToday(@PathVariable(name = "doctorId") long doctorId, Model model) {
        try {
            // Assuming HospitalService accepts a long doctorId and fetches the Doctor internally
            model.addAttribute("box", hospitalService.selectAllMedicalAppointmentsOfDoctorToday(doctorId));
            return "show-appointments-page";
        } catch (Exception e) {
            model.addAttribute("box", e.getMessage());
            return "error-page";
        }
    }

    // f. GET and POST - /hospital/change-doctor/{appointmentId}
    @GetMapping("/change-doctor/{appointmentId}")
    public String getControllerChangeDoctorForAppointmentForm(@PathVariable(name = "appointmentId") long appointmentId, Model model) {
        try {
            model.addAttribute("appointmentId", appointmentId);
            model.addAttribute("doctor", new Doctor()); // For form binding
            return "change-doctor-form";
        } catch (Exception e) {
            model.addAttribute("box", e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/change-doctor/{appointmentId}")
    public String postControllerChangeDoctorForAppointment(@PathVariable(name = "appointmentId") long appointmentId, @ModelAttribute Doctor newDoctor, Model model) {
        try {
            hospitalService.changeDoctorForAppointment(appointmentId, newDoctor);
            model.addAttribute("box", "Doctor changed for appointment ID " + appointmentId);
            return "show-appointments-page";
        } catch (Exception e) {
            model.addAttribute("box", e.getMessage());
            return "error-page";
        }
    }

    // g. GET - /hospital/patients/doctor-type/{doctorType}
    @GetMapping("/patients/doctor-type/{doctorType}")
    public String getControllerCalculatePatientsForDoctorType(@PathVariable(name = "doctorType") String doctorType, Model model) {
        try {
            int count = hospitalService.calculateManyPatientsForDoctorType(DoctorType.valueOf(doctorType));
            model.addAttribute("box", "Total unique patients for " + doctorType + ": " + count);
            return "data-page";
        } catch (Exception e) {
            model.addAttribute("box", e.getMessage());
            return "error-page";
        }
    }

    // h. GET and POST - /hospital/add-history
    @GetMapping("/add-history")
    public String getControllerInsertDiseaseHistoryForm(Model model) {
        try {
            model.addAttribute("patient", new Patient());
            return "add-history-form";
        } catch (Exception e) {
            model.addAttribute("box", e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/add-history")
    public String postControllerInsertDiseaseHistory(@ModelAttribute Patient patient, Model model) {
        try {
            hospitalService.insertNewDiseaseHistoryForPatient(patient);
            model.addAttribute("box", "Disease history added for patient with person code: " + patient.getPersonCode());
            return "show-history-page";
        } catch (Exception e) {
            model.addAttribute("box", e.getMessage());
            return "error-page";
        }
    }
}