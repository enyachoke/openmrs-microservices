package org.openmrs.orders.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    @Column(name = "concept_uuid")
    private UUID conceptUuid;

    @Column(name = "orderer_uuid")
    private UUID ordererUuid;

    @Column(name = "encounter_uuid")
    private UUID encounterUuid;

    @Column(name = "instructions")
    private String instructions;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "auto_expire_date")
    private LocalDate autoExpireDate;

    @Column(name = "discontinued")
    private Boolean discontinued;

    @Column(name = "discontinued_date")
    private LocalDate discontinuedDate;

    @Column(name = "accession_number")
    private String accessionNumber;

    @Column(name = "discontinued_reason_non_coded")
    private String discontinuedReasonNonCoded;

    @Column(name = "patient_uuid")
    private UUID patientUuid;

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

    public Order uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getConceptUuid() {
        return conceptUuid;
    }

    public Order conceptUuid(UUID conceptUuid) {
        this.conceptUuid = conceptUuid;
        return this;
    }

    public void setConceptUuid(UUID conceptUuid) {
        this.conceptUuid = conceptUuid;
    }

    public UUID getOrdererUuid() {
        return ordererUuid;
    }

    public Order ordererUuid(UUID ordererUuid) {
        this.ordererUuid = ordererUuid;
        return this;
    }

    public void setOrdererUuid(UUID ordererUuid) {
        this.ordererUuid = ordererUuid;
    }

    public UUID getEncounterUuid() {
        return encounterUuid;
    }

    public Order encounterUuid(UUID encounterUuid) {
        this.encounterUuid = encounterUuid;
        return this;
    }

    public void setEncounterUuid(UUID encounterUuid) {
        this.encounterUuid = encounterUuid;
    }

    public String getInstructions() {
        return instructions;
    }

    public Order instructions(String instructions) {
        this.instructions = instructions;
        return this;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Order startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getAutoExpireDate() {
        return autoExpireDate;
    }

    public Order autoExpireDate(LocalDate autoExpireDate) {
        this.autoExpireDate = autoExpireDate;
        return this;
    }

    public void setAutoExpireDate(LocalDate autoExpireDate) {
        this.autoExpireDate = autoExpireDate;
    }

    public Boolean isDiscontinued() {
        return discontinued;
    }

    public Order discontinued(Boolean discontinued) {
        this.discontinued = discontinued;
        return this;
    }

    public void setDiscontinued(Boolean discontinued) {
        this.discontinued = discontinued;
    }

    public LocalDate getDiscontinuedDate() {
        return discontinuedDate;
    }

    public Order discontinuedDate(LocalDate discontinuedDate) {
        this.discontinuedDate = discontinuedDate;
        return this;
    }

    public void setDiscontinuedDate(LocalDate discontinuedDate) {
        this.discontinuedDate = discontinuedDate;
    }

    public String getAccessionNumber() {
        return accessionNumber;
    }

    public Order accessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
        return this;
    }

    public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    public String getDiscontinuedReasonNonCoded() {
        return discontinuedReasonNonCoded;
    }

    public Order discontinuedReasonNonCoded(String discontinuedReasonNonCoded) {
        this.discontinuedReasonNonCoded = discontinuedReasonNonCoded;
        return this;
    }

    public void setDiscontinuedReasonNonCoded(String discontinuedReasonNonCoded) {
        this.discontinuedReasonNonCoded = discontinuedReasonNonCoded;
    }

    public UUID getPatientUuid() {
        return patientUuid;
    }

    public Order patientUuid(UUID patientUuid) {
        this.patientUuid = patientUuid;
        return this;
    }

    public void setPatientUuid(UUID patientUuid) {
        this.patientUuid = patientUuid;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", conceptUuid='" + getConceptUuid() + "'" +
            ", ordererUuid='" + getOrdererUuid() + "'" +
            ", encounterUuid='" + getEncounterUuid() + "'" +
            ", instructions='" + getInstructions() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", autoExpireDate='" + getAutoExpireDate() + "'" +
            ", discontinued='" + isDiscontinued() + "'" +
            ", discontinuedDate='" + getDiscontinuedDate() + "'" +
            ", accessionNumber='" + getAccessionNumber() + "'" +
            ", discontinuedReasonNonCoded='" + getDiscontinuedReasonNonCoded() + "'" +
            ", patientUuid='" + getPatientUuid() + "'" +
            "}";
    }
}
