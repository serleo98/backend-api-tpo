package com.uade.tpo.demo.repository.db;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.uade.tpo.demo.entity.InformProblemEntity;
@Repository
public interface InformProblemRepository  extends JpaRepository<InformProblemEntity, Long>{
    
}
