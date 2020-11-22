package org.openmrs.concepts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.openmrs.concepts.web.rest.TestUtil;

public class ConceptProposalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConceptProposal.class);
        ConceptProposal conceptProposal1 = new ConceptProposal();
        conceptProposal1.setId(1L);
        ConceptProposal conceptProposal2 = new ConceptProposal();
        conceptProposal2.setId(conceptProposal1.getId());
        assertThat(conceptProposal1).isEqualTo(conceptProposal2);
        conceptProposal2.setId(2L);
        assertThat(conceptProposal1).isNotEqualTo(conceptProposal2);
        conceptProposal1.setId(null);
        assertThat(conceptProposal1).isNotEqualTo(conceptProposal2);
    }
}
