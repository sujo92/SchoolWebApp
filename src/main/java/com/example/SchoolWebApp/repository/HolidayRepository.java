package com.example.SchoolWebApp.repository;

import com.example.SchoolWebApp.model.Holiday;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayRepository extends ListCrudRepository<Holiday,String> {
}
