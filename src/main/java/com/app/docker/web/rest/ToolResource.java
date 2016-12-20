package com.app.docker.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.app.docker.service.ToolService;
import com.app.docker.web.rest.util.HeaderUtil;
import com.app.docker.service.dto.ToolDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Tool.
 */
@RestController
@RequestMapping("/api")
public class ToolResource {

    private final Logger log = LoggerFactory.getLogger(ToolResource.class);
        
    @Inject
    private ToolService toolService;

    /**
     * POST  /tools : Create a new tool.
     *
     * @param toolDTO the toolDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new toolDTO, or with status 400 (Bad Request) if the tool has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tools")
    @Timed
    public ResponseEntity<ToolDTO> createTool(@Valid @RequestBody ToolDTO toolDTO) throws URISyntaxException {
        log.debug("REST request to save Tool : {}", toolDTO);
        if (toolDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tool", "idexists", "A new tool cannot already have an ID")).body(null);
        }
        ToolDTO result = toolService.save(toolDTO);
        return ResponseEntity.created(new URI("/api/tools/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tool", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tools : Updates an existing tool.
     *
     * @param toolDTO the toolDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated toolDTO,
     * or with status 400 (Bad Request) if the toolDTO is not valid,
     * or with status 500 (Internal Server Error) if the toolDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tools")
    @Timed
    public ResponseEntity<ToolDTO> updateTool(@Valid @RequestBody ToolDTO toolDTO) throws URISyntaxException {
        log.debug("REST request to update Tool : {}", toolDTO);
        if (toolDTO.getId() == null) {
            return createTool(toolDTO);
        }
        ToolDTO result = toolService.save(toolDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tool", toolDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tools : get all the tools.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tools in body
     */
    @GetMapping("/tools")
    @Timed
    public List<ToolDTO> getAllTools() {
        log.debug("REST request to get all Tools");
        return toolService.findAll();
    }

    /**
     * GET  /tools/:id : get the "id" tool.
     *
     * @param id the id of the toolDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the toolDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tools/{id}")
    @Timed
    public ResponseEntity<ToolDTO> getTool(@PathVariable Long id) {
        log.debug("REST request to get Tool : {}", id);
        ToolDTO toolDTO = toolService.findOne(id);
        return Optional.ofNullable(toolDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tools/:id : delete the "id" tool.
     *
     * @param id the id of the toolDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tools/{id}")
    @Timed
    public ResponseEntity<Void> deleteTool(@PathVariable Long id) {
        log.debug("REST request to delete Tool : {}", id);
        toolService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tool", id.toString())).build();
    }

}
