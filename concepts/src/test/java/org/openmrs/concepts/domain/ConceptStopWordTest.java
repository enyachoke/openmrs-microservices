package org.openmrs.concepts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.openmrs.concepts.web.rest.TestUtil;

public class ConceptStopWordTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConceptStopWord.class);
        ConceptStopWord conceptStopWord1 = new ConceptStopWord();
        conceptStopWord1.setId(1L);
        ConceptStopWord conceptStopWord2 = new ConceptStopWord();
        conceptStopWord2.setId(conceptStopWord1.getId());
        assertThat(conceptStopWord1).isEqualTo(conceptStopWord2);
        conceptStopWord2.setId(2L);
        assertThat(conceptStopWord1).isNotEqualTo(conceptStopWord2);
        conceptStopWord1.setId(null);
        assertThat(conceptStopWord1).isNotEqualTo(conceptStopWord2);
    }
}
