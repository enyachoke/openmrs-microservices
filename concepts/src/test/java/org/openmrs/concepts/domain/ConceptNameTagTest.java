package org.openmrs.concepts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.openmrs.concepts.web.rest.TestUtil;

public class ConceptNameTagTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConceptNameTag.class);
        ConceptNameTag conceptNameTag1 = new ConceptNameTag();
        conceptNameTag1.setId(1L);
        ConceptNameTag conceptNameTag2 = new ConceptNameTag();
        conceptNameTag2.setId(conceptNameTag1.getId());
        assertThat(conceptNameTag1).isEqualTo(conceptNameTag2);
        conceptNameTag2.setId(2L);
        assertThat(conceptNameTag1).isNotEqualTo(conceptNameTag2);
        conceptNameTag1.setId(null);
        assertThat(conceptNameTag1).isNotEqualTo(conceptNameTag2);
    }
}
