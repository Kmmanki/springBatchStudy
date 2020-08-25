package com.batch.study.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileVOTest {

    @Test
    void delimeterSet() {
        FileVO fileVO = new FileVO();
        fileVO.setName("덕산아내프리미엄아파트 작은도서관");
        fileVO.setSido("경상남도");
        fileVO.setSigungu("거제시");
        fileVO.setType("작은도서관");
        fileVO.setClosedDay("매주월요일, 공휴일");
        fileVO.setOpenTime("16:00");
        fileVO.setClosedTime("22:00");
        fileVO.setOpenTimeSat("16:00");
        fileVO.setClosedTimeSat("22:00");
        fileVO.setOpenTimeHol("16:00");
        fileVO.setClosedTimeHol("22:00");
        fileVO.setSeats("40");
        fileVO.setBooks("10345");
        fileVO.setSequensBooks("0");
        fileVO.setDataBooks("0");
        fileVO.setAvailableBooks("3");
        fileVO.setDueDay("7");
        fileVO.setAddress("경상남도 거제시 아주로 100-10(아주동, 덕산아내프리미엄1차)");
        fileVO.setInstitution("경상남도 거제시 덕산아내프리미엄1차아파트입주자대표회");
        fileVO.setPhone("055-681-9766");
        fileVO.setSize1("055-681-9766");
        fileVO.setSize2("142");
        fileVO.setHomePage("");
        fileVO.setLat("34.86643348");
        fileVO.setLongti("128.6849359");
        fileVO.setUpdatedDate("2020-01-30");
        fileVO.setOperCode("5370000");
        fileVO.setOperName("경상남도 거제시");

        System.out.println("adasdas");
        FileVO ttt = fileVO.delimeterSet();
        System.out.println(ttt);
    }
}