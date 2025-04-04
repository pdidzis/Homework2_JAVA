package lv.venta.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"pid", "address"})
@Table(name = "patient_table")
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    @Column(name = "pid")
    private Long pid;

    @ManyToOne
    @JoinColumn(name = "aid")
    @NotNull
    private Address address;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "patient_code")
    @NotNull
    private String patientCode;

    @Column(name = "person_code")
    @NotNull
    private String personCode;

    @Column(name = "phone_no")
    @NotNull
    private String phoneNo;

    @Column(name = "surname")
    @NotNull
    private String surname;
    
    
 

    
    public Patient(Address inputAddress, String inputName, String inputPatientCode, String inputPersonCode, String inputPhoneNo, String inputSurname) {
        setAddress(inputAddress);
        setName(inputName);
        setPatientCode(inputPatientCode);
        setPersonCode(inputPersonCode);
        setPhoneNo(inputPhoneNo);
        setSurname(inputSurname);
    }



}