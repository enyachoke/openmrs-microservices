package org.openmrs.concepts.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * A ConceptDataType.
 */
@Entity
@Table(name = "concept_data_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConceptDataType implements Serializable {

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

    @Column(name = "hl_7_abbreviation")
    private String hl7Abbreviation;

    @Column(name = "description")
    private String description;

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

    public ConceptDataType uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public ConceptDataType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHl7Abbreviation() {
        return hl7Abbreviation;
    }

    public ConceptDataType hl7Abbreviation(String hl7Abbreviation) {
        this.hl7Abbreviation = hl7Abbreviation;
        return this;
    }

    public void setHl7Abbreviation(String hl7Abbreviation) {
        this.hl7Abbreviation = hl7Abbreviation;
    }

    public String getDescription() {
        return description;
    }

    public ConceptDataType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConceptDataType)) {
            return false;
        }
        return id != null && id.equals(((ConceptDataType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConceptDataType{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", name='" + getName() + "'" +
            ", hl7Abbreviation='" + getHl7Abbreviation() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
