package com.karim.repostiory;

import com.karim.model.FraudCheckHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FraudCheckHistoryRepostiory extends JpaRepository<FraudCheckHistory , Long> {
}
