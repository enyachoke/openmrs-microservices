package org.openmrs.concepts.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * A ConceptProposal.
 */
@Entity
@Table(name = "concept_proposal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConceptProposal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    @Column(name = "encounter")
    private UUID encounter;

    @Column(name = "original_text")
    private String originalText;

    @Column(name = "final_text")
    private String finalText;

    @Column(name = "obs_uuid")
    private UUID obsUuid;

    @Column(name = "obs_concept_uuid")
    private UUID obsConceptUuid;

    @Column(name = "state")
    private String state;

    @Column(name = "comments")
    private String comments;

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

    public ConceptProposal uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getEncounter() {
        return encounter;
    }

    public ConceptProposal encounter(UUID encounter) {
        this.encounter = encounter;
        return this;
    }

    public void setEncounter(UUID encounter) {
        this.encounter = encounter;
    }

    public String getOriginalText() {
        return originalText;
    }

    public ConceptProposal originalText(String originalText) {
        this.originalText = originalText;
        return this;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public String getFinalText() {
        return finalText;
    }

    public ConceptProposal finalText(String finalText) {
        this.finalText = finalText;
        return this;
    }

    public void setFinalText(String finalText) {
        this.finalText = finalText;
    }

    public UUID getObsUuid() {
        return obsUuid;
    }

    public ConceptProposal obsUuid(UUID obsUuid) {
        this.obsUuid = obsUuid;
        return this;
    }

    public void setObsUuid(UUID obsUuid) {
        this.obsUuid = obsUuid;
    }

    public UUID getObsConceptUuid() {
        return obsConceptUuid;
    }

    public ConceptProposal obsConceptUuid(UUID obsConceptUuid) {
        this.obsConceptUuid = obsConceptUuid;
        return this;
    }

    public void setObsConceptUuid(UUID obsConceptUuid) {
        this.obsConceptUuid = obsConceptUuid;
    }

    public String getState() {
        return state;
    }

    public ConceptProposal state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getComments() {
        return comments;
    }

    public ConceptProposal comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLocale() {
        return locale;
    }

    public ConceptProposal locale(String locale) {
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
        if (!(o instanceof ConceptProposal)) {
            return false;
        }
        return id != null && id.equals(((ConceptProposal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConceptProposal{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", encounter='" + getEncounter() + "'" +
            ", originalText='" + getOriginalText() + "'" +
            ", finalText='" + getFinalText() + "'" +
            ", obsUuid='" + getObsUuid() + "'" +
            ", obsConceptUuid='" + getObsConceptUuid() + "'" +
            ", state='" + getState() + "'" +
            ", comments='" + getComments() + "'" +
            ", locale='" + getLocale() + "'" +
            "}";
    }
}
