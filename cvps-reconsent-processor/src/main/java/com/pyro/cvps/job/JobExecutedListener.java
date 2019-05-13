package com.pyro.cvps.job;

import java.util.List;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JobExecutedListener extends JobExecutionListenerSupport {

	
	@Override
    public void afterJob(JobExecution jobExecution) {
      log.info("Job execution completed ::: context {}",jobExecution);
 
        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
        	log.info("circle job completed successfully");
        }else if(jobExecution.getStatus() == BatchStatus.FAILED){
        	log.info("circle job failed with following exceptions ");
            List<Throwable> exceptionList = jobExecution.getAllFailureExceptions();
            for(Throwable th : exceptionList){
            	log.error("exception : {}" ,th.getLocalizedMessage());
            }
        }
    }
 
    

	@Override
	public void beforeJob(JobExecution jobExecution) {
		
		log.info("Before starting of Job Params>>>>>>>>>> {}",jobExecution);
		log.info("Before starting of Job start time>>>>>>>>>> {}",jobExecution.getStartTime());
	}
	
	
	
	

}
