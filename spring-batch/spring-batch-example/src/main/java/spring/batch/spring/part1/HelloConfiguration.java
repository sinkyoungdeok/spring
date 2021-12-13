package spring.batch.spring.part1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class HelloConfiguration {
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  @Bean
  public Job helloJob() {
    return jobBuilderFactory.get("helloJob")
        .incrementer(new RunIdIncrementer())
        .start(this.hellopStep())
        .build();
  }

  @Bean
  public Step hellopStep() {
    return stepBuilderFactory.get("hellopStep")
        .tasklet((contribution, chunkContext) -> {
          log.info("hello spring batch");
          return RepeatStatus.FINISHED;
        }).build();
  }

}
