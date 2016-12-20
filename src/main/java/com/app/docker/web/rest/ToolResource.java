package com.app.docker.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.app.docker.domain.Tool;

import com.app.docker.repository.ToolRepository;
import com.app.docker.web.rest.util.HeaderUtil;

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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Tool.
 */
@RestController
@RequestMapping("/api")
public class ToolResource {

    private final Logger log = LoggerFactory.getLogger(ToolResource.class);
        
    @Inject
    private ToolRepository toolRepository;

    /**
     * POST  /tools : Create a new tool.
     *
     * @param tool the tool to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tool, or with status 400 (Bad Request) if the tool has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tools")
    @Timed
    public ResponseEntity<Tool> createTool(@Valid @RequestBody Tool tool) throws URISyntaxException {
        log.debug("REST request to save Tool : {}", tool);
        if (tool.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tool", "idexists", "A new tool cannot already have an ID")).body(null);
        }
        Tool result = toolRepository.save(tool);
        return ResponseEntity.created(new URI("/api/tools/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tool", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tools : Updates an existing tool.
     *
     * @param tool the tool to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tool,
     * or with status 400 (Bad Request) if the tool is not valid,
     * or with status 500 (Internal Server Error) if the tool couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tools")
    @Timed
    public ResponseEntity<Tool> updateTool(@Valid @RequestBody Tool tool) throws URISyntaxException {
        log.debug("REST request to update Tool : {}", tool);
        if (tool.getId() == null) {
            return createTool(tool);
        }
        Tool result = toolRepository.save(tool);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tool", tool.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tools : get all the tools.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tools in body
     */
    @GetMapping("/tools")
    @Timed
    public List<Tool> getAllTools() {
        log.debug("REST request to get all Tools");
        List<Tool> tools = toolRepository.findAll();
        return tools;
    }

    /**
     * GET  /tools/:id : get the "id" tool.
     *
     * @param id the id of the tool to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tool, or with status 404 (Not Found)
     */
    @GetMapping("/tools/{id}")
    @Timed
    public ResponseEntity<Tool> getTool(@PathVariable Long id) {
        log.debug("REST request to get Tool : {}", id);
        Tool tool = toolRepository.findOne(id);
        return Optional.ofNullable(tool)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tools/:id : delete the "id" tool.
     *
     * @param id the id of the tool to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tools/{id}")
    @Timed
    public ResponseEntity<Void> deleteTool(@PathVariable Long id) {
        log.debug("REST request to delete Tool : {}", id);
        toolRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tool", id.toString())).build();
    }

}
