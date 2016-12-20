package com.app.docker.web.rest;

import com.app.docker.DockerTestApp;

import com.app.docker.domain.OS;
import com.app.docker.repository.OSRepository;
import com.app.docker.service.OSService;
import com.app.docker.service.dto.OSDTO;
import com.app.docker.service.mapper.OSMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OSResource REST controller.
 *
 * @see OSResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DockerTestApp.class)
public class OSResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private OSRepository oSRepository;

    @Inject
    private OSMapper oSMapper;

    @Inject
    private OSService oSService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restOSMockMvc;

    private OS oS;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OSResource oSResource = new OSResource();
        ReflectionTestUtils.setField(oSResource, "oSService", oSService);
        this.restOSMockMvc = MockMvcBuilders.standaloneSetup(oSResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OS createEntity(EntityManager em) {
        OS oS = new OS()
                .name(DEFAULT_NAME)
                .version(DEFAULT_VERSION)
                .description(DEFAULT_DESCRIPTION);
        return oS;
    }

    @Before
    public void initTest() {
        oS = createEntity(em);
    }

    @Test
    @Transactional
    public void createOS() throws Exception {
        int databaseSizeBeforeCreate = oSRepository.findAll().size();

        // Create the OS
        OSDTO oSDTO = oSMapper.oSToOSDTO(oS);

        restOSMockMvc.perform(post("/api/o-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oSDTO)))
            .andExpect(status().isCreated());

        // Validate the OS in the database
        List<OS> oSList = oSRepository.findAll();
        assertThat(oSList).hasSize(databaseSizeBeforeCreate + 1);
        OS testOS = oSList.get(oSList.size() - 1);
        assertThat(testOS.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOS.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testOS.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createOSWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = oSRepository.findAll().size();

        // Create the OS with an existing ID
        OS existingOS = new OS();
        existingOS.setId(1L);
        OSDTO existingOSDTO = oSMapper.oSToOSDTO(existingOS);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOSMockMvc.perform(post("/api/o-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingOSDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<OS> oSList = oSRepository.findAll();
        assertThat(oSList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = oSRepository.findAll().size();
        // set the field null
        oS.setName(null);

        // Create the OS, which fails.
        OSDTO oSDTO = oSMapper.oSToOSDTO(oS);

        restOSMockMvc.perform(post("/api/o-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oSDTO)))
            .andExpect(status().isBadRequest());

        List<OS> oSList = oSRepository.findAll();
        assertThat(oSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = oSRepository.findAll().size();
        // set the field null
        oS.setVersion(null);

        // Create the OS, which fails.
        OSDTO oSDTO = oSMapper.oSToOSDTO(oS);

        restOSMockMvc.perform(post("/api/o-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oSDTO)))
            .andExpect(status().isBadRequest());

        List<OS> oSList = oSRepository.findAll();
        assertThat(oSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOS() throws Exception {
        // Initialize the database
        oSRepository.saveAndFlush(oS);

        // Get all the oSList
        restOSMockMvc.perform(get("/api/o-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oS.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getOS() throws Exception {
        // Initialize the database
        oSRepository.saveAndFlush(oS);

        // Get the oS
        restOSMockMvc.perform(get("/api/o-s/{id}", oS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(oS.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOS() throws Exception {
        // Get the oS
        restOSMockMvc.perform(get("/api/o-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOS() throws Exception {
        // Initialize the database
        oSRepository.saveAndFlush(oS);
        int databaseSizeBeforeUpdate = oSRepository.findAll().size();

        // Update the oS
        OS updatedOS = oSRepository.findOne(oS.getId());
        updatedOS
                .name(UPDATED_NAME)
                .version(UPDATED_VERSION)
                .description(UPDATED_DESCRIPTION);
        OSDTO oSDTO = oSMapper.oSToOSDTO(updatedOS);

        restOSMockMvc.perform(put("/api/o-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oSDTO)))
            .andExpect(status().isOk());

        // Validate the OS in the database
        List<OS> oSList = oSRepository.findAll();
        assertThat(oSList).hasSize(databaseSizeBeforeUpdate);
        OS testOS = oSList.get(oSList.size() - 1);
        assertThat(testOS.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOS.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testOS.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingOS() throws Exception {
        int databaseSizeBeforeUpdate = oSRepository.findAll().size();

        // Create the OS
        OSDTO oSDTO = oSMapper.oSToOSDTO(oS);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOSMockMvc.perform(put("/api/o-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oSDTO)))
            .andExpect(status().isCreated());

        // Validate the OS in the database
        List<OS> oSList = oSRepository.findAll();
        assertThat(oSList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOS() throws Exception {
        // Initialize the database
        oSRepository.saveAndFlush(oS);
        int databaseSizeBeforeDelete = oSRepository.findAll().size();

        // Get the oS
        restOSMockMvc.perform(delete("/api/o-s/{id}", oS.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OS> oSList = oSRepository.findAll();
        assertThat(oSList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
