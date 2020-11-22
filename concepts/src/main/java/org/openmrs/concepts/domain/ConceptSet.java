package org.openmrs.concepts.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * A ConceptSet.
 */
@Entity
@Table(name = "concept_set")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConceptSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    @Column(name = "sort_weight")
    private Double sortWeight;

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

    public ConceptSet uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Double getSortWeight() {
        return sortWeight;
    }

    public ConceptSet sortWeight(Double sortWeight) {
        this.sortWeight = sortWeight;
        return this;
    }

    public void setSortWeight(Double sortWeight) {
        this.sortWeight = sortWeight;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConceptSet)) {
            return false;
        }
        return id != null && id.equals(((ConceptSet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConceptSet{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", sortWeight=" + getSortWeight() +
            "}";
    }
}
