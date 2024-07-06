package com.uade.tpo.demo.repository.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.ChekoutEntity;
import com.uade.tpo.demo.entity.User;
@Repository
public interface CheckOutRepository extends JpaRepository<ChekoutEntity,Integer>{

    
} 
