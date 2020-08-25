package com.batch.study.batch.library;

import com.batch.study.vo.FileVO;
import com.batch.study.vo.LibraryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
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

    public ItemWriter<FileVO> itemWriter(){
        Resource outputResource = new FileSystemResource("/Users/kimmangi/IdeaProjects/outputData.csv");
        FlatFileItemWriter<FileVO> writer = new FlatFileItemWriter<>();
        System.out.println("=========");
        writer.setResource(outputResource);

        writer.setAppendAllowed(true);

        writer.setLineAggregator(new DelimitedLineAggregator<FileVO>() {
            {
                setDelimiter(",");

                setFieldExtractor(new BeanWrapperFieldExtractor<FileVO>() {
                    {

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

                return item.toFileVO().delimeterSet();
            }
        };
        return itemProcessor;

    }
}
