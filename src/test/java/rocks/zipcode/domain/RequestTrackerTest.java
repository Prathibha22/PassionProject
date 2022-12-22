package rocks.zipcode.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rocks.zipcode.web.rest.TestUtil;

class RequestTrackerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestTracker.class);
        RequestTracker requestTracker1 = new RequestTracker();
        requestTracker1.setId(1L);
        RequestTracker requestTracker2 = new RequestTracker();
        requestTracker2.setId(requestTracker1.getId());
        assertThat(requestTracker1).isEqualTo(requestTracker2);
        requestTracker2.setId(2L);
        assertThat(requestTracker1).isNotEqualTo(requestTracker2);
        requestTracker1.setId(null);
        assertThat(requestTracker1).isNotEqualTo(requestTracker2);
    }
}
