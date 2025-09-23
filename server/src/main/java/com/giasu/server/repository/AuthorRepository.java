package com.giasu.server.repository;

import com.giasu.server.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query(value = "SELECT COALESCE(MAX(id), 0) FROM authors", nativeQuery = true)
    Long findMaxId();
}