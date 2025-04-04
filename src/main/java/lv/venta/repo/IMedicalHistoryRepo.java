package lv.venta.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import lv.venta.model.MedicalHistory;

public interface IMedicalHistoryRepo extends JpaRepository<MedicalHistory, Long> {

    @Modifying
    @Query("DELETE FROM MedicalHistory mh WHERE mh.doctor.did = :id")
    void deleteByDoctorId(long id);
}