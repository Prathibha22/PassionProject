package rocks.zipcode.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rocks.zipcode.web.rest.TestUtil;

class BusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bus.class);
        Bus bus1 = new Bus();
        bus1.setId(1L);
        Bus bus2 = new Bus();
        bus2.setId(bus1.getId());
        assertThat(bus1).isEqualTo(bus2);
        bus2.setId(2L);
        assertThat(bus1).isNotEqualTo(bus2);
        bus1.setId(null);
        assertThat(bus1).isNotEqualTo(bus2);
    }
}
