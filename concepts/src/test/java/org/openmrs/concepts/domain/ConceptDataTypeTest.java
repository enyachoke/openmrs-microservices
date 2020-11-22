package org.openmrs.concepts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.openmrs.concepts.web.rest.TestUtil;

public class ConceptDataTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConceptDataType.class);
        ConceptDataType conceptDataType1 = new ConceptDataType();
        conceptDataType1.setId(1L);
        ConceptDataType conceptDataType2 = new ConceptDataType();
        conceptDataType2.setId(conceptDataType1.getId());
        assertThat(conceptDataType1).isEqualTo(conceptDataType2);
        conceptDataType2.setId(2L);
        assertThat(conceptDataType1).isNotEqualTo(conceptDataType2);
        conceptDataType1.setId(null);
        assertThat(conceptDataType1).isNotEqualTo(conceptDataType2);
    }
}
