package com.example.scoutsapi.services;

import com.example.scoutsapi.model.Members;
import com.example.scoutsapi.repositories.MembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MemberService {
    @Autowired
    private MembersRepository membersRepository;

    @Autowired
    public MemberService(MembersRepository membersRepository){
        this.membersRepository = membersRepository;
    }

    public List<Members> getAllMembers(){
        return membersRepository.findAll();
    }

    public Optional<Members> getMemberById(String id){
        return  membersRepository.findByMemberId(id);
    }

    public void updateMemberByID(String id, Members members){
        members.setMemberId(id);
        membersRepository.save(members);
    }

    public void deleteMemberById( String id){
        membersRepository.delete(membersRepository.findByMemberId(id).get());
    }

    public Members createMember(Members members){
        membersRepository.save(members);
        return members;
    }

}