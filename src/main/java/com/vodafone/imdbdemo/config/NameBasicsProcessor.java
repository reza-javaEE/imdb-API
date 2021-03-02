package com.vodafone.imdbdemo.config;

import com.vodafone.imdbdemo.model.NameBasics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * ItemProcessor to add business logic after reading the input and before passing it to writer for writing to the database.
 * I Create NameBasicsProcessor which will log the  records before writing to database.
 * Created By : rezaee_r
 * Date : 2/27/2021
 **/

public class NameBasicsProcessor implements ItemProcessor<NameBasics, NameBasics> {

    //todo Enable/Disable logger in application.properties

    private static final Logger logger = LoggerFactory.getLogger("nameBasicsProcessor");

    @Override
    public NameBasics process(NameBasics nameBasics) {

        logger.info(" insert nameBasics : " + nameBasics);
        return nameBasics;
    }
}
