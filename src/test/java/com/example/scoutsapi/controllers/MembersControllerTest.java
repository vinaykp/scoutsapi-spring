package com.example.scoutsapi.controllers;

import com.example.scoutsapi.model.Members;
import com.example.scoutsapi.services.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = { MembersController.class }, secure = false)
public class MembersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Autowired
    ObjectMapper objectMapper;

    ObjectId mockid = new ObjectId("5c1a9c97c1ae12366891aa91");
    Members mockMember = new Members(mockid, "TestX UserX", "testx.userx@x.com", "male", "12", "123-456-7890" );

    @Test
    public void getAllMemberSuccess() throws Exception {
        List<Members> mockMembers = new ArrayList<>();
        mockMembers.add(mockMember);

        when(
                memberService.getAllMembers()
        ).thenReturn(mockMembers);

        mockMvc.perform(get("/members/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void getAllMemberNotFound() throws Exception {
        List<Members> mockMembers = new ArrayList<>();
        when(
                memberService.getAllMembers()
        ).thenReturn(mockMembers);

        mockMvc.perform(get("/members/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getOneMemberSuccess() throws Exception {
        when(
                memberService.getMemberById(mockid)
        ).thenReturn(Optional.of(mockMember));

        mockMvc.perform(get("/members/{id}", mockMember.get_id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("age", is("12"))).andReturn();
    }

    @Test
    public void getOneMemberNotFound() throws Exception {
        when(
                memberService.getMemberById(mockid)
        ).thenReturn(Optional.empty());

        mockMvc.perform(get("/members/{id}", mockMember.get_id()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createMemberSuccess() throws Exception {
        final String dummyMemberJson = objectMapper.writeValueAsString(mockMember);
        MvcResult result = mockMvc.perform(post("/members/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dummyMemberJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        final String header = result.getResponse().getHeader("Location");
        assertThat(header, is("http://localhost/members/5c1a9c97c1ae12366891aa91"));
    }

    @Test
    public void updatSuccess() throws Exception {
        final String dummyMemberJson = objectMapper.writeValueAsString(mockMember);
        when(memberService.getMemberById(mockid)).thenReturn(Optional.of(mockMember));
        doNothing().when(memberService).updateMemberByID(mockid, mockMember);
        this.mockMvc.perform(put("/members/{id}", mockMember.get_id() )
                .contentType(MediaType.APPLICATION_JSON)
                .content(dummyMemberJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age", is(mockMember.getAge())));
    }

    @Test
    public void deleteMemberSuccess() throws Exception {
        when(memberService.getMemberById(mockid)).thenReturn(Optional.of(mockMember));
        this.mockMvc.perform(delete("/members/{id}", mockMember.get_id()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteMemberNotSuccess () throws Exception {
        when(memberService.getMemberById(mockid)).thenReturn(Optional.empty());
        final String expectedContent = String.format("Member %s not found", mockMember.get_id());
        this.mockMvc.perform(delete("/members/{id}", mockMember.get_id()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
