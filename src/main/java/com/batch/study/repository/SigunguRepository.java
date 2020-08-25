package com.batch.study.repository;

import com.batch.study.vo.SidoVO;
import com.batch.study.vo.SigunguVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SigunguRepository extends JpaRepository<SigunguVO, Long> {
    public boolean existsByName(String name);
    public SigunguVO findByName(String name);
}
