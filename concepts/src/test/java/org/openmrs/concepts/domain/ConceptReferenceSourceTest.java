package org.openmrs.concepts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.openmrs.concepts.web.rest.TestUtil;

public class ConceptReferenceSourceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConceptReferenceSource.class);
        ConceptReferenceSource conceptReferenceSource1 = new ConceptReferenceSource();
        conceptReferenceSource1.setId(1L);
        ConceptReferenceSource conceptReferenceSource2 = new ConceptReferenceSource();
        conceptReferenceSource2.setId(conceptReferenceSource1.getId());
        assertThat(conceptReferenceSource1).isEqualTo(conceptReferenceSource2);
        conceptReferenceSource2.setId(2L);
        assertThat(conceptReferenceSource1).isNotEqualTo(conceptReferenceSource2);
        conceptReferenceSource1.setId(null);
        assertThat(conceptReferenceSource1).isNotEqualTo(conceptReferenceSource2);
    }
}
