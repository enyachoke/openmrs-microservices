package org.openmrs.forms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.openmrs.forms.web.rest.TestUtil;

public class FieldAnswerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldAnswer.class);
        FieldAnswer fieldAnswer1 = new FieldAnswer();
        fieldAnswer1.setId(1L);
        FieldAnswer fieldAnswer2 = new FieldAnswer();
        fieldAnswer2.setId(fieldAnswer1.getId());
        assertThat(fieldAnswer1).isEqualTo(fieldAnswer2);
        fieldAnswer2.setId(2L);
        assertThat(fieldAnswer1).isNotEqualTo(fieldAnswer2);
        fieldAnswer1.setId(null);
        assertThat(fieldAnswer1).isNotEqualTo(fieldAnswer2);
    }
}
