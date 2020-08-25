package com.batch.study.repository;

import com.batch.study.vo.SidoVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SidoRepository extends JpaRepository<SidoVO, Long> {
    public boolean existsByName(String name);
    public SidoVO findByName(String name);
}
