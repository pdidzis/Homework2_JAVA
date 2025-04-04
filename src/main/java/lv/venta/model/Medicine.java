package lv.venta.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@ToString(exclude = {"mid", "medicalHistories"})
@Table(name = "medicine_table")
@Entity
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    @Column(name = "mid")
    private Long mid;

    @Column(name = "expiry_date")
    @NotNull
    private LocalDate expiryDate;

    @Column(name = "dosage")
    @NotNull
    private String dosage;

    @Column(name = "manufacturer")
    @NotNull
    private String manufacturer;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "usage_instructions")
    private String usageInstructions;

    @ManyToMany(mappedBy = "medicines")
    private List<MedicalHistory> medicalHistories;
    
    public Medicine(LocalDate inputExpiryDate, String inputDosage, String inputManufacturer, String inputName, String inputUsageInstructions) {
        setExpiryDate(inputExpiryDate);
        setDosage(inputDosage);
        setManufacturer(inputManufacturer);
        setName(inputName);
        setUsageInstructions(inputUsageInstructions);
    }

}