package lv.venta.model;

import java.time.LocalDateTime;

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
@ToString(exclude = {"mid", "doctor", "patient"})
@Table(name = "medical_appointment_table")
@Entity
public class MedicalAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    @Column(name = "mid")
    private Long mid;

    @ManyToOne
    @JoinColumn(name = "did", referencedColumnName = "did")
    @NotNull
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "pid", referencedColumnName = "pid")
    @NotNull
    private Patient patient;

    @Column(name = "datetime")
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "cabinet")
    @NotNull
    private String cabinet;


    public MedicalAppointment(Doctor inputDoctor, Patient inputPatient, LocalDateTime inputDateTime, String inputCabinet) {
    	setDoctor(inputDoctor);
    	setPatient(inputPatient);
    	setDateTime(inputDateTime);
    	setCabinet(inputCabinet);
    }



}