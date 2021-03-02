package com.vodafone.imdbdemo.controller;

import com.vodafone.imdbdemo.model.TitleBasics;
import com.vodafone.imdbdemo.service.TitleBasicsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(TitleBasicsControler.class)

public class TitleBasicsControlerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TitleBasicsService titleBasicsService;


    @Test
    public void findTitleByActorsName_TestStatus() {
        try {
            mockMvc.perform(get("/api/v1/title/Ingrid Bergman/Humphrey Bogart").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());


        } catch (Exception e) {
        }
    }

    /*@Test
    public void findTitleByActorsName_TodoEntryNotFound() throws Exception {
        when(titleBasicsService.findTitleByActorsName("Ingrid Bergman", "Humphrey Bogart")).thenThrow(new NotFoundException(""));

        mockMvc.perform(get("/api/v1/title/{actor1}/{actor2} ", "Ingrid Bergman", "Humphrey Bogart"))
                .andExpect(status().isNotFound());

        verify(titleBasicsService, times(1)).findTitleByActorsName("Ingrid Bergman", "Humphrey Bogart");
        verifyZeroInteractions(titleBasicsService);
    }*/


    @Test
    public void retrieveDetailsForTitleBasicsTest() throws Exception {

        TitleBasics mockTitleBasics = new TitleBasics("tt0000001", "", "Casablanca", "Casablanca", "0", "1942", "1942", "3", "romantic");
        List<TitleBasics> mockList = new ArrayList<>();
        mockList.add(mockTitleBasics);

        Mockito.when(
                titleBasicsService.findTitleByActorsName(Mockito.anyString(),
                        Mockito.anyString())).thenReturn(mockList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/v1/title/Ingrid Bergman/Humphrey Bogart").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());
        String expected = "[{\"tconst\":\"tt0000001\",\"titleType\":\"\",\"primaryTitle\":\"Casablanca\",\"originalTitle\":\"Casablanca\",\"isAdult\":\"0\",\"startYear\":\"1942\",\"endYear\":\"1942\",\"runtimeMinutes\":\"3\",\"genres\":\"romantic\"}]";


        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

}