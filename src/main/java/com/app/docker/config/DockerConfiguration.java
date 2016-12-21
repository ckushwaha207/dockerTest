package com.app.docker.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

/**
 * Created by chandan.kushwaha on 20-12-2016.
 */
@Configuration
public class DockerConfiguration {
    private final Logger log = LoggerFactory.getLogger(DockerConfiguration.class);

    @Inject
    private JHipsterProperties jHipsterProperties;

    @Bean(name = "dockerClient")
    public DockerClient getDockerClient() {
        log.debug("Creating Docker Client");
        DockerClientConfig config =
            DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(jHipsterProperties.getDocker().getHost())
                .withDockerTlsVerify(jHipsterProperties.getDocker().getTlsVerify())
                .withDockerCertPath(jHipsterProperties.getDocker().getCertPath())
                .withDockerConfig(jHipsterProperties.getDocker().getConfig())
                .build();

        DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
            .withReadTimeout(1000)
            .withConnectTimeout(1000)
            .withMaxTotalConnections(100)
            .withMaxPerRouteConnections(10);

        return DockerClientBuilder.getInstance(config)
            .withDockerCmdExecFactory(dockerCmdExecFactory)
            .build();
    }
}
