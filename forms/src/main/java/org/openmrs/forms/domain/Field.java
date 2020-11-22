package org.openmrs.forms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * A Field.
 */
@Entity
@Table(name = "field")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Field implements Serializable {

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

    @Column(name = "description")
    private String description;

    @Column(name = "concept_uuid")
    private UUID conceptUuid;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "attributes_name")
    private String attributesName;

    @Column(name = "default_value")
    private String defaultValue;

    @Column(name = "select_multiple")
    private Boolean selectMultiple;

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

    public Field uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public Field name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Field description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getConceptUuid() {
        return conceptUuid;
    }

    public Field conceptUuid(UUID conceptUuid) {
        this.conceptUuid = conceptUuid;
        return this;
    }

    public void setConceptUuid(UUID conceptUuid) {
        this.conceptUuid = conceptUuid;
    }

    public String getTableName() {
        return tableName;
    }

    public Field tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getAttributesName() {
        return attributesName;
    }

    public Field attributesName(String attributesName) {
        this.attributesName = attributesName;
        return this;
    }

    public void setAttributesName(String attributesName) {
        this.attributesName = attributesName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public Field defaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Boolean isSelectMultiple() {
        return selectMultiple;
    }

    public Field selectMultiple(Boolean selectMultiple) {
        this.selectMultiple = selectMultiple;
        return this;
    }

    public void setSelectMultiple(Boolean selectMultiple) {
        this.selectMultiple = selectMultiple;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Field)) {
            return false;
        }
        return id != null && id.equals(((Field) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Field{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", conceptUuid='" + getConceptUuid() + "'" +
            ", tableName='" + getTableName() + "'" +
            ", attributesName='" + getAttributesName() + "'" +
            ", defaultValue='" + getDefaultValue() + "'" +
            ", selectMultiple='" + isSelectMultiple() + "'" +
            "}";
    }
}
