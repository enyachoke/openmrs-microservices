package org.openmrs.concepts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.openmrs.concepts.web.rest.TestUtil;

public class ConceptWordTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConceptWord.class);
        ConceptWord conceptWord1 = new ConceptWord();
        conceptWord1.setId(1L);
        ConceptWord conceptWord2 = new ConceptWord();
        conceptWord2.setId(conceptWord1.getId());
        assertThat(conceptWord1).isEqualTo(conceptWord2);
        conceptWord2.setId(2L);
        assertThat(conceptWord1).isNotEqualTo(conceptWord2);
        conceptWord1.setId(null);
        assertThat(conceptWord1).isNotEqualTo(conceptWord2);
    }
}
