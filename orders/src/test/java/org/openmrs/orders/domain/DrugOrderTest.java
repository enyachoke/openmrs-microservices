package org.openmrs.orders.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.openmrs.orders.web.rest.TestUtil;

public class DrugOrderTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DrugOrder.class);
        DrugOrder drugOrder1 = new DrugOrder();
        drugOrder1.setId(1L);
        DrugOrder drugOrder2 = new DrugOrder();
        drugOrder2.setId(drugOrder1.getId());
        assertThat(drugOrder1).isEqualTo(drugOrder2);
        drugOrder2.setId(2L);
        assertThat(drugOrder1).isNotEqualTo(drugOrder2);
        drugOrder1.setId(null);
        assertThat(drugOrder1).isNotEqualTo(drugOrder2);
    }
}
