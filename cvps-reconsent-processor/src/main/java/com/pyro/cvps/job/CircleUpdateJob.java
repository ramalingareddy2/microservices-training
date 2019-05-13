package com.pyro.cvps.job;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

//import com.pyro.cvps.job.content.ContentCircleBatchSetter;
//import com.pyro.cvps.job.content.ContentCircleChunkListener;
//import com.pyro.cvps.job.content.ContentCircleNameProcessor;
//import com.pyro.cvps.job.content.ContentStepListener;
import com.pyro.cvps.model.SubscriptionInfo;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableBatchProcessing
@Slf4j
@SuppressWarnings("unused")
public class CircleUpdateJob{
	
	private static final String JOB_NAME= "circlenamejob";
	
	@Value("${recordsToSkipIfFail}")
	private int recordsToSkip;
	
	
	@Autowired
    private JobLauncher jobLauncher;
	
	@Autowired
	private StepBuilderFactory stepFactory;
	
	@Autowired
	private JobBuilderFactory jobFactory;
	
	@Autowired
    private JobExplorer jobs;

	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SubscriptionMapper mapper;
	
	@Autowired
	private CircleNameProcessor processor;
	
//	@Autowired
//	private ContentCircleNameProcessor contentProcessor;
//	
	@Autowired
	private CircleChunkListener listener;
	
//	@Autowired
//	private ContentCircleChunkListener contentChunkListener;
//	
//	@Autowired
//	private ContentStepListener contentStepListener;
	
	@Autowired
	private JobExecutedListener jobListener;
	
	@Autowired
	private CircleBatchSetter batchSetter;
	
//	@Autowired
//	private ContentCircleBatchSetter contentBacthSetter;

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Autowired
	@Qualifier("conf")
	private PropertiesConfiguration conf;

	
	//######################################################################################
	
	/*@Bean
	public ResourcelessTransactionManager transactionManager() {
		return new ResourcelessTransactionManager();
	}

	@Bean
	public MapJobRepositoryFactoryBean mapJobRepositoryFactory(ResourcelessTransactionManager txManager)
			throws Exception {

		MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean(txManager);

		factory.afterPropertiesSet();

		return factory;
	}

	@Bean
	public JobRepository jobRepository(MapJobRepositoryFactoryBean factory) throws Exception {
		return factory.getObject();
	}
	
	
	@Bean
	public SimpleJobLauncher jobLauncher(JobRepository jobRepository) {
		SimpleJobLauncher launcher = new SimpleJobLauncher();
		launcher.setJobRepository(jobRepository);
		this.jobLauncher = launcher;
		launcher.setTaskExecutor(taskExecutor);
		return launcher;
	}*/
	
	
	
	//#######################################################################################
	

	
	
	
	@Bean
	@StepScope
	public JdbcBatchItemWriter<SubscriptionInfo> itemWriter(){
		JdbcBatchItemWriter<SubscriptionInfo> writer = new JdbcBatchItemWriter<>();
		writer.setSql(conf.getString("UPDATE_SMS_STATUS_QUERY"));
		writer.setDataSource(dataSource);
		writer.setItemPreparedStatementSetter(batchSetter);
		return writer;
	}
	
	
	
	
//	@Bean
//	@StepScope
//	public JdbcBatchItemWriter<SubscriptionInfo> contentItemWriter(){
//		JdbcBatchItemWriter<SubscriptionInfo> writer = new JdbcBatchItemWriter<>();
//		writer.setSql(conf.getString("CONTENT_UPDATE_CIRCLE_QUERY"));
//		writer.setDataSource(dataSource);
//		writer.setItemPreparedStatementSetter(contentBacthSetter);
//		return writer;
//	}
//	
	

	 @PreDestroy
	    public void destroy() throws NoSuchJobException {
	        jobs.getJobNames().forEach(name -> System.out.println("job name: {}"+ name));
	        jobs.getJobInstances(JOB_NAME, 0, jobs.getJobInstanceCount(JOB_NAME)).forEach(
	            jobInstance -> {
	            	System.out.println("job instance id {}"+ jobInstance.getInstanceId());
	            }
	        );

	    }
	
	
	
	@StepScope
	@Bean
	public JdbcCursorItemReader<SubscriptionInfo> itemReader(){
		JdbcCursorItemReader<SubscriptionInfo> reader = new JdbcCursorItemReader<>();
		reader.setDataSource(dataSource);
		reader.setRowMapper(mapper);
		reader.setSql(conf.getString("FETCH_MSISDN_QUERY"));
		return reader;
	}
	
	
	
//	@StepScope
//	@Bean
//	public JdbcCursorItemReader<SubscriptionInfo> contentItemReader(){
//		JdbcCursorItemReader<SubscriptionInfo> reader = new JdbcCursorItemReader<>();
//		reader.setDataSource(dataSource);
//		reader.setRowMapper(mapper);
//		reader.setSql(conf.getString("CONTENT_FETCH_MSISDN_QUERY"));
//		return reader;
//	}
	
	
	
	
	
	@Bean
	public Step configureStep() {
		return stepFactory.get("retrieve_data")
				   .<SubscriptionInfo,SubscriptionInfo>chunk(conf.getInt("recordspertx"))
//				   .faultTolerant()
//				   .skipLimit(recordsToSkip)
//				   .skip(Exception.class)
				   .reader(itemReader())
				   .processor(processor)
				   .writer(itemWriter())
				   .exceptionHandler((context,throwable) -> {System.out.println(throwable);})
				   //.taskExecutor(taskExecutor)
				   .listener(listener)
				   //.throttleLimit(2000)
				   .build();
			
	}
	
	
	
	
	
	
//	@Bean
//	public Step configureContentStep() {
//		return stepFactory.get("retrieve_content_data")
//				   .listener(contentStepListener)
//				   .<SubscriptionInfo,SubscriptionInfo>chunk(conf.getInt("recordspertx"))
//				   /*.faultTolerant()
//				   .skipLimit(recordsToSkip)
//				   .skip(Exception.class)*/
//				  
//				   .reader(contentItemReader())
//				   .processor(contentProcessor)
//				   .writer(contentItemWriter())
//				   .exceptionHandler((context,throwable) -> {System.out.println(throwable);})
//				   //.taskExecutor(taskExecutor)
//				   .listener(contentChunkListener)
//				   //.throttleLimit(2000)
//				   .build();
//			
//	}
	//try once
	
	
	
	@Bean	
    public Job circleNameJob() {
		return jobFactory.get("circlenamejob")
        				 .incrementer(new RunIdIncrementer())
        				 .listener(jobListener)
        				 .start(configureStep())
        				 //.next(configureContentStep())
        				 .build();
            
    }
	
	
	@Scheduled(cron = "${cron.expression}")
	public void run() throws Exception{
		JobExecution jobExecution = jobLauncher.run(circleNameJob(), 
				new JobParametersBuilder().addLong("uniqueness", System.nanoTime()).toJobParameters());
		log.info("Job Id :::: {} Exit status::: {}",jobExecution.getId(),jobExecution.getStatus());
		
	}
}
