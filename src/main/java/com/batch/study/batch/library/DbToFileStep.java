package com.batch.study.batch.library;

import com.batch.study.vo.FileVO;
import com.batch.study.vo.LibraryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import javax.persistence.EntityManagerFactory;

@Configuration
@RequiredArgsConstructor
public class DbToFileStep {
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    public Step dbToFileStep(){
        return stepBuilderFactory.get("dbToFileStep")
                .<LibraryVO, FileVO>chunk(30)
                .reader(jpaPagingItemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    public FlatFileItemWriter<FileVO> itemWriter(){
        //저장할 위치
        Resource outputResource = new FileSystemResource("/Users/kimmangi/IdeaProjects/outputData.csv");
        FlatFileItemWriter<FileVO> writer = new FlatFileItemWriter<>();
        writer.setResource(outputResource);

        //덮어쓰기
        writer.setAppendAllowed(true);

        //FlatFileItemWriter는 Resource, LineAggregator에 기본적으로 의존성을 갖으며, LineAggregator에 따라 구분자(Delimited)와 고정길이(Fixed Length) 방식으로 쓸 수 있다.
        //temReader, ItemProcessor 과정을 거친 Item을 1 라인의 String으로 변환하는 과정
        writer.setLineAggregator(new DelimitedLineAggregator<FileVO>() {
            {
                //각 컬럼의 구분은 ,로 한다.
                setDelimiter(",");

                setFieldExtractor(new BeanWrapperFieldExtractor<FileVO>() {
                    {
                        //BeanWrapperFieldExtractor에 아래와 같은 항목을 설정해야한다.
                        //Name은 추출할 필드명을 기입
                        setNames(new String[] {
                                "name", "sido", "sigungu","type", "closedDay","openTime","closedTime"
                                ,"openTimeSat","closedTimeSat","openTimeHol","closedTimeHol","seats"
                                ,"books","sequensBooks","dataBooks", "availableBooks", "dueDay"
                                ,"address","institution","phone","size1","size2","homePage","lat","longti"
                                ,"updatedDate", "operCode", "operName"
                        });
                    }
                });
            }
        });
        return writer;
    }

    public JpaPagingItemReader<LibraryVO> jpaPagingItemReader(){
        return new JpaPagingItemReaderBuilder<LibraryVO>()
                .entityManagerFactory(entityManagerFactory)
                .name("jpaPagingItemLibraryVO")
                .pageSize(30)
                .queryString("SELECT l FROM libraryvo l")
                .build();
    }

    public ItemProcessor<LibraryVO, FileVO> itemProcessor(){
        ItemProcessor<LibraryVO, FileVO> itemProcessor = new ItemProcessor<LibraryVO, FileVO>() {
            @Override
            public FileVO process(LibraryVO item) throws Exception {
                //delemeterSet은 각 컬럼 내부에 ,가 존재하여 잘못 파싱되는 경우가 발생하여
                //, 를 가지고 있는 컬럼이라면 월,화,수 ->  \"월,화,수\" 방식으로 변환                return item.toFileVO().delimeterSet();
            }
        };
        return itemProcessor;

    }
}
