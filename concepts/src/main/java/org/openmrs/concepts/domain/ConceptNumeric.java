package org.openmrs.concepts.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * A ConceptNumeric.
 */
@Entity
@Table(name = "concept_numeric")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConceptNumeric implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    @Column(name = "hi_absolute")
    private Double hiAbsolute;

    @Column(name = "hi_normal")
    private Double hiNormal;

    @Column(name = "hi_critical")
    private Double hiCritical;

    @Column(name = "low_absolute")
    private Double lowAbsolute;

    @Column(name = "low_normal")
    private Double lowNormal;

    @Column(name = "low_critical")
    private Double lowCritical;

    @Column(name = "units")
    private String units;

    @Column(name = "precise")
    private Boolean precise;

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

    public ConceptNumeric uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Double getHiAbsolute() {
        return hiAbsolute;
    }

    public ConceptNumeric hiAbsolute(Double hiAbsolute) {
        this.hiAbsolute = hiAbsolute;
        return this;
    }

    public void setHiAbsolute(Double hiAbsolute) {
        this.hiAbsolute = hiAbsolute;
    }

    public Double getHiNormal() {
        return hiNormal;
    }

    public ConceptNumeric hiNormal(Double hiNormal) {
        this.hiNormal = hiNormal;
        return this;
    }

    public void setHiNormal(Double hiNormal) {
        this.hiNormal = hiNormal;
    }

    public Double getHiCritical() {
        return hiCritical;
    }

    public ConceptNumeric hiCritical(Double hiCritical) {
        this.hiCritical = hiCritical;
        return this;
    }

    public void setHiCritical(Double hiCritical) {
        this.hiCritical = hiCritical;
    }

    public Double getLowAbsolute() {
        return lowAbsolute;
    }

    public ConceptNumeric lowAbsolute(Double lowAbsolute) {
        this.lowAbsolute = lowAbsolute;
        return this;
    }

    public void setLowAbsolute(Double lowAbsolute) {
        this.lowAbsolute = lowAbsolute;
    }

    public Double getLowNormal() {
        return lowNormal;
    }

    public ConceptNumeric lowNormal(Double lowNormal) {
        this.lowNormal = lowNormal;
        return this;
    }

    public void setLowNormal(Double lowNormal) {
        this.lowNormal = lowNormal;
    }

    public Double getLowCritical() {
        return lowCritical;
    }

    public ConceptNumeric lowCritical(Double lowCritical) {
        this.lowCritical = lowCritical;
        return this;
    }

    public void setLowCritical(Double lowCritical) {
        this.lowCritical = lowCritical;
    }

    public String getUnits() {
        return units;
    }

    public ConceptNumeric units(String units) {
        this.units = units;
        return this;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Boolean isPrecise() {
        return precise;
    }

    public ConceptNumeric precise(Boolean precise) {
        this.precise = precise;
        return this;
    }

    public void setPrecise(Boolean precise) {
        this.precise = precise;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConceptNumeric)) {
            return false;
        }
        return id != null && id.equals(((ConceptNumeric) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConceptNumeric{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", hiAbsolute=" + getHiAbsolute() +
            ", hiNormal=" + getHiNormal() +
            ", hiCritical=" + getHiCritical() +
            ", lowAbsolute=" + getLowAbsolute() +
            ", lowNormal=" + getLowNormal() +
            ", lowCritical=" + getLowCritical() +
            ", units='" + getUnits() + "'" +
            ", precise='" + isPrecise() + "'" +
            "}";
    }
}
