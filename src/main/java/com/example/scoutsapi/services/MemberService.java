package com.example.scoutsapi.services;

import com.example.scoutsapi.model.Members;
import com.example.scoutsapi.repositories.MembersRepository;
import org.bson.types.ObjectId;
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
        members.set_id(new ObjectId(members.getMemberId()));
        membersRepository.save(members);
    }

    public void deleteMemberById( String id){
        Members members = membersRepository.findByMemberId(id).get();
        membersRepository.delete(members);
    }

    public Members createMember(Members members){
        members.set_id(new ObjectId(members.getMemberId()));
        membersRepository.save(members);
        return members;
    }

}