package com.medicaloperations.MedOps.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicaloperations.MedOps.entities.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {

}
