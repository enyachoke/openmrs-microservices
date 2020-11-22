package org.openmrs.forms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * A FormField.
 */
@Entity
@Table(name = "form_field")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FormField implements Serializable {

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

    @Column(name = "field_number")
    private Integer fieldNumber;

    @Column(name = "field_part")
    private String fieldPart;

    @Column(name = "page_number")
    private Integer pageNumber;

    @Column(name = "min_occurs")
    private Integer minOccurs;

    @Column(name = "max_occurs")
    private Integer maxOccurs;

    @Column(name = "is_required")
    private Boolean isRequired;

    @Column(name = "sort_weight")
    private Float sortWeight;

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

    public FormField uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public FormField name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFieldNumber() {
        return fieldNumber;
    }

    public FormField fieldNumber(Integer fieldNumber) {
        this.fieldNumber = fieldNumber;
        return this;
    }

    public void setFieldNumber(Integer fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    public String getFieldPart() {
        return fieldPart;
    }

    public FormField fieldPart(String fieldPart) {
        this.fieldPart = fieldPart;
        return this;
    }

    public void setFieldPart(String fieldPart) {
        this.fieldPart = fieldPart;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public FormField pageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getMinOccurs() {
        return minOccurs;
    }

    public FormField minOccurs(Integer minOccurs) {
        this.minOccurs = minOccurs;
        return this;
    }

    public void setMinOccurs(Integer minOccurs) {
        this.minOccurs = minOccurs;
    }

    public Integer getMaxOccurs() {
        return maxOccurs;
    }

    public FormField maxOccurs(Integer maxOccurs) {
        this.maxOccurs = maxOccurs;
        return this;
    }

    public void setMaxOccurs(Integer maxOccurs) {
        this.maxOccurs = maxOccurs;
    }

    public Boolean isIsRequired() {
        return isRequired;
    }

    public FormField isRequired(Boolean isRequired) {
        this.isRequired = isRequired;
        return this;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public Float getSortWeight() {
        return sortWeight;
    }

    public FormField sortWeight(Float sortWeight) {
        this.sortWeight = sortWeight;
        return this;
    }

    public void setSortWeight(Float sortWeight) {
        this.sortWeight = sortWeight;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormField)) {
            return false;
        }
        return id != null && id.equals(((FormField) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FormField{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", name='" + getName() + "'" +
            ", fieldNumber=" + getFieldNumber() +
            ", fieldPart='" + getFieldPart() + "'" +
            ", pageNumber=" + getPageNumber() +
            ", minOccurs=" + getMinOccurs() +
            ", maxOccurs=" + getMaxOccurs() +
            ", isRequired='" + isIsRequired() + "'" +
            ", sortWeight=" + getSortWeight() +
            "}";
    }
}
