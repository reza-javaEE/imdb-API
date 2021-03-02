package com.vodafone.imdbdemo.service;

import com.vodafone.imdbdemo.dao.TitleBasicsRepository;
import com.vodafone.imdbdemo.model.TitleBasics;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TitleBasicsService {

    private final TitleBasicsRepository titleBasicsRepository;

    public TitleBasicsService(TitleBasicsRepository titleBasicsRepository) {
        this.titleBasicsRepository = titleBasicsRepository;
    }

    public List<TitleBasics> findTitleByActorsName(String actor1, String actor2) {
        return titleBasicsRepository.findTitleByActorsName(actor1, actor2);
    }

    public List<TitleBasics> findMovieDetailsByActorName(String actor) {
        return titleBasicsRepository.findMovieDetailsByActorName(actor);
    }
}
