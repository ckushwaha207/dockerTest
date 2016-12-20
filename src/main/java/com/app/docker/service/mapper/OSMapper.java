package com.app.docker.service.mapper;

import com.app.docker.domain.*;
import com.app.docker.service.dto.OSDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity OS and its DTO OSDTO.
 */
@Mapper(componentModel = "spring", uses = {ToolMapper.class, })
public interface OSMapper {

    OSDTO oSToOSDTO(OS oS);

    List<OSDTO> oSToOSDTOs(List<OS> oS);

    OS oSDTOToOS(OSDTO oSDTO);

    List<OS> oSDTOsToOS(List<OSDTO> oSDTOs);

    default Tool toolFromId(Long id) {
        if (id == null) {
            return null;
        }
        Tool tool = new Tool();
        tool.setId(id);
        return tool;
    }
}
