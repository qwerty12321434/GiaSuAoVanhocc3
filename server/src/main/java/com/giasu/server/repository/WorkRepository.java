package com.giasu.server.repository;

import com.giasu.server.model.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {
    List<Work> findByGrade(String grade);
    
    @Query(value = "SELECT COALESCE(MAX(id), 0) FROM works", nativeQuery = true)
    Long findMaxId();
}