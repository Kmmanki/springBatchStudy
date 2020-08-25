package com.batch.study.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "sigungu")
@Getter
@Setter
@NoArgsConstructor
public class SigunguVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sigunguId;
    private String name;

    public SigunguVO (String name ){
        this.name = name;
    }
}
