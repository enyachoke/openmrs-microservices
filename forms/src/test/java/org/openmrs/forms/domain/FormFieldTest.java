package org.openmrs.forms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.openmrs.forms.web.rest.TestUtil;

public class FormFieldTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormField.class);
        FormField formField1 = new FormField();
        formField1.setId(1L);
        FormField formField2 = new FormField();
        formField2.setId(formField1.getId());
        assertThat(formField1).isEqualTo(formField2);
        formField2.setId(2L);
        assertThat(formField1).isNotEqualTo(formField2);
        formField1.setId(null);
        assertThat(formField1).isNotEqualTo(formField2);
    }
}
