package com.example.SchoolWebApp.repository;

import com.example.SchoolWebApp.model.EClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EClassRepository extends JpaRepository<EClass, Integer> {

}
