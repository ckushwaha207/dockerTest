package com.app.docker.service.impl;

import com.app.docker.service.OSService;
import com.app.docker.domain.OS;
import com.app.docker.repository.OSRepository;
import com.app.docker.service.dto.OSDTO;
import com.app.docker.service.mapper.OSMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing OS.
 */
@Service
@Transactional
public class OSServiceImpl implements OSService{

    private final Logger log = LoggerFactory.getLogger(OSServiceImpl.class);
    
    @Inject
    private OSRepository oSRepository;

    @Inject
    private OSMapper oSMapper;

    /**
     * Save a oS.
     *
     * @param oSDTO the entity to save
     * @return the persisted entity
     */
    public OSDTO save(OSDTO oSDTO) {
        log.debug("Request to save OS : {}", oSDTO);
        OS oS = oSMapper.oSDTOToOS(oSDTO);
        oS = oSRepository.save(oS);
        OSDTO result = oSMapper.oSToOSDTO(oS);
        return result;
    }

    /**
     *  Get all the oS.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<OSDTO> findAll() {
        log.debug("Request to get all OS");
        List<OSDTO> result = oSRepository.findAllWithEagerRelationships().stream()
            .map(oSMapper::oSToOSDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one oS by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public OSDTO findOne(Long id) {
        log.debug("Request to get OS : {}", id);
        OS oS = oSRepository.findOneWithEagerRelationships(id);
        OSDTO oSDTO = oSMapper.oSToOSDTO(oS);
        return oSDTO;
    }

    /**
     *  Delete the  oS by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OS : {}", id);
        oSRepository.delete(id);
    }
}
