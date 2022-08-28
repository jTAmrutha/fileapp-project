package com.brooklet.fileapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brooklet.fileapp.model.Header;

@Repository
public interface HeaderDAO extends JpaRepository<Header, Long>{

}