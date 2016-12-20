package com.app.docker.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the OS entity.
 */
public class OSDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String version;

    private String description;


    private Set<ToolDTO> tools = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ToolDTO> getTools() {
        return tools;
    }

    public void setTools(Set<ToolDTO> tools) {
        this.tools = tools;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OSDTO oSDTO = (OSDTO) o;

        if ( ! Objects.equals(id, oSDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OSDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", version='" + version + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
