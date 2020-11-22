package org.openmrs.concepts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.openmrs.concepts.web.rest.TestUtil;

public class ConceptNameTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConceptName.class);
        ConceptName conceptName1 = new ConceptName();
        conceptName1.setId(1L);
        ConceptName conceptName2 = new ConceptName();
        conceptName2.setId(conceptName1.getId());
        assertThat(conceptName1).isEqualTo(conceptName2);
        conceptName2.setId(2L);
        assertThat(conceptName1).isNotEqualTo(conceptName2);
        conceptName1.setId(null);
        assertThat(conceptName1).isNotEqualTo(conceptName2);
    }
}
