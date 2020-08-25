package com.batch.study.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Setter
@Getter
@Entity(name = "tmp_library")
@NoArgsConstructor
public class FileVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long libraryId;
    private String name;
    private String sido;
    private String sigungu;
    private String type;
    private String closedDay = "";

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


    public LibraryVO fileVOToLibrary(){
        LibraryVO libraryVO = new LibraryVO();
        libraryVO.setName(this.name);
        libraryVO.setType(this.type);
        libraryVO.setClosedDay(this.closedDay);
        libraryVO.setOpenTime(this.openTime);
        libraryVO.setClosedTime(this.closedTime);
        libraryVO.setOpenTimeSat(this.openTimeSat);
        libraryVO.setClosedTimeSat(this.closedTimeSat);
        libraryVO.setOpenTimeHol(this.openTimeHol);
        libraryVO.setClosedTimeHol(this.closedTimeHol);
        libraryVO.setSeats(this.seats);
        libraryVO.setBooks(this.books);
        libraryVO.setAvailableBooks(this.availableBooks);
        libraryVO.setSequensBooks(this.sequensBooks);
        libraryVO.setDataBooks(this.dataBooks);
        libraryVO.setDueDay(this.dueDay);
        libraryVO.setAddress(this.address);
        libraryVO.setInstitution(this.institution);
        libraryVO.setPhone(this.phone);
        libraryVO.setSize1(this.size1);
        libraryVO.setSize2(this.size2);
        libraryVO.setHomePage(this.homePage);
        libraryVO.setLat(this.lat);
        libraryVO.setLongti(this.longti);
        libraryVO.setUpdatedDate(this.updatedDate);
        libraryVO.setOperCode(this.operCode);
        libraryVO.setOperName(this.operName);


        return libraryVO;
    }

    public FileVO delimeterSet(){
        Class<FileVO> fileVOClass = (Class<FileVO>) this.getClass();
        Method[] methods = this.getClass().getMethods();

        try {
            for(Method m : methods){
                if(m.getName().contains("get") && !m.getName().equals("getLibraryId") ){
                   String tmp = (String) m.invoke(this);
//                    System.out.println(tmp);

                   if(tmp.contains(",")){
                       tmp = "\""+ tmp + "\"";
                       String methodName = m.getName().replace("get", "set");
                       Method set = this.getClass().getDeclaredMethod(methodName,String.class);
                       set.invoke(this, tmp);
                       System.out.println(m.invoke(this));
                   }


                }
            }
        }catch (Exception e){

        }







        return this;
    }

}
