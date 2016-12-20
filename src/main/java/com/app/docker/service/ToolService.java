package com.app.docker.service;

import com.app.docker.service.dto.ToolDTO;
import java.util.List;

/**
 * Service Interface for managing Tool.
 */
public interface ToolService {

    /**
     * Save a tool.
     *
     * @param toolDTO the entity to save
     * @return the persisted entity
     */
    ToolDTO save(ToolDTO toolDTO);

    /**
     *  Get all the tools.
     *  
     *  @return the list of entities
     */
    List<ToolDTO> findAll();

    /**
     *  Get the "id" tool.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ToolDTO findOne(Long id);

    /**
     *  Delete the "id" tool.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
