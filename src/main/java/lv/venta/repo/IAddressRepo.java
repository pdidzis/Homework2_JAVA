package lv.venta.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import lv.venta.model.Address;

public interface IAddressRepo extends JpaRepository<Address, Long> {
	
}