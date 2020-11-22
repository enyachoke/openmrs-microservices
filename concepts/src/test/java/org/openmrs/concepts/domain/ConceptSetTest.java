package org.openmrs.concepts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.openmrs.concepts.web.rest.TestUtil;

public class ConceptSetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConceptSet.class);
        ConceptSet conceptSet1 = new ConceptSet();
        conceptSet1.setId(1L);
        ConceptSet conceptSet2 = new ConceptSet();
        conceptSet2.setId(conceptSet1.getId());
        assertThat(conceptSet1).isEqualTo(conceptSet2);
        conceptSet2.setId(2L);
        assertThat(conceptSet1).isNotEqualTo(conceptSet2);
        conceptSet1.setId(null);
        assertThat(conceptSet1).isNotEqualTo(conceptSet2);
    }
}
