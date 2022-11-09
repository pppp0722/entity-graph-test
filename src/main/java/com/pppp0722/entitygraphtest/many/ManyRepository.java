package com.pppp0722.entitygraphtest.many;

import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManyRepository extends JpaRepository<Many, Long> {

    @EntityGraph(value = "Many.withOne")
    Optional<Many> findById(Long id);
}
