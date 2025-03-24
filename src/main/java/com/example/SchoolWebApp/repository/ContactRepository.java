package com.example.SchoolWebApp.repository;

import com.example.SchoolWebApp.model.Contact;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    List<Contact> findByStatus(String status);

//    @Query("SELECT c FROM Contact c WHERE c.status = :status")
    @Query(value = "SELECT * FROM contact_msg WHERE status = :status", nativeQuery = true)
    Page<Contact> findByStatus(String status, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value="Update Contact c SET c.status = ?1 where id = ?2")
    int updateStatusById(String status, int id);

    Page<Contact> findOpenMsgs(String status, Pageable pageable);

    @Transactional
    @Modifying
    int updateMsgStatus(String status, int id);
}
