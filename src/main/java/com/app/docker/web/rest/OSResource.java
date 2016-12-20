package com.app.docker.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.app.docker.domain.OS;

import com.app.docker.repository.OSRepository;
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
 * REST controller for managing OS.
 */
@RestController
@RequestMapping("/api")
public class OSResource {

    private final Logger log = LoggerFactory.getLogger(OSResource.class);
        
    @Inject
    private OSRepository oSRepository;

    /**
     * POST  /o-s : Create a new oS.
     *
     * @param oS the oS to create
     * @return the ResponseEntity with status 201 (Created) and with body the new oS, or with status 400 (Bad Request) if the oS has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/o-s")
    @Timed
    public ResponseEntity<OS> createOS(@Valid @RequestBody OS oS) throws URISyntaxException {
        log.debug("REST request to save OS : {}", oS);
        if (oS.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("oS", "idexists", "A new oS cannot already have an ID")).body(null);
        }
        OS result = oSRepository.save(oS);
        return ResponseEntity.created(new URI("/api/o-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("oS", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /o-s : Updates an existing oS.
     *
     * @param oS the oS to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated oS,
     * or with status 400 (Bad Request) if the oS is not valid,
     * or with status 500 (Internal Server Error) if the oS couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/o-s")
    @Timed
    public ResponseEntity<OS> updateOS(@Valid @RequestBody OS oS) throws URISyntaxException {
        log.debug("REST request to update OS : {}", oS);
        if (oS.getId() == null) {
            return createOS(oS);
        }
        OS result = oSRepository.save(oS);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("oS", oS.getId().toString()))
            .body(result);
    }

    /**
     * GET  /o-s : get all the oS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of oS in body
     */
    @GetMapping("/o-s")
    @Timed
    public List<OS> getAllOS() {
        log.debug("REST request to get all OS");
        List<OS> oS = oSRepository.findAllWithEagerRelationships();
        return oS;
    }

    /**
     * GET  /o-s/:id : get the "id" oS.
     *
     * @param id the id of the oS to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the oS, or with status 404 (Not Found)
     */
    @GetMapping("/o-s/{id}")
    @Timed
    public ResponseEntity<OS> getOS(@PathVariable Long id) {
        log.debug("REST request to get OS : {}", id);
        OS oS = oSRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(oS)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /o-s/:id : delete the "id" oS.
     *
     * @param id the id of the oS to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/o-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteOS(@PathVariable Long id) {
        log.debug("REST request to delete OS : {}", id);
        oSRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("oS", id.toString())).build();
    }

}
