package com.vodafone.imdbdemo.dao;

import com.vodafone.imdbdemo.model.TitleBasics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TitleBasicsRepository extends JpaRepository<TitleBasics, String> {

    @Query(value = "SELECT * FROM titlebasics tb INNER JOIN titleprincipals tp ON tp.tconst = tb.tconst INNER JOIN namebasics nb ON tp.nconst = nb.nconst WHERE nb.primaryName IN (:actor1, :actor2) GROUP BY tb.primaryTitle HAVING count(tb.tconst) > 1 ", nativeQuery = true)
    List<TitleBasics> findTitleByActorsName(@Param("actor1") String actor1, @Param("actor2") String actor2);


    @Query(value = "\n" +
            "SELECT * FROM `titlebasics` WHERE `tconst` IN (\n" +
            "            SELECT `tconst` FROM `titleprincipals` WHERE `nconst` IN(\n" +
            "            SELECT `nconst` FROM `namebasics` WHERE `primaryName`= :actor))", nativeQuery = true)
    List<TitleBasics> findMovieDetailsByActorName(@Param("actor") String actor);


}
