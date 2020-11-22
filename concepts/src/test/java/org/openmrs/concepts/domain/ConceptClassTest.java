package org.openmrs.concepts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.openmrs.concepts.web.rest.TestUtil;

public class ConceptClassTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConceptClass.class);
        ConceptClass conceptClass1 = new ConceptClass();
        conceptClass1.setId(1L);
        ConceptClass conceptClass2 = new ConceptClass();
        conceptClass2.setId(conceptClass1.getId());
        assertThat(conceptClass1).isEqualTo(conceptClass2);
        conceptClass2.setId(2L);
        assertThat(conceptClass1).isNotEqualTo(conceptClass2);
        conceptClass1.setId(null);
        assertThat(conceptClass1).isNotEqualTo(conceptClass2);
    }
}
