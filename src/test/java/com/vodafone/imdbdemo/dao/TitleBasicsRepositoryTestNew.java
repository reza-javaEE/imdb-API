package com.vodafone.imdbdemo.dao;


import com.vodafone.imdbdemo.model.TitleBasics;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TitleBasicsRepositoryTestNew {

    @Autowired
    private TitleBasicsRepository repository;


    @Test
    public void saveTest() {
        TitleBasics titleBasics1 = new TitleBasics("tt0000001", "", "Casablanca", "Casablanca", "0", "1942", "1942", "3", "romantic");
        repository.save(titleBasics1);
        Assert.assertNotNull(repository.findById("tt0000001"));
    }


}
