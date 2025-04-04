package lv.venta.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import lv.venta.model.Doctor;

public interface IDoctorRepo extends JpaRepository<Doctor, Long> {
	
}