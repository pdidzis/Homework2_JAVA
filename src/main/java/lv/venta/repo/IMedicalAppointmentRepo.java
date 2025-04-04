package lv.venta.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import lv.venta.model.MedicalAppointment;

public interface IMedicalAppointmentRepo extends JpaRepository<MedicalAppointment, Long> {

    @Modifying
    @Query("DELETE FROM MedicalAppointment ma WHERE ma.doctor.did = :id")
    void deleteByDoctorId(long id);
    
}