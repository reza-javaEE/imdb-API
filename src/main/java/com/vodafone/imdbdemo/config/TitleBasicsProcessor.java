package com.vodafone.imdbdemo.config;

import com.vodafone.imdbdemo.model.TitleBasics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;


/**
 * Created By : rezaee_r
 * Date : 2/25/2021
 **/

public class TitleBasicsProcessor implements ItemProcessor<TitleBasics, TitleBasics> {

    private static final Logger logger = LoggerFactory.getLogger("TitleBasicsProcessor");

    @Override
    public TitleBasics process(TitleBasics titleBasics) {
        logger.info(" insert titleBasic : " + titleBasics);
        return titleBasics;
    }
}


