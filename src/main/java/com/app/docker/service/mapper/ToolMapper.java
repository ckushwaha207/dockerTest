package com.app.docker.service.mapper;

import com.app.docker.domain.*;
import com.app.docker.service.dto.ToolDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Tool and its DTO ToolDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ToolMapper {

    ToolDTO toolToToolDTO(Tool tool);

    List<ToolDTO> toolsToToolDTOs(List<Tool> tools);

    @Mapping(target = "os", ignore = true)
    Tool toolDTOToTool(ToolDTO toolDTO);

    List<Tool> toolDTOsToTools(List<ToolDTO> toolDTOs);
}
