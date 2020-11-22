package org.openmrs.orders.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * A DrugOrder.
 */
@Entity
@Table(name = "drug_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DrugOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    @Column(name = "drug_inventory_uuid")
    private UUID drugInventoryUuid;

    @Column(name = "dose")
    private Double dose;

    @Column(name = "equivalent_daily_dose")
    private Double equivalentDailyDose;

    @Column(name = "units")
    private String units;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "prn")
    private Boolean prn;

    @Column(name = "complex")
    private Boolean complex;

    @Column(name = "quantity")
    private Integer quantity;

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

    public DrugOrder uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getDrugInventoryUuid() {
        return drugInventoryUuid;
    }

    public DrugOrder drugInventoryUuid(UUID drugInventoryUuid) {
        this.drugInventoryUuid = drugInventoryUuid;
        return this;
    }

    public void setDrugInventoryUuid(UUID drugInventoryUuid) {
        this.drugInventoryUuid = drugInventoryUuid;
    }

    public Double getDose() {
        return dose;
    }

    public DrugOrder dose(Double dose) {
        this.dose = dose;
        return this;
    }

    public void setDose(Double dose) {
        this.dose = dose;
    }

    public Double getEquivalentDailyDose() {
        return equivalentDailyDose;
    }

    public DrugOrder equivalentDailyDose(Double equivalentDailyDose) {
        this.equivalentDailyDose = equivalentDailyDose;
        return this;
    }

    public void setEquivalentDailyDose(Double equivalentDailyDose) {
        this.equivalentDailyDose = equivalentDailyDose;
    }

    public String getUnits() {
        return units;
    }

    public DrugOrder units(String units) {
        this.units = units;
        return this;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getFrequency() {
        return frequency;
    }

    public DrugOrder frequency(String frequency) {
        this.frequency = frequency;
        return this;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Boolean isPrn() {
        return prn;
    }

    public DrugOrder prn(Boolean prn) {
        this.prn = prn;
        return this;
    }

    public void setPrn(Boolean prn) {
        this.prn = prn;
    }

    public Boolean isComplex() {
        return complex;
    }

    public DrugOrder complex(Boolean complex) {
        this.complex = complex;
        return this;
    }

    public void setComplex(Boolean complex) {
        this.complex = complex;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public DrugOrder quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DrugOrder)) {
            return false;
        }
        return id != null && id.equals(((DrugOrder) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DrugOrder{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", drugInventoryUuid='" + getDrugInventoryUuid() + "'" +
            ", dose=" + getDose() +
            ", equivalentDailyDose=" + getEquivalentDailyDose() +
            ", units='" + getUnits() + "'" +
            ", frequency='" + getFrequency() + "'" +
            ", prn='" + isPrn() + "'" +
            ", complex='" + isComplex() + "'" +
            ", quantity=" + getQuantity() +
            "}";
    }
}
