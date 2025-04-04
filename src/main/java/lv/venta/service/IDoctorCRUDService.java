package lv.venta.service;

import java.util.List;

import lv.venta.model.Doctor;


public interface IDoctorCRUDService {
    List<Doctor> selectAllDoctors(); // return list
    Doctor selectDoctorById(long id); // return doctor
    void deleteDoctorById(long id);
    void updateDoctorById(long id, Doctor updatedDoctor);
    void insertNewDoctor(Doctor doctor); // accept new doctor as parameter
}