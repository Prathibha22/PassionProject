package rocks.zipcode.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RequestTrackerMapperTest {

    private RequestTrackerMapper requestTrackerMapper;

    @BeforeEach
    public void setUp() {
        requestTrackerMapper = new RequestTrackerMapperImpl();
    }
}
