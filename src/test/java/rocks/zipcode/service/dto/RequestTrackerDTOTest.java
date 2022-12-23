package rocks.zipcode.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rocks.zipcode.web.rest.TestUtil;

class RequestTrackerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestTrackerDTO.class);
        RequestTrackerDTO requestTrackerDTO1 = new RequestTrackerDTO();
        requestTrackerDTO1.setId(1L);
        RequestTrackerDTO requestTrackerDTO2 = new RequestTrackerDTO();
        assertThat(requestTrackerDTO1).isNotEqualTo(requestTrackerDTO2);
        requestTrackerDTO2.setId(requestTrackerDTO1.getId());
        assertThat(requestTrackerDTO1).isEqualTo(requestTrackerDTO2);
        requestTrackerDTO2.setId(2L);
        assertThat(requestTrackerDTO1).isNotEqualTo(requestTrackerDTO2);
        requestTrackerDTO1.setId(null);
        assertThat(requestTrackerDTO1).isNotEqualTo(requestTrackerDTO2);
    }
}
