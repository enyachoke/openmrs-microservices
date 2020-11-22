package org.openmrs.concepts.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * A ConceptName.
 */
@Entity
@Table(name = "concept_name")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConceptName implements Serializable {

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

    @Column(name = "locale")
    private String locale;

    @Column(name = "concept_name_type")
    private String conceptNameType;

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

    public ConceptName uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public ConceptName name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocale() {
        return locale;
    }

    public ConceptName locale(String locale) {
        this.locale = locale;
        return this;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getConceptNameType() {
        return conceptNameType;
    }

    public ConceptName conceptNameType(String conceptNameType) {
        this.conceptNameType = conceptNameType;
        return this;
    }

    public void setConceptNameType(String conceptNameType) {
        this.conceptNameType = conceptNameType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConceptName)) {
            return false;
        }
        return id != null && id.equals(((ConceptName) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConceptName{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", name='" + getName() + "'" +
            ", locale='" + getLocale() + "'" +
            ", conceptNameType='" + getConceptNameType() + "'" +
            "}";
    }
}
