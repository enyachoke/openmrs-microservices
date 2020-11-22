package org.openmrs.concepts.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * A Drug.
 */
@Entity
@Table(name = "drug")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Drug implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "combination")
    private Boolean combination;

    @Column(name = "dosage_form")
    private Integer dosageForm;

    @Column(name = "dose_strength")
    private Double doseStrength;

    @Column(name = "maximum_daily_dose")
    private Double maximumDailyDose;

    @Column(name = "minimum_daily_dose")
    private Double minimumDailyDose;

    @Column(name = "route")
    private Integer route;

    @Column(name = "units")
    private String units;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Drug uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public Drug name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isCombination() {
        return combination;
    }

    public Drug combination(Boolean combination) {
        this.combination = combination;
        return this;
    }

    public void setCombination(Boolean combination) {
        this.combination = combination;
    }

    public Integer getDosageForm() {
        return dosageForm;
    }

    public Drug dosageForm(Integer dosageForm) {
        this.dosageForm = dosageForm;
        return this;
    }

    public void setDosageForm(Integer dosageForm) {
        this.dosageForm = dosageForm;
    }

    public Double getDoseStrength() {
        return doseStrength;
    }

    public Drug doseStrength(Double doseStrength) {
        this.doseStrength = doseStrength;
        return this;
    }

    public void setDoseStrength(Double doseStrength) {
        this.doseStrength = doseStrength;
    }

    public Double getMaximumDailyDose() {
        return maximumDailyDose;
    }

    public Drug maximumDailyDose(Double maximumDailyDose) {
        this.maximumDailyDose = maximumDailyDose;
        return this;
    }

    public void setMaximumDailyDose(Double maximumDailyDose) {
        this.maximumDailyDose = maximumDailyDose;
    }

    public Double getMinimumDailyDose() {
        return minimumDailyDose;
    }

    public Drug minimumDailyDose(Double minimumDailyDose) {
        this.minimumDailyDose = minimumDailyDose;
        return this;
    }

    public void setMinimumDailyDose(Double minimumDailyDose) {
        this.minimumDailyDose = minimumDailyDose;
    }

    public Integer getRoute() {
        return route;
    }

    public Drug route(Integer route) {
        this.route = route;
        return this;
    }

    public void setRoute(Integer route) {
        this.route = route;
    }

    public String getUnits() {
        return units;
    }

    public Drug units(String units) {
        this.units = units;
        return this;
    }

    public void setUnits(String units) {
        this.units = units;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Drug)) {
            return false;
        }
        return id != null && id.equals(((Drug) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Drug{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", name='" + getName() + "'" +
            ", combination='" + isCombination() + "'" +
            ", dosageForm=" + getDosageForm() +
            ", doseStrength=" + getDoseStrength() +
            ", maximumDailyDose=" + getMaximumDailyDose() +
            ", minimumDailyDose=" + getMinimumDailyDose() +
            ", route=" + getRoute() +
            ", units='" + getUnits() + "'" +
            "}";
    }
}
