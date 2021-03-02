package com.vodafone.imdbdemo.config;

import com.vodafone.imdbdemo.model.TitlePrincipalsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created By : rezaee_r
 * Date : 2/27/2021
 **/

public class TitlePrincipalsProcessor implements ItemProcessor<TitlePrincipalsModel, TitlePrincipalsModel> {

    private static final Logger logger = LoggerFactory.getLogger("TitlePrincipalsProcessor");

    @Override
    public TitlePrincipalsModel process(TitlePrincipalsModel titlePrincipals) {
        logger.info(" insert titlePrincipals : " + titlePrincipals);
        return titlePrincipals;
    }
}

