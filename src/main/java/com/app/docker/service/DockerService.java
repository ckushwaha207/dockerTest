package com.app.docker.service;

import com.app.docker.config.DockerConfiguration;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.PullResponseItem;
import com.github.dockerjava.core.command.PullImageResultCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by chandan.kushwaha on 20-12-2016.
 */
@Service
public class DockerService {

    private final Logger log = LoggerFactory.getLogger(DockerService.class);

    @Inject
    private DockerClient dockerClient;

    public Info getInfo() {
        log.debug("Get docker info.");
        return dockerClient.infoCmd().exec();
    }

    public List<Image> getAllImages() {
        log.debug("Get all docker image(s).");
        return dockerClient.listImagesCmd().exec();
    }

    public List<Container> getAllContainers() {
        log.debug("Get all docker container(s).");
        return dockerClient.listContainersCmd().exec();
    }

    public void createImage(String image) {
        log.debug("Create image: {0}", image);
        dockerClient.pullImageCmd(image).exec(new PullImageResultCallback()).awaitSuccess();
    }


    public InspectImageResponse getImage(String id) {
        return dockerClient.inspectImageCmd(id).exec();
    }
}
