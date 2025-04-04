package lv.venta.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lv.venta.model.Doctor;
import lv.venta.service.impl.DoctorCRUDService;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorCRUDService doctorCRUDService;

    @GetMapping("/show/all") // localhost:8080/doctor/show/all
    public String getControllerShowAllDoctors(Model model) {
        try {
        	List<Doctor> allDoctors = doctorCRUDService.selectAllDoctors(); 

            model.addAttribute("box", allDoctors);
            return "show-doctors-page";
        } catch (Exception e) {
            model.addAttribute("box", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/show/{id}") // localhost:8080/doctor/show/1
    public String getControllerShowDoctorById(@PathVariable(name = "id") long id, Model model) {
        try {
            Doctor doctor = doctorCRUDService.selectDoctorById(id);
            model.addAttribute("box", doctor);
            return "show-one-doctor-page";
        } catch (Exception e) {
            model.addAttribute("box", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/remove/{id}") // localhost:8080/doctor/remove/1
    public String getControllerRemoveDoctorForm(@PathVariable(name = "id") long id, Model model) {
        try {
            model.addAttribute("doctorId", id);
            return "remove-doctor-form";
        } catch (Exception e) {
            model.addAttribute("box", e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/remove/{id}") // submit removal
    public String postControllerRemoveDoctor(@PathVariable(name = "id") long id, Model model) {
        try {
            doctorCRUDService.deleteDoctorById(id);
            model.addAttribute("box", "Doctor with ID " + id + " removed");
            return "show-doctors-page";
        } catch (Exception e) {
            model.addAttribute("box", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/add") // localhost:8080/doctor/add
    public String getControllerAddDoctorForm(Model model) {
        try {
            model.addAttribute("doctor", new Doctor());
            return "add-doctor-form";
        } catch (Exception e) {
            model.addAttribute("box", e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/add") // submit new doctor
    public String postControllerAddDoctor(@ModelAttribute Doctor doctor, Model model) {
        try {
            doctorCRUDService.insertNewDoctor(doctor);
            model.addAttribute("box", "Doctor added: " + doctor.getName() + " " + doctor.getSurname());
            return "show-doctors-page";
        } catch (Exception e) {
            model.addAttribute("box", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/update/{id}") // localhost:8080/doctor/update/1
    public String getControllerUpdateDoctorForm(@PathVariable(name = "id") long id, Model model) {
        try {
            Doctor doctor = doctorCRUDService.selectDoctorById(id);
            model.addAttribute("doctor", doctor);
            return "update-doctor-form";
        } catch (Exception e) {
            model.addAttribute("box", e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/update/{id}") // submit update
    public String postControllerUpdateDoctor(@PathVariable(name = "id") long id, @ModelAttribute Doctor doctor, Model model) {
        try {
            doctorCRUDService.updateDoctorById(id, doctor);
            model.addAttribute("box", "Doctor with ID " + id + " updated");
            return "show-doctors-page";
        } catch (Exception e) {
            model.addAttribute("box", e.getMessage());
            return "error-page";
        }
    }
}
