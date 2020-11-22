package org.openmrs.concepts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.openmrs.concepts.web.rest.TestUtil;

public class ConceptNumericTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConceptNumeric.class);
        ConceptNumeric conceptNumeric1 = new ConceptNumeric();
        conceptNumeric1.setId(1L);
        ConceptNumeric conceptNumeric2 = new ConceptNumeric();
        conceptNumeric2.setId(conceptNumeric1.getId());
        assertThat(conceptNumeric1).isEqualTo(conceptNumeric2);
        conceptNumeric2.setId(2L);
        assertThat(conceptNumeric1).isNotEqualTo(conceptNumeric2);
        conceptNumeric1.setId(null);
        assertThat(conceptNumeric1).isNotEqualTo(conceptNumeric2);
    }
}
