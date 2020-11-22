package org.openmrs.concepts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.openmrs.concepts.web.rest.TestUtil;

public class ConceptReferenceTermTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConceptReferenceTerm.class);
        ConceptReferenceTerm conceptReferenceTerm1 = new ConceptReferenceTerm();
        conceptReferenceTerm1.setId(1L);
        ConceptReferenceTerm conceptReferenceTerm2 = new ConceptReferenceTerm();
        conceptReferenceTerm2.setId(conceptReferenceTerm1.getId());
        assertThat(conceptReferenceTerm1).isEqualTo(conceptReferenceTerm2);
        conceptReferenceTerm2.setId(2L);
        assertThat(conceptReferenceTerm1).isNotEqualTo(conceptReferenceTerm2);
        conceptReferenceTerm1.setId(null);
        assertThat(conceptReferenceTerm1).isNotEqualTo(conceptReferenceTerm2);
    }
}
