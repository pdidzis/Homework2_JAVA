package lv.venta.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lv.venta.model.enums.City;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"aid"})
@Table(name = "address_table")
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    @Column(name = "aid")
    private Long aid;

    @Column(name = "city")
    @NotNull
    private City city;

    @Column(name = "house_no")
    @NotNull
    private int houseNo;

    @Column(name = "street_or_house_title")
    @NotNull
    private String streetOrHouseTitle;
    
    
    public Address(City inputCity, int inputHouseNo , String inputStreetOrHouseTitle) {
		setCity(inputCity);
		setHouseNo(inputHouseNo);
		setStreetOrHouseTitle(inputStreetOrHouseTitle);
	}

}