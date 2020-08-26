package com.batch.study.batch.library;

import com.batch.study.repository.LibraryVORepository;
import com.batch.study.repository.SidoRepository;
import com.batch.study.repository.SigunguRepository;
import com.batch.study.vo.FileVO;
import com.batch.study.vo.LibraryVO;
import com.batch.study.vo.SidoVO;
import com.batch.study.vo.SigunguVO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
@RequiredArgsConstructor
public class DbToDbStep {

//    private final FileVORepository fileVORepository;
    private final SidoRepository sidoRepository;
    private final SigunguRepository sigunguRepository;
    private final LibraryVORepository libraryVORepository;

    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;



//    @Bean(name = "dbToDbStep")
    public Step dbToDbStep(){
        return stepBuilderFactory.get("dbToDbStep")
                //FileVO(DB) -> LibraryVO (DB) 만들기
                .<FileVO, LibraryVO>chunk(30)
                .reader(fileVOJpaPagingItemReader())
                .processor(fileVOLibraryVOItemProcessor())
                .writer(jpaItemWriter())
//                .writer(itemWriter())
                .build();

    }

    //30개의 FileVO를 가져온다.
    public JpaPagingItemReader<FileVO> fileVOJpaPagingItemReader(){
        return new JpaPagingItemReaderBuilder<FileVO>()
                .name("fileVOJpaPagingItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(30)
                .queryString("SELECT f FROM tmp_library f ")
                .build();
    }

    public ItemProcessor<FileVO, LibraryVO> fileVOLibraryVOItemProcessor(){
        //반환할 ItemProcessor를 만들고 내부 process 오버라이딩

//                  InputType, OutputType
        ItemProcessor<FileVO, LibraryVO> itemProcessor = new ItemProcessor<FileVO, LibraryVO>() {

            @Override
            //FileVO를 사용하여 LibraryVO, sidoVO, SigunguVO 를만들고
            //Sido, Sigungu는 저장. LibraryVO는 반환
            public LibraryVO process(FileVO item) throws Exception {
                String sidoName = item.getSido();
                String sigunguName = item.getSigungu();

                SidoVO sidoVO = null;
                SigunguVO sigunguVO = null;

                LibraryVO libraryVO = item.fileVOToLibrary();

                if(sidoRepository.existsByName(sidoName)){
                    sidoVO = sidoRepository.findByName(sidoName);
                }else{
                   sidoVO =  sidoRepository.save(new SidoVO(sidoName));
                }

                if(sigunguRepository.existsByName(sigunguName)){
                    sigunguVO = sigunguRepository.findByName(sigunguName);
                }else{
                    sigunguVO =  sigunguRepository.save(new SigunguVO(sigunguName));
                }
                libraryVO.setSido(sidoVO);
                libraryVO.setSigungu(sigunguVO);

                return libraryVO;
            }
        };
        return itemProcessor;
    }

    public ItemWriter<LibraryVO> itemWriter(){
        return list ->{
            System.out.println("wowowowowow");
            libraryVORepository.saveAll(list);
        };
    }

    public JpaItemWriter<LibraryVO> jpaItemWriter() {
        JpaItemWriter<LibraryVO> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

}
