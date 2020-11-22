package org.openmrs.concepts.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * A ConceptDescription.
 */
@Entity
@Table(name = "concept_description")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConceptDescription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    @Column(name = "description")
    private String description;

    @Column(name = "locale")
    private String locale;

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

    public ConceptDescription uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getDescription() {
        return description;
    }

    public ConceptDescription description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocale() {
        return locale;
    }

    public ConceptDescription locale(String locale) {
        this.locale = locale;
        return this;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConceptDescription)) {
            return false;
        }
        return id != null && id.equals(((ConceptDescription) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConceptDescription{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", description='" + getDescription() + "'" +
            ", locale='" + getLocale() + "'" +
            "}";
    }
}
