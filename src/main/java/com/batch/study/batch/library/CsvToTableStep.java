package com.batch.study.batch.library;

import com.batch.study.repository.FileVORepository;
import com.batch.study.vo.FileVO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.persistence.EntityManagerFactory;

@Configuration
@RequiredArgsConstructor
public class CsvToTableStep {
    //FileVO = csv에서 읽은 값을을 임시 저장하는 VO
    private final FileVORepository fileVORepository;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

//    @Bean
    public Step cvsToTableStep(){
        return stepBuilderFactory.get("cvsToTableStep")
                //FileVO를 Reader로 읽어서(.CSV 파일) FileVO로 저장 (DB =tmp_library)
                .<FileVO,FileVO>chunk(30)
                .reader(readCsvFile())
//                .writer(itemWriter())
                .writer(jpaItemWriter())
                .build();
    }

    public FlatFileItemReader<FileVO> readCsvFile(){

        FlatFileItemReader<FileVO> flatFileItemReader = new FlatFileItemReader<>();
        //읽을 리소스 파일 위치 설정
        flatFileItemReader.setResource(new FileSystemResource("/Users/kimmangi/IdeaProjects/batchSample.csv"));
        //헤더를 넣지 않기위해 첫 번째 라인은 스킵
        flatFileItemReader.setLinesToSkip(1);
//        flatFileItemReader.setSaveState(true);
        //한글이기 때문에 utf-8 ISO-8859 로 하면 한글이 깨진다.
        flatFileItemReader.setEncoding("euc-kr");

        //읽은 line을 어떻게 매핑 할 것인지 지정하기 위한 매 ->(1)LineTokenizer와 (2)FieldSetMapper 필요
        DefaultLineMapper<FileVO> defaultLineMapper = new DefaultLineMapper<>();

        //(1)LineTokenizer 입력한 이름으로 쪼갠다. 즉 첫 번째 ,이전은 name
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames(
                "name", "sido", "sigungu","type", "closedDay","openTime","closedTime"
                ,"openTimeSat","closedTimeSat","openTimeHol","closedTimeHol","seats"
                ,"books","sequensBooks","dataBooks", "availableBooks", "dueDay"
                ,"address","institution","phone","size1","size2","homePage","lat","longti"
                ,"updatedDate", "operCode", "operName"

        );
        //(2)FieldSetMapper 읽은 것을 매핑 할 객체 설정
        BeanWrapperFieldSetMapper<FileVO> beanWrapperFieldSetMapper =  new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(FileVO.class);
        //라인매퍼에 Tkenizer, mapper 할당
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        //FlatFileItemReader에 LineMapper 할당
        flatFileItemReader.setLineMapper(defaultLineMapper);
        return flatFileItemReader;
    }


    //itemWriter 에서 받은 리스트를 30개를 SaveAll하는 방식
    public ItemWriter<FileVO> itemWriter(){
        return list -> {
            fileVORepository.saveAll(list);
//            System.out.println("=========");
//            list는 Reader에서 읽은 FileVO의 리스트 Ex) List<FileVO> list
            for(Object tmpLibraryVO : list){
                System.out.println(((FileVO)tmpLibraryVO).getName());
            }
        };
    }

    //읽어보니 1개씩 save 하는 것 같은데??????? ㅇㅅㅇ?! 굳이?
    //jpaItemWriter -> doWritee
    public JpaItemWriter<FileVO> jpaItemWriter() {
        JpaItemWriter<FileVO> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

}
