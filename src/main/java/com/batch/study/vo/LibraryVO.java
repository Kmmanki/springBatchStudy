package com.batch.study.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity(name = "libraryvo")
@Getter
@Setter
@NoArgsConstructor
public class LibraryVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long libraryId;
    private String name;
    @OneToOne
    private SidoVO sido;
    @OneToOne
    private SigunguVO sigungu;
    private String type;
    private String closedDay;

    private String openTime;
    private String closedTime;
    private String openTimeSat;
    private String closedTimeSat;

    private String openTimeHol;
    private String closedTimeHol;

    private String seats;
    private String books;
    private String availableBooks;
    private String sequensBooks;
    private String dataBooks;
    private String dueDay;
    private String address;
    private String institution;
    private String phone;
    private String size1;



    private String size2;
    private String homePage;
    private String lat;
    private String longti;
    private String updatedDate;
    private String operCode;
    private String operName;
    
    
    public FileVO toFileVO(){
        FileVO fileVO = new FileVO();

        fileVO.setSido(this.sido.getName());
        fileVO.setSigungu(this.sigungu.getName());

        fileVO.setName(this.name);
        fileVO.setType(this.type);
        fileVO.setClosedDay(this.closedDay);
        fileVO.setOpenTime(this.openTime);
        fileVO.setClosedTime(this.closedTime);
        fileVO.setOpenTimeSat(this.openTimeSat);
        fileVO.setClosedTimeSat(this.closedTimeSat);
        fileVO.setOpenTimeHol(this.openTimeHol);
        fileVO.setClosedTimeHol(this.closedTimeHol);
        fileVO.setSeats(this.seats);
        fileVO.setBooks(this.books);
        fileVO.setAvailableBooks(this.availableBooks);
        fileVO.setSequensBooks(this.sequensBooks);
        fileVO.setDataBooks(this.dataBooks);
        fileVO.setDueDay(this.dueDay);
        fileVO.setAddress(this.address);
        fileVO.setInstitution(this.institution);
        fileVO.setPhone(this.phone);
        fileVO.setSize1(this.size1);
        fileVO.setSize2(this.size2);
        fileVO.setHomePage(this.homePage);
        fileVO.setLat(this.lat);
        fileVO.setLongti(this.longti);
        fileVO.setUpdatedDate(this.updatedDate);
        fileVO.setOperCode(this.operCode);
        fileVO.setOperName(this.operName);



        return  fileVO;
    }

}
