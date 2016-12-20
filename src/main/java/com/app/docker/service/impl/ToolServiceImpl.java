package com.app.docker.service.impl;

import com.app.docker.service.ToolService;
import com.app.docker.domain.Tool;
import com.app.docker.repository.ToolRepository;
import com.app.docker.service.dto.ToolDTO;
import com.app.docker.service.mapper.ToolMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Tool.
 */
@Service
@Transactional
public class ToolServiceImpl implements ToolService{

    private final Logger log = LoggerFactory.getLogger(ToolServiceImpl.class);
    
    @Inject
    private ToolRepository toolRepository;

    @Inject
    private ToolMapper toolMapper;

    /**
     * Save a tool.
     *
     * @param toolDTO the entity to save
     * @return the persisted entity
     */
    public ToolDTO save(ToolDTO toolDTO) {
        log.debug("Request to save Tool : {}", toolDTO);
        Tool tool = toolMapper.toolDTOToTool(toolDTO);
        tool = toolRepository.save(tool);
        ToolDTO result = toolMapper.toolToToolDTO(tool);
        return result;
    }

    /**
     *  Get all the tools.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ToolDTO> findAll() {
        log.debug("Request to get all Tools");
        List<ToolDTO> result = toolRepository.findAll().stream()
            .map(toolMapper::toolToToolDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one tool by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ToolDTO findOne(Long id) {
        log.debug("Request to get Tool : {}", id);
        Tool tool = toolRepository.findOne(id);
        ToolDTO toolDTO = toolMapper.toolToToolDTO(tool);
        return toolDTO;
    }

    /**
     *  Delete the  tool by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tool : {}", id);
        toolRepository.delete(id);
    }
}
