package com.brooklet.fileapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brooklet.fileapp.model.Detail;

@Repository
public interface DetailDAO extends JpaRepository<Detail, Long>{

}