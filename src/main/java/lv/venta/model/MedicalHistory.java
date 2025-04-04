package lv.venta.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lv.venta.model.enums.Disease;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"pdhid", "doctor", "patient", "medicines"})
@Table(name = "patient_dis_history_table")
@Entity
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    @Column(name = "pdhid")
    private Long pdhid;

    @ManyToOne
    @JoinColumn(name = "did", referencedColumnName = "did")
    @NotNull
    private Doctor doctor;

    @Column(name = "disease")
    @NotNull
    private Disease disease;

    @Column(name = "disease_starting_date")
    @NotNull
    private LocalDate diseaseStartingDate;

    @Column(name = "is_present")
    @NotNull
    private int isPresent;

    @ManyToOne
    @JoinColumn(name = "pid", referencedColumnName = "pid")
    @NotNull
    private Patient patient;

    @Column(name = "severity")
    @NotNull
    private int severity;

    @Column(name = "notes")
    private String notes;

    @ManyToMany
    @JoinTable(
        name = "medicine_pat_dis_history_table",
        joinColumns = @JoinColumn(name = "pdhid"),
        inverseJoinColumns = @JoinColumn(name = "mid")
    )
    private List<Medicine> medicines;


    public MedicalHistory(Doctor inputDoctor, Disease inputDisease, LocalDate inputDiseaseStartingDate, int inputIsPresent, Patient inputPatient, int inputSeverity, String inputNotes) {
    	setDoctor(inputDoctor);
    	setDisease(inputDisease);
    	setDiseaseStartingDate(inputDiseaseStartingDate);
    	setIsPresent(inputIsPresent);
    	setPatient(inputPatient);
    	setSeverity(inputSeverity);
    	setNotes(inputNotes);
    }
    
}