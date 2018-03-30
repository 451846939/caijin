package com.kdkj.caijin.dao;

import com.kdkj.caijin.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilesDao extends JpaRepository<Files, String> {
}
