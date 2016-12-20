package com.app.docker.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A OS.
 */
@Entity
@Table(name = "os")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OS implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "os_tools",
               joinColumns = @JoinColumn(name="os_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="tools_id", referencedColumnName="ID"))
    private Set<Tool> tools = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public OS name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public OS version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public OS description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Tool> getTools() {
        return tools;
    }

    public OS tools(Set<Tool> tools) {
        this.tools = tools;
        return this;
    }

    public OS addTools(Tool tool) {
        tools.add(tool);
        tool.getOs().add(this);
        return this;
    }

    public OS removeTools(Tool tool) {
        tools.remove(tool);
        tool.getOs().remove(this);
        return this;
    }

    public void setTools(Set<Tool> tools) {
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
        OS oS = (OS) o;
        if (oS.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, oS.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OS{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", version='" + version + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
