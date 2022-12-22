package rocks.zipcode.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import rocks.zipcode.IntegrationTest;
import rocks.zipcode.domain.Bus;
import rocks.zipcode.repository.BusRepository;

/**
 * Integration tests for the {@link BusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BusResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/buses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBusMockMvc;

    private Bus bus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bus createEntity(EntityManager em) {
        Bus bus = new Bus().name(DEFAULT_NAME);
        return bus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bus createUpdatedEntity(EntityManager em) {
        Bus bus = new Bus().name(UPDATED_NAME);
        return bus;
    }

    @BeforeEach
    public void initTest() {
        bus = createEntity(em);
    }

    @Test
    @Transactional
    void createBus() throws Exception {
        int databaseSizeBeforeCreate = busRepository.findAll().size();
        // Create the Bus
        restBusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bus)))
            .andExpect(status().isCreated());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeCreate + 1);
        Bus testBus = busList.get(busList.size() - 1);
        assertThat(testBus.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createBusWithExistingId() throws Exception {
        // Create the Bus with an existing ID
        bus.setId(1L);

        int databaseSizeBeforeCreate = busRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bus)))
            .andExpect(status().isBadRequest());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBuses() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList
        restBusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getBus() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get the bus
        restBusMockMvc
            .perform(get(ENTITY_API_URL_ID, bus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingBus() throws Exception {
        // Get the bus
        restBusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBus() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        int databaseSizeBeforeUpdate = busRepository.findAll().size();

        // Update the bus
        Bus updatedBus = busRepository.findById(bus.getId()).get();
        // Disconnect from session so that the updates on updatedBus are not directly saved in db
        em.detach(updatedBus);
        updatedBus.name(UPDATED_NAME);

        restBusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBus))
            )
            .andExpect(status().isOk());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeUpdate);
        Bus testBus = busList.get(busList.size() - 1);
        assertThat(testBus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingBus() throws Exception {
        int databaseSizeBeforeUpdate = busRepository.findAll().size();
        bus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bus.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bus))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBus() throws Exception {
        int databaseSizeBeforeUpdate = busRepository.findAll().size();
        bus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bus))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBus() throws Exception {
        int databaseSizeBeforeUpdate = busRepository.findAll().size();
        bus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bus)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBusWithPatch() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        int databaseSizeBeforeUpdate = busRepository.findAll().size();

        // Update the bus using partial update
        Bus partialUpdatedBus = new Bus();
        partialUpdatedBus.setId(bus.getId());

        restBusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBus))
            )
            .andExpect(status().isOk());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeUpdate);
        Bus testBus = busList.get(busList.size() - 1);
        assertThat(testBus.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateBusWithPatch() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        int databaseSizeBeforeUpdate = busRepository.findAll().size();

        // Update the bus using partial update
        Bus partialUpdatedBus = new Bus();
        partialUpdatedBus.setId(bus.getId());

        partialUpdatedBus.name(UPDATED_NAME);

        restBusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBus))
            )
            .andExpect(status().isOk());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeUpdate);
        Bus testBus = busList.get(busList.size() - 1);
        assertThat(testBus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingBus() throws Exception {
        int databaseSizeBeforeUpdate = busRepository.findAll().size();
        bus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bus))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBus() throws Exception {
        int databaseSizeBeforeUpdate = busRepository.findAll().size();
        bus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bus))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBus() throws Exception {
        int databaseSizeBeforeUpdate = busRepository.findAll().size();
        bus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bus)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBus() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        int databaseSizeBeforeDelete = busRepository.findAll().size();

        // Delete the bus
        restBusMockMvc.perform(delete(ENTITY_API_URL_ID, bus.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
