package org.openmrs.concepts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.openmrs.concepts.web.rest.TestUtil;

public class ConceptComplexTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConceptComplex.class);
        ConceptComplex conceptComplex1 = new ConceptComplex();
        conceptComplex1.setId(1L);
        ConceptComplex conceptComplex2 = new ConceptComplex();
        conceptComplex2.setId(conceptComplex1.getId());
        assertThat(conceptComplex1).isEqualTo(conceptComplex2);
        conceptComplex2.setId(2L);
        assertThat(conceptComplex1).isNotEqualTo(conceptComplex2);
        conceptComplex1.setId(null);
        assertThat(conceptComplex1).isNotEqualTo(conceptComplex2);
    }
}
