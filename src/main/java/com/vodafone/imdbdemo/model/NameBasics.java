package com.vodafone.imdbdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table
public class NameBasics {

    /**
     * alphanumeric unique identifier of the name/person
     */
    @Id
    private String nconst;
    /*
     * name by which the person is most often credited
     */
    private String primaryName;
    private String birthYear;
    private String deathYear;
    /*
     * the top-3 professions of the person
     */
    private String primaryProfession;
    /*
     * titles the person is known for
     */
    private String knownForTitles;


}
