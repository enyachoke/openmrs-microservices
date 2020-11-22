package org.openmrs.concepts.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * A ConceptStopWord.
 */
@Entity
@Table(name = "concept_stop_word")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConceptStopWord implements Serializable {

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

    public ConceptStopWord uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getWord() {
        return word;
    }

    public ConceptStopWord word(String word) {
        this.word = word;
        return this;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getLocale() {
        return locale;
    }

    public ConceptStopWord locale(String locale) {
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
        if (!(o instanceof ConceptStopWord)) {
            return false;
        }
        return id != null && id.equals(((ConceptStopWord) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConceptStopWord{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", word='" + getWord() + "'" +
            ", locale='" + getLocale() + "'" +
            "}";
    }
}
