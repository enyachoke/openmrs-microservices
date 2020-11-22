package org.openmrs.concepts.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * A ConceptComplex.
 */
@Entity
@Table(name = "concept_complex")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConceptComplex implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    @Column(name = "handler")
    private String handler;

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

    public ConceptComplex uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getHandler() {
        return handler;
    }

    public ConceptComplex handler(String handler) {
        this.handler = handler;
        return this;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConceptComplex)) {
            return false;
        }
        return id != null && id.equals(((ConceptComplex) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConceptComplex{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", handler='" + getHandler() + "'" +
            "}";
    }
}
