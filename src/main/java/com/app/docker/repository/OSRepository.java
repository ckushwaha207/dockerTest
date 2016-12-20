package com.app.docker.repository;

import com.app.docker.domain.OS;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the OS entity.
 */
@SuppressWarnings("unused")
public interface OSRepository extends JpaRepository<OS,Long> {

    @Query("select distinct oS from OS oS left join fetch oS.tools")
    List<OS> findAllWithEagerRelationships();

    @Query("select oS from OS oS left join fetch oS.tools where oS.id =:id")
    OS findOneWithEagerRelationships(@Param("id") Long id);

}
