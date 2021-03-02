package com.vodafone.imdbdemo.controller;

import com.vodafone.imdbdemo.model.TitleBasics;
import com.vodafone.imdbdemo.service.TitleBasicsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created By : rezaee_r
 * Date : 2/28/2021
 **/

@RestController
@RequestMapping("/api/v1")
public class TitleBasicsControler {

    private final TitleBasicsService titleBasicsService;

    public TitleBasicsControler(TitleBasicsService titleBasicsService) {
        this.titleBasicsService = titleBasicsService;
    }

    /**
     * <p> findTitleByActorsName method : Two Actor/Actress names are entered. It is informed whether the two names played in the same movie.
     * </p>
     *
     * @param actor1 the name of first actor
     * @param actor2 the name of second actor
     * @return the list of titles that two actors play together.
     */
    @GetMapping("/title/{actor1}/{actor2}")
    @ApiOperation(value = "get a same title by two actors", notes = "get a same title by two actors", nickname = "findTitleByActorsName")
    public List<TitleBasics> findTitleByActorsName(@PathVariable("actor1") String actor1, @PathVariable("actor2") String actor2) {
        return titleBasicsService.findTitleByActorsName(actor1, actor2);
    }

    /**
     * <p> findMovieDetails method : Actor / Actress name entered.  Returning the details found about the movie he/she played
     * </p>
     *
     * @param actor the name of an actor/actress
     * @return the list of title details that actor plays.
     */
    @GetMapping("/titleDetails/{actor}")
    @ApiOperation(value = "get a title details by an actor", notes = "get a title details by an actor", nickname = "findMovieDetails")
    public List<TitleBasics> findMovieDetails(@PathVariable("actor") String actor) {
        return titleBasicsService.findMovieDetailsByActorName(actor);
    }


}
