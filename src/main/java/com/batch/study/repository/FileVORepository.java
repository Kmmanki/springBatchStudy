package com.batch.study.repository;

import com.batch.study.vo.FileVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileVORepository extends JpaRepository<FileVO, Long> {
}
