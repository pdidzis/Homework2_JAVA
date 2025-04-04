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
import lv.venta.model.enums.DoctorType;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"did"})
@Table(name = "doctor_table")
@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    @Column(name = "did")
    private Long did;

    public long getId() {
        return did;
    }
    
    @Column(name = "doctor_type")
    @NotNull
    private DoctorType doctorType;

    @Column(name = "experience_in_years")
    @NotNull
    private int experienceInYears;

    @Column(name = "certificate_number")
    @NotNull
    private String certificateNumber;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "person_code")
    @NotNull
    private String personCode;

    @Column(name = "surname")
    @NotNull
    private String surname;
    
    public Doctor(DoctorType inputDoctorType, int inputExperienceInYears, String inputCertificateNumber, String inputName, String inputPersonCode, String inputSurname) {
        setDoctorType(inputDoctorType);
        setExperienceInYears(inputExperienceInYears);
        setCertificateNumber(inputCertificateNumber);
        setName(inputName);
        setPersonCode(inputPersonCode);
        setSurname(inputSurname);
    }


    }
