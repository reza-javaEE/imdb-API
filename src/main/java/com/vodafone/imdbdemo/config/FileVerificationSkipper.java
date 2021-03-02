package com.vodafone.imdbdemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

import java.io.FileNotFoundException;

/**
 * Created By : rezaee_r
 * Date : 2/25/2021
 **/

public class FileVerificationSkipper implements SkipPolicy {

    private static final Logger logger = LoggerFactory.getLogger("badRecordLogger");

    @Override
    public boolean shouldSkip(Throwable exception, int skipCount) throws SkipLimitExceededException {
        //todo Exception handling
        if (exception instanceof FileNotFoundException) {
            logger.error("file not found exception");
            return false;
        } else {
            return true;
        }
    }
}
