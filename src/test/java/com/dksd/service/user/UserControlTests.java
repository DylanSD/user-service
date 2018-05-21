/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dksd.service.user;

import com.dksd.service.user.model.User;
import com.dksd.service.user.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControlTests {

    @MockBean
    private UserService<User, String> entryService;
    
    @Autowired
    private MockMvc mockMvc;

    private Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();

/*    @Test
    public void addEntryTest() throws Exception {
        User onEntry = new User();
        onEntry.setTitle("First Title");
        onEntry.setDescription("My first ever card");
        User offEntryRequest = new User();
        runAddTest();
        given(entryService.findOne("1"))
                .willReturn(onEntry);
        given(entryService.add(any(User.class)))
                .willReturn(onEntry);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/entries/1").
                contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(offEntryRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value(onEntry.getDescription()))
                .andExpect(jsonPath("$.title").value("First Title"));
    }*/

    @Test
    public void runAddTest() throws Exception {
        User requestEntry = User.EXAMPLE;
        User savedEntry = User.EXAMPLE;
        given(entryService.add(any(User.class)))
                .willReturn(savedEntry);
        String exampleEntry = gson.toJson(requestEntry);
        this.mockMvc.perform(post("/entries").
                contentType(MediaType.APPLICATION_JSON)
                .content(exampleEntry))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname").value(savedEntry.getFirstName()))
                .andExpect(jsonPath("$.lastname").value(savedEntry.getLastName()))
                .andExpect(jsonPath("$.email").value(savedEntry.getEmail()));
        Mockito.verify(entryService).add(any(User.class));
    }

    @Test
    public void getEntryTest() throws Exception {
        User retEntry = User.EXAMPLE;
        given(entryService.findOne("1"))
                .willReturn(retEntry);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/entries/1").
                contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.description").value("Description"));
        Mockito.verify(entryService).findOne("1");
    }

    @Test
    public void deleteEntryTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/entries/1").
                contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(entryService).delete("1");
    }

}
