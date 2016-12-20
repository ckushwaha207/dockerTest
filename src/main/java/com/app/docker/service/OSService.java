package com.app.docker.service;

import com.app.docker.service.dto.OSDTO;
import java.util.List;

/**
 * Service Interface for managing OS.
 */
public interface OSService {

    /**
     * Save a oS.
     *
     * @param oSDTO the entity to save
     * @return the persisted entity
     */
    OSDTO save(OSDTO oSDTO);

    /**
     *  Get all the oS.
     *  
     *  @return the list of entities
     */
    List<OSDTO> findAll();

    /**
     *  Get the "id" oS.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OSDTO findOne(Long id);

    /**
     *  Delete the "id" oS.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
