package org.openmrs.concepts.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * A ConceptWord.
 */
@Entity
@Table(name = "concept_word")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConceptWord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    @Column(name = "word")
    private String word;

    @Column(name = "locale")
    private String locale;

    @Column(name = "weight")
    private Double weight;

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

    public ConceptWord uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getWord() {
        return word;
    }

    public ConceptWord word(String word) {
        this.word = word;
        return this;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getLocale() {
        return locale;
    }

    public ConceptWord locale(String locale) {
        this.locale = locale;
        return this;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Double getWeight() {
        return weight;
    }

    public ConceptWord weight(Double weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConceptWord)) {
            return false;
        }
        return id != null && id.equals(((ConceptWord) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConceptWord{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", word='" + getWord() + "'" +
            ", locale='" + getLocale() + "'" +
            ", weight=" + getWeight() +
            "}";
    }
}
