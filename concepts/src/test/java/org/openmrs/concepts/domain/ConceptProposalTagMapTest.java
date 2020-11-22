package org.openmrs.concepts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.openmrs.concepts.web.rest.TestUtil;

public class ConceptProposalTagMapTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConceptProposalTagMap.class);
        ConceptProposalTagMap conceptProposalTagMap1 = new ConceptProposalTagMap();
        conceptProposalTagMap1.setId(1L);
        ConceptProposalTagMap conceptProposalTagMap2 = new ConceptProposalTagMap();
        conceptProposalTagMap2.setId(conceptProposalTagMap1.getId());
        assertThat(conceptProposalTagMap1).isEqualTo(conceptProposalTagMap2);
        conceptProposalTagMap2.setId(2L);
        assertThat(conceptProposalTagMap1).isNotEqualTo(conceptProposalTagMap2);
        conceptProposalTagMap1.setId(null);
        assertThat(conceptProposalTagMap1).isNotEqualTo(conceptProposalTagMap2);
    }
}
