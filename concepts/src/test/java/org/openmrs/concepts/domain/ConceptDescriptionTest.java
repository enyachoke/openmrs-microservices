package org.openmrs.concepts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.openmrs.concepts.web.rest.TestUtil;

public class ConceptDescriptionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConceptDescription.class);
        ConceptDescription conceptDescription1 = new ConceptDescription();
        conceptDescription1.setId(1L);
        ConceptDescription conceptDescription2 = new ConceptDescription();
        conceptDescription2.setId(conceptDescription1.getId());
        assertThat(conceptDescription1).isEqualTo(conceptDescription2);
        conceptDescription2.setId(2L);
        assertThat(conceptDescription1).isNotEqualTo(conceptDescription2);
        conceptDescription1.setId(null);
        assertThat(conceptDescription1).isNotEqualTo(conceptDescription2);
    }
}
