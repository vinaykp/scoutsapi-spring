package com.example.scoutsapi.services;

import com.example.scoutsapi.controllers.MembersController;
import com.example.scoutsapi.model.Members;
import com.example.scoutsapi.repositories.MembersRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = { MemberService.class }, secure = false)
public class MembersServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MembersRepository membersRepository;

    private MemberService memberService;
    String mockid = "5c1a9c97c1ae12366891aa91";
    Members mockMember = new Members( mockid, "TestX UserX", "testx.userx@x.com", "male", "12", "123-456-7890" );

    @Before
    public void setUp() {
        memberService = new MemberService(membersRepository);
    }

    @Test
    public void deleteMemberSuccess() throws Exception {
        when(membersRepository.findByMemberId(mockid)).thenReturn(Optional.of(mockMember));
        memberService.deleteMemberById(mockid);
        verify(membersRepository, times(1)).delete(mockMember);

    }
}
