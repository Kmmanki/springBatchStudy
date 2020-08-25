package com.batch.study.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "sido")
@Getter
@Setter
@NoArgsConstructor
public class SidoVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sidoId;
    private String name;

    public SidoVO (String name){
        this.name = name;
    }
}
