package org.openmrs.concepts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.openmrs.concepts.web.rest.TestUtil;

public class ConceptProposalReferenceMapTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConceptProposalReferenceMap.class);
        ConceptProposalReferenceMap conceptProposalReferenceMap1 = new ConceptProposalReferenceMap();
        conceptProposalReferenceMap1.setId(1L);
        ConceptProposalReferenceMap conceptProposalReferenceMap2 = new ConceptProposalReferenceMap();
        conceptProposalReferenceMap2.setId(conceptProposalReferenceMap1.getId());
        assertThat(conceptProposalReferenceMap1).isEqualTo(conceptProposalReferenceMap2);
        conceptProposalReferenceMap2.setId(2L);
        assertThat(conceptProposalReferenceMap1).isNotEqualTo(conceptProposalReferenceMap2);
        conceptProposalReferenceMap1.setId(null);
        assertThat(conceptProposalReferenceMap1).isNotEqualTo(conceptProposalReferenceMap2);
    }
}
