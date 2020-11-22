package org.openmrs.forms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * A Form.
 */
@Entity
@Table(name = "form")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Form implements Serializable {

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

    @Column(name = "version")
    private String version;

    @Column(name = "build")
    private Integer build;

    @Column(name = "published")
    private Boolean published;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "encounter_type", nullable = false)
    private UUID encounterType;

    @Column(name = "template")
    private String template;

    @Column(name = "xslt")
    private String xslt;

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

    public Form uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public Form name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public Form version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getBuild() {
        return build;
    }

    public Form build(Integer build) {
        this.build = build;
        return this;
    }

    public void setBuild(Integer build) {
        this.build = build;
    }

    public Boolean isPublished() {
        return published;
    }

    public Form published(Boolean published) {
        this.published = published;
        return this;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public String getDescription() {
        return description;
    }

    public Form description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getEncounterType() {
        return encounterType;
    }

    public Form encounterType(UUID encounterType) {
        this.encounterType = encounterType;
        return this;
    }

    public void setEncounterType(UUID encounterType) {
        this.encounterType = encounterType;
    }

    public String getTemplate() {
        return template;
    }

    public Form template(String template) {
        this.template = template;
        return this;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getXslt() {
        return xslt;
    }

    public Form xslt(String xslt) {
        this.xslt = xslt;
        return this;
    }

    public void setXslt(String xslt) {
        this.xslt = xslt;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Form)) {
            return false;
        }
        return id != null && id.equals(((Form) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Form{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", name='" + getName() + "'" +
            ", version='" + getVersion() + "'" +
            ", build=" + getBuild() +
            ", published='" + isPublished() + "'" +
            ", description='" + getDescription() + "'" +
            ", encounterType='" + getEncounterType() + "'" +
            ", template='" + getTemplate() + "'" +
            ", xslt='" + getXslt() + "'" +
            "}";
    }
}
