package org.openmrs.concepts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.openmrs.concepts.web.rest.TestUtil;

public class ConceptNameTagMapTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConceptNameTagMap.class);
        ConceptNameTagMap conceptNameTagMap1 = new ConceptNameTagMap();
        conceptNameTagMap1.setId(1L);
        ConceptNameTagMap conceptNameTagMap2 = new ConceptNameTagMap();
        conceptNameTagMap2.setId(conceptNameTagMap1.getId());
        assertThat(conceptNameTagMap1).isEqualTo(conceptNameTagMap2);
        conceptNameTagMap2.setId(2L);
        assertThat(conceptNameTagMap1).isNotEqualTo(conceptNameTagMap2);
        conceptNameTagMap1.setId(null);
        assertThat(conceptNameTagMap1).isNotEqualTo(conceptNameTagMap2);
    }
}
