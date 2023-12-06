package com.xyz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyz.entity.UserFiles;

public interface FilesRepo extends JpaRepository<UserFiles, Integer> {

}
