package com.app.docker.web.rest;

import com.app.docker.service.DockerService;
import com.app.docker.web.rest.util.HeaderUtil;
import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Docker.
 * <p>
 * Created by chandan.kushwaha on 20-12-2016.
 */
@RestController
@RequestMapping("/api")
public class DockerResource {

    private static Logger log = LoggerFactory.getLogger(DockerResource.class);

    @Inject
    private DockerService dockerService;

    @GetMapping("/docker/info")
    public ResponseEntity<Info> getInfo() {
        log.debug("REST request to get docker info.");
        Info info = dockerService.getInfo();
        return Optional.ofNullable(info)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/docker/containers")
    public List<Container> getAllContainers() {
        log.debug("REST request to get all containers.");
        List<Container> containers = dockerService.getAllContainers();
        return containers;
    }

    @GetMapping("/docker/images")
    public List<Image> getAllImages() {
        log.debug("REST request to get all images.");
        List<Image> images = dockerService.getAllImages();
        return images;
    }

    @GetMapping("/docker/images/{id}")
    public ResponseEntity<InspectImageResponse> getImage(@PathVariable String id) {
        log.debug("REST request to get image: {}", id);
        InspectImageResponse response = dockerService.getImage(id);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /*@PostMapping("/docker/images")
    public ResponseEntity<InspectImageResponse> createImage(@PathVariable String image) {
        log.debug("REST request to create image: {0}.", image);
        InspectImageResponse result = dockerService.createImage(image);

        return ResponseEntity.created(new URI("/api/images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tool", result.getId().toString()))
            .body(result);
    }*/

}
