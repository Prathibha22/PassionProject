package rocks.zipcode.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import rocks.zipcode.IntegrationTest;
import rocks.zipcode.domain.RequestTracker;
import rocks.zipcode.domain.enumeration.RequestType;
import rocks.zipcode.repository.RequestTrackerRepository;
import rocks.zipcode.service.RequestTrackerService;
import rocks.zipcode.service.dto.RequestTrackerDTO;
import rocks.zipcode.service.mapper.RequestTrackerMapper;

/**
 * Integration tests for the {@link RequestTrackerResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RequestTrackerResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final RequestType DEFAULT_REQUEST_TYPE = RequestType.ABSENT;
    private static final RequestType UPDATED_REQUEST_TYPE = RequestType.EARLYDISMISSAL;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/request-trackers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RequestTrackerRepository requestTrackerRepository;

    @Mock
    private RequestTrackerRepository requestTrackerRepositoryMock;

    @Autowired
    private RequestTrackerMapper requestTrackerMapper;

    @Mock
    private RequestTrackerService requestTrackerServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequestTrackerMockMvc;

    private RequestTracker requestTracker;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestTracker createEntity(EntityManager em) {
        RequestTracker requestTracker = new RequestTracker()
            .date(DEFAULT_DATE)
            .requestType(DEFAULT_REQUEST_TYPE)
            .description(DEFAULT_DESCRIPTION);
        return requestTracker;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestTracker createUpdatedEntity(EntityManager em) {
        RequestTracker requestTracker = new RequestTracker()
            .date(UPDATED_DATE)
            .requestType(UPDATED_REQUEST_TYPE)
            .description(UPDATED_DESCRIPTION);
        return requestTracker;
    }

    @BeforeEach
    public void initTest() {
        requestTracker = createEntity(em);
    }

    @Test
    @Transactional
    void createRequestTracker() throws Exception {
        int databaseSizeBeforeCreate = requestTrackerRepository.findAll().size();
        // Create the RequestTracker
        RequestTrackerDTO requestTrackerDTO = requestTrackerMapper.toDto(requestTracker);
        restRequestTrackerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestTrackerDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RequestTracker in the database
        List<RequestTracker> requestTrackerList = requestTrackerRepository.findAll();
        assertThat(requestTrackerList).hasSize(databaseSizeBeforeCreate + 1);
        RequestTracker testRequestTracker = requestTrackerList.get(requestTrackerList.size() - 1);
        assertThat(testRequestTracker.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testRequestTracker.getRequestType()).isEqualTo(DEFAULT_REQUEST_TYPE);
        assertThat(testRequestTracker.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createRequestTrackerWithExistingId() throws Exception {
        // Create the RequestTracker with an existing ID
        requestTracker.setId(1L);
        RequestTrackerDTO requestTrackerDTO = requestTrackerMapper.toDto(requestTracker);

        int databaseSizeBeforeCreate = requestTrackerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestTrackerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestTrackerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestTracker in the database
        List<RequestTracker> requestTrackerList = requestTrackerRepository.findAll();
        assertThat(requestTrackerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRequestTrackers() throws Exception {
        // Initialize the database
        requestTrackerRepository.saveAndFlush(requestTracker);

        // Get all the requestTrackerList
        restRequestTrackerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestTracker.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].requestType").value(hasItem(DEFAULT_REQUEST_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRequestTrackersWithEagerRelationshipsIsEnabled() throws Exception {
        when(requestTrackerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRequestTrackerMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(requestTrackerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRequestTrackersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(requestTrackerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRequestTrackerMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(requestTrackerRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getRequestTracker() throws Exception {
        // Initialize the database
        requestTrackerRepository.saveAndFlush(requestTracker);

        // Get the requestTracker
        restRequestTrackerMockMvc
            .perform(get(ENTITY_API_URL_ID, requestTracker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requestTracker.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.requestType").value(DEFAULT_REQUEST_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingRequestTracker() throws Exception {
        // Get the requestTracker
        restRequestTrackerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRequestTracker() throws Exception {
        // Initialize the database
        requestTrackerRepository.saveAndFlush(requestTracker);

        int databaseSizeBeforeUpdate = requestTrackerRepository.findAll().size();

        // Update the requestTracker
        RequestTracker updatedRequestTracker = requestTrackerRepository.findById(requestTracker.getId()).get();
        // Disconnect from session so that the updates on updatedRequestTracker are not directly saved in db
        em.detach(updatedRequestTracker);
        updatedRequestTracker.date(UPDATED_DATE).requestType(UPDATED_REQUEST_TYPE).description(UPDATED_DESCRIPTION);
        RequestTrackerDTO requestTrackerDTO = requestTrackerMapper.toDto(updatedRequestTracker);

        restRequestTrackerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestTrackerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestTrackerDTO))
            )
            .andExpect(status().isOk());

        // Validate the RequestTracker in the database
        List<RequestTracker> requestTrackerList = requestTrackerRepository.findAll();
        assertThat(requestTrackerList).hasSize(databaseSizeBeforeUpdate);
        RequestTracker testRequestTracker = requestTrackerList.get(requestTrackerList.size() - 1);
        assertThat(testRequestTracker.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testRequestTracker.getRequestType()).isEqualTo(UPDATED_REQUEST_TYPE);
        assertThat(testRequestTracker.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingRequestTracker() throws Exception {
        int databaseSizeBeforeUpdate = requestTrackerRepository.findAll().size();
        requestTracker.setId(count.incrementAndGet());

        // Create the RequestTracker
        RequestTrackerDTO requestTrackerDTO = requestTrackerMapper.toDto(requestTracker);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestTrackerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestTrackerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestTrackerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestTracker in the database
        List<RequestTracker> requestTrackerList = requestTrackerRepository.findAll();
        assertThat(requestTrackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequestTracker() throws Exception {
        int databaseSizeBeforeUpdate = requestTrackerRepository.findAll().size();
        requestTracker.setId(count.incrementAndGet());

        // Create the RequestTracker
        RequestTrackerDTO requestTrackerDTO = requestTrackerMapper.toDto(requestTracker);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestTrackerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestTrackerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestTracker in the database
        List<RequestTracker> requestTrackerList = requestTrackerRepository.findAll();
        assertThat(requestTrackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequestTracker() throws Exception {
        int databaseSizeBeforeUpdate = requestTrackerRepository.findAll().size();
        requestTracker.setId(count.incrementAndGet());

        // Create the RequestTracker
        RequestTrackerDTO requestTrackerDTO = requestTrackerMapper.toDto(requestTracker);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestTrackerMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestTrackerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestTracker in the database
        List<RequestTracker> requestTrackerList = requestTrackerRepository.findAll();
        assertThat(requestTrackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequestTrackerWithPatch() throws Exception {
        // Initialize the database
        requestTrackerRepository.saveAndFlush(requestTracker);

        int databaseSizeBeforeUpdate = requestTrackerRepository.findAll().size();

        // Update the requestTracker using partial update
        RequestTracker partialUpdatedRequestTracker = new RequestTracker();
        partialUpdatedRequestTracker.setId(requestTracker.getId());

        partialUpdatedRequestTracker.date(UPDATED_DATE).requestType(UPDATED_REQUEST_TYPE);

        restRequestTrackerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestTracker.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequestTracker))
            )
            .andExpect(status().isOk());

        // Validate the RequestTracker in the database
        List<RequestTracker> requestTrackerList = requestTrackerRepository.findAll();
        assertThat(requestTrackerList).hasSize(databaseSizeBeforeUpdate);
        RequestTracker testRequestTracker = requestTrackerList.get(requestTrackerList.size() - 1);
        assertThat(testRequestTracker.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testRequestTracker.getRequestType()).isEqualTo(UPDATED_REQUEST_TYPE);
        assertThat(testRequestTracker.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateRequestTrackerWithPatch() throws Exception {
        // Initialize the database
        requestTrackerRepository.saveAndFlush(requestTracker);

        int databaseSizeBeforeUpdate = requestTrackerRepository.findAll().size();

        // Update the requestTracker using partial update
        RequestTracker partialUpdatedRequestTracker = new RequestTracker();
        partialUpdatedRequestTracker.setId(requestTracker.getId());

        partialUpdatedRequestTracker.date(UPDATED_DATE).requestType(UPDATED_REQUEST_TYPE).description(UPDATED_DESCRIPTION);

        restRequestTrackerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestTracker.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequestTracker))
            )
            .andExpect(status().isOk());

        // Validate the RequestTracker in the database
        List<RequestTracker> requestTrackerList = requestTrackerRepository.findAll();
        assertThat(requestTrackerList).hasSize(databaseSizeBeforeUpdate);
        RequestTracker testRequestTracker = requestTrackerList.get(requestTrackerList.size() - 1);
        assertThat(testRequestTracker.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testRequestTracker.getRequestType()).isEqualTo(UPDATED_REQUEST_TYPE);
        assertThat(testRequestTracker.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingRequestTracker() throws Exception {
        int databaseSizeBeforeUpdate = requestTrackerRepository.findAll().size();
        requestTracker.setId(count.incrementAndGet());

        // Create the RequestTracker
        RequestTrackerDTO requestTrackerDTO = requestTrackerMapper.toDto(requestTracker);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestTrackerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requestTrackerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestTrackerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestTracker in the database
        List<RequestTracker> requestTrackerList = requestTrackerRepository.findAll();
        assertThat(requestTrackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequestTracker() throws Exception {
        int databaseSizeBeforeUpdate = requestTrackerRepository.findAll().size();
        requestTracker.setId(count.incrementAndGet());

        // Create the RequestTracker
        RequestTrackerDTO requestTrackerDTO = requestTrackerMapper.toDto(requestTracker);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestTrackerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestTrackerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestTracker in the database
        List<RequestTracker> requestTrackerList = requestTrackerRepository.findAll();
        assertThat(requestTrackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequestTracker() throws Exception {
        int databaseSizeBeforeUpdate = requestTrackerRepository.findAll().size();
        requestTracker.setId(count.incrementAndGet());

        // Create the RequestTracker
        RequestTrackerDTO requestTrackerDTO = requestTrackerMapper.toDto(requestTracker);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestTrackerMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestTrackerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestTracker in the database
        List<RequestTracker> requestTrackerList = requestTrackerRepository.findAll();
        assertThat(requestTrackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequestTracker() throws Exception {
        // Initialize the database
        requestTrackerRepository.saveAndFlush(requestTracker);

        int databaseSizeBeforeDelete = requestTrackerRepository.findAll().size();

        // Delete the requestTracker
        restRequestTrackerMockMvc
            .perform(delete(ENTITY_API_URL_ID, requestTracker.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RequestTracker> requestTrackerList = requestTrackerRepository.findAll();
        assertThat(requestTrackerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
