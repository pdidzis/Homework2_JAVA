package lv.venta.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import lv.venta.model.Patient;

public interface IPatientRepo extends JpaRepository<Patient, Long> {
	Optional<Patient> findByPersonCode(String personCode);
}