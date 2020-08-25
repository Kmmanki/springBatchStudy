package com.batch.study.batch.library;

import com.batch.study.vo.FileVO;
import com.batch.study.repository.FileVORepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@RequiredArgsConstructor
public class CsvToTableStep {

    private final FileVORepository fileVORepository;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;

//    @Bean
    public Step cvsToTableStep(){
        return stepBuilderFactory.get("cvsToTableStep")
                .<FileVO,FileVO>chunk(30)
                .reader(readCsvFile())
                .writer(itemWriter())
                .build();
    }

    public ItemWriter<FileVO> itemWriter(){
        return list -> {
            fileVORepository.saveAll(list);
            System.out.println("=========");
            for(Object tmpLibraryVO : list){
                System.out.println(((FileVO)tmpLibraryVO).getName());
            }
        };
    }

    public FlatFileItemReader<FileVO> readCsvFile(){
        FlatFileItemReader<FileVO> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("/Users/kimmangi/IdeaProjects/batchSample.csv"));
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setSaveState(true);
        flatFileItemReader.setEncoding("euc-kr");
        DefaultLineMapper<FileVO> defaultLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames(
                "name", "sido", "sigungu","type", "closedDay","openTime","closedTime"
                ,"openTimeSat","closedTimeSat","openTimeHol","closedTimeHol","seat"
                ,"books","sequensBooks","dataBooks", "availableBooks", "dueDay"
                ,"address","institution","phone","size1","size2","honePage","lat","longti"
                ,"updatedDate", "operCode", "operName"

        );

        BeanWrapperFieldSetMapper<FileVO> beanWrapperFieldSetMapper =  new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(FileVO.class);

        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        flatFileItemReader.setLineMapper(defaultLineMapper);
        return flatFileItemReader;
    }


}
