package com.vodafone.imdbdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * *     title.principals – Contains the principal cast/crew for titles
 * Created By : rezaee_r
 * Date : 2/27/2021
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table
public class TitlePrincipals {

    /*
     *composite PrimaryKey (ordering , tconst)
     */
    @EmbeddedId
    private TPrincipalsId tPrincipalsId;
    private String nconst;
    /*
     * the category of job that person was in
     */
    private String category;
    private String job;
    private String characters;
}
