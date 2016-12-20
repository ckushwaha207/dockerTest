package com.app.docker.repository;

import com.app.docker.domain.Tool;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Tool entity.
 */
@SuppressWarnings("unused")
public interface ToolRepository extends JpaRepository<Tool,Long> {

}
