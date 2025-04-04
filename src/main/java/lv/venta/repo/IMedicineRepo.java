package lv.venta.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import lv.venta.model.Medicine;

public interface IMedicineRepo extends JpaRepository<Medicine, Long> {
	
}