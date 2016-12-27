package com.sid.spi.repository;

import com.sid.model.Repayment;
import com.sid.model.Statement;

import java.util.List;
import java.util.Map;

public interface RepaymentRepository {
    List<Repayment> findByStatement(Statement statement);

    Repayment save(Map<String, Object> info);

    Repayment findById(String id);
}
