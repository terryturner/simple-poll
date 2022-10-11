package com.simple.poll.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.simple.poll.AbstractTestContainerTests;
import com.simple.poll.model.output.ApiResponseBody;

@SpringBootTest
@AutoConfigureMockMvc
public class BallotBoxControllerTests extends AbstractTestContainerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    
    private String tokenId;
    
    @BeforeEach
    public void signin() throws IOException, Exception {
        MvcResult mvcSigninRequest = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/guest/signin")
                .content(fromFile("com/simple/poll/controller/BallotBoxController_request1.json"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.resultBody").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.resultBody.tokenId").isNotEmpty())
                .andReturn()
                ;
        String mvcSigninResponse = mvcSigninRequest.getResponse().getContentAsString();
        Type apiResponseType = new TypeToken<ApiResponseBody<Map<String, Object>>>() {}.getType();
        ApiResponseBody<Map<String, Object>> apiSigninResponse = gson.fromJson(mvcSigninResponse, apiResponseType);
        tokenId = apiSigninResponse.getResultBody().get("tokenId").toString();
    }
    
    @Transactional
    @Test
    public void testVoteOnlyOnce() throws IOException, Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/ballotBox/vote")
                .header("Authorization", String.format("Bearer %s", tokenId))
                .content(fromFile("com/simple/poll/controller/BallotBoxController_request2.json"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                ;
    }
    
    @Transactional
    @Test
    public void testGetSimpleStaticAfterVoteComplete() throws IOException, Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/ballotBox/vote")
                .header("Authorization", String.format("Bearer %s", tokenId))
                .content(fromFile("com/simple/poll/controller/BallotBoxController_request3.json"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.resultBody").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.resultBody.electionId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.resultBody.electionName").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.resultBody.electionState").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.resultBody.statics").isArray())
                ;
    }
}
