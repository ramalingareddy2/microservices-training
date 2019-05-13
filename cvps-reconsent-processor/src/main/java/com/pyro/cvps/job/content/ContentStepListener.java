package com.pyro.cvps.job.content;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepListenerSupport;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

import com.pyro.cvps.model.SubscriptionInfo;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ContentStepListener extends StepListenerSupport<SubscriptionInfo, SubscriptionInfo>{@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.info("content step listener afterstep ------ > {}",stepExecution);
		return super.afterStep(stepExecution);
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		log.info("content step listener before starting step ------ > {}",stepExecution);
		super.beforeStep(stepExecution);
	}

	@Override
	public void afterChunk(ChunkContext context) {
		log.info("content step listener afer chunk ------ > {}",context);
		super.afterChunk(context);
	}

	@Override
	public void beforeChunk(ChunkContext context) {
		log.info("content step listener before chunk ------ > {}",context);
		super.beforeChunk(context);
	}

	


	@Override
	public void afterChunkError(ChunkContext context) {
		log.info("content step listener error in processing chunk ------ > {}",context);
		super.afterChunkError(context);
	}

}
