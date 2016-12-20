package com.app.docker.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.app.docker.service.OSService;
import com.app.docker.web.rest.util.HeaderUtil;
import com.app.docker.service.dto.OSDTO;

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
 * REST controller for managing OS.
 */
@RestController
@RequestMapping("/api")
public class OSResource {

    private final Logger log = LoggerFactory.getLogger(OSResource.class);
        
    @Inject
    private OSService oSService;

    /**
     * POST  /o-s : Create a new oS.
     *
     * @param oSDTO the oSDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new oSDTO, or with status 400 (Bad Request) if the oS has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/o-s")
    @Timed
    public ResponseEntity<OSDTO> createOS(@Valid @RequestBody OSDTO oSDTO) throws URISyntaxException {
        log.debug("REST request to save OS : {}", oSDTO);
        if (oSDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("oS", "idexists", "A new oS cannot already have an ID")).body(null);
        }
        OSDTO result = oSService.save(oSDTO);
        return ResponseEntity.created(new URI("/api/o-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("oS", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /o-s : Updates an existing oS.
     *
     * @param oSDTO the oSDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated oSDTO,
     * or with status 400 (Bad Request) if the oSDTO is not valid,
     * or with status 500 (Internal Server Error) if the oSDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/o-s")
    @Timed
    public ResponseEntity<OSDTO> updateOS(@Valid @RequestBody OSDTO oSDTO) throws URISyntaxException {
        log.debug("REST request to update OS : {}", oSDTO);
        if (oSDTO.getId() == null) {
            return createOS(oSDTO);
        }
        OSDTO result = oSService.save(oSDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("oS", oSDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /o-s : get all the oS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of oS in body
     */
    @GetMapping("/o-s")
    @Timed
    public List<OSDTO> getAllOS() {
        log.debug("REST request to get all OS");
        return oSService.findAll();
    }

    /**
     * GET  /o-s/:id : get the "id" oS.
     *
     * @param id the id of the oSDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the oSDTO, or with status 404 (Not Found)
     */
    @GetMapping("/o-s/{id}")
    @Timed
    public ResponseEntity<OSDTO> getOS(@PathVariable Long id) {
        log.debug("REST request to get OS : {}", id);
        OSDTO oSDTO = oSService.findOne(id);
        return Optional.ofNullable(oSDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /o-s/:id : delete the "id" oS.
     *
     * @param id the id of the oSDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/o-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteOS(@PathVariable Long id) {
        log.debug("REST request to delete OS : {}", id);
        oSService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("oS", id.toString())).build();
    }

}
