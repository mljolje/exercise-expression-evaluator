package com.leapwise.evalexp.repository;

import com.leapwise.evalexp.domain.ExpressionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpressionRepository extends JpaRepository<ExpressionEntity, Long> {
}