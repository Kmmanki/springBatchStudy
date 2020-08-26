package com.batch.study.batch.library;

import com.batch.study.repository.FileVORepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LibraryBatchConfig {

//    private final StepBuilderFactory stepBuilderFactory;
    private final FileVORepository fileVORepository;
    private final JobBuilderFactory jobBuilderFactory;


    private final CsvToTableStep csvToTableStep;

    private final DbToDbStep dbToDbStep;

    private final DbToFileStep dbToFileStep;

    @Bean
    public Job libraryJob(){
        fileVORepository.deleteAll();
        return jobBuilderFactory.get("libraryJob")
                .start(csvToTableStep.cvsToTableStep())
                .next(dbToDbStep.dbToDbStep())
                .next(dbToFileStep.dbToFileStep())
                .build();
    }




}
