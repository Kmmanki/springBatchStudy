package com.batch.study.repository;

import com.batch.study.vo.FileVO;
import com.batch.study.vo.LibraryVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryVORepository extends JpaRepository<LibraryVO, Long> {
}
