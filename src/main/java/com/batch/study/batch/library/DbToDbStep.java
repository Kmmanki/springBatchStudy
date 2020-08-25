package com.batch.study.batch.library;

import com.batch.study.repository.FileVORepository;
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
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
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
                .<FileVO, LibraryVO>chunk(30)
                .reader(fileVOJpaPagingItemReader())
                .processor(fileVOLibraryVOItemProcessor())
                .writer(itemWriter())
                .build();

    }

    public JpaPagingItemReader<FileVO> fileVOJpaPagingItemReader(){
        return new JpaPagingItemReaderBuilder<FileVO>()
                .name("fileVOJpaPagingItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(30)
                .queryString("SELECT f FROM tmp_library f ")
                .build();
    }

    public ItemProcessor<FileVO, LibraryVO> fileVOLibraryVOItemProcessor(){
        ItemProcessor<FileVO, LibraryVO> itemProcessor = new ItemProcessor<FileVO, LibraryVO>() {

            @Override
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
}
