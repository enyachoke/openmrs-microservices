package org.openmrs.concepts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.openmrs.concepts.web.rest.TestUtil;

public class ConceptAnswerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConceptAnswer.class);
        ConceptAnswer conceptAnswer1 = new ConceptAnswer();
        conceptAnswer1.setId(1L);
        ConceptAnswer conceptAnswer2 = new ConceptAnswer();
        conceptAnswer2.setId(conceptAnswer1.getId());
        assertThat(conceptAnswer1).isEqualTo(conceptAnswer2);
        conceptAnswer2.setId(2L);
        assertThat(conceptAnswer1).isNotEqualTo(conceptAnswer2);
        conceptAnswer1.setId(null);
        assertThat(conceptAnswer1).isNotEqualTo(conceptAnswer2);
    }
}
