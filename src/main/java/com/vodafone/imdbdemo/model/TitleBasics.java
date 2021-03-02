package com.vodafone.imdbdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created By : rezaee_r
 * Date : 2/25/2021
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table
public class TitleBasics {


    /*
     * alphanumeric unique identifier of the title
     */
    @Id
    private String tconst;
    /*
     * the type/format of the title (e.g. movie, short, tvseries, tvepisode, video, etc)
     */
    private String titleType;
    /*
     *the more popular title / the title used by the filmmakers on promotional materials at the point of release
     */
    private String primaryTitle;
    /*
     * original title, in the original language
     */
    private String originalTitle;
    /*
     * 0: non-adult title; 1: adult title
     */
    private String isAdult;
    private String startYear;
    private String endYear;
    /*
     *primary runtime of the title, in minutes
     */
    private String runtimeMinutes;
    private String genres;


}
