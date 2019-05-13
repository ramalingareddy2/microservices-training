package com.pyro.cvps.job;

import org.springframework.batch.core.listener.ChunkListenerSupport;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CircleChunkListener extends ChunkListenerSupport{

	@Override
	public void afterChunk(ChunkContext context) {
		log.info("After completion chunk ::: ChunkContext --> {}",context);
		log.info("After completion chunk ::: StepContext --> {}",context.getStepContext());
	}

	@Override
	public void beforeChunk(ChunkContext context) {
		log.info("Before starting chunk ::: ChunkContext --> {}",context);
		log.info("Before starting chunk ::: StepContext --> {}",context.getStepContext());
		
		
	}

	@Override
	public void afterChunkError(ChunkContext context) {
		log.info("error in chunk ::: ChunkContext --> {}",context);
		log.info("error in chunk ::: StepContext --> {}",context.getStepContext());
		
	}
	
	

}
