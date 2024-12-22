package com.learning.linkedin.connection_service.repository;

import com.learning.linkedin.connection_service.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends Neo4jRepository<Person, Long> {

    Optional<Person> getByName(String name);

    @Query("MATCH (p1:Person {userId: $person1Id}), (p2:Person {userId: $person2Id}) " +
            "MERGE (p1)-[:CONNECTED_TO]->(p2) " +
            "MERGE (p2)-[:CONNECTED_TO]->(p1) " +
            "RETURN p1, p2")
    void connectPersons(Long person1Id, Long person2Id);

}
