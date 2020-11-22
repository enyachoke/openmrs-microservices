package org.openmrs.concepts.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * A Concept.
 */
@Entity
@Table(name = "concept")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Concept implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "description")
    private String description;

    @Column(name = "form_text")
    private String formText;

    @Column(name = "version")
    private String version;

    @Column(name = "is_set")
    private Boolean isSet;

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

    public Concept uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getShortName() {
        return shortName;
    }

    public Concept shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public Concept description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormText() {
        return formText;
    }

    public Concept formText(String formText) {
        this.formText = formText;
        return this;
    }

    public void setFormText(String formText) {
        this.formText = formText;
    }

    public String getVersion() {
        return version;
    }

    public Concept version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean isIsSet() {
        return isSet;
    }

    public Concept isSet(Boolean isSet) {
        this.isSet = isSet;
        return this;
    }

    public void setIsSet(Boolean isSet) {
        this.isSet = isSet;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Concept)) {
            return false;
        }
        return id != null && id.equals(((Concept) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Concept{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", description='" + getDescription() + "'" +
            ", formText='" + getFormText() + "'" +
            ", version='" + getVersion() + "'" +
            ", isSet='" + isIsSet() + "'" +
            "}";
    }
}
