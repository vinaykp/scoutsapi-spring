package com.example.scoutsapi.services;


import com.example.scoutsapi.exceptions.MemberNotFoundException;
import com.example.scoutsapi.model.Members;
import com.example.scoutsapi.repositories.MembersRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
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

    public Optional<Members> getMemberById(ObjectId id){
        return  membersRepository.findBy_id(id);
    }

    public void updateMemberByID(ObjectId id, Members members){
        members.set_id(id);
        membersRepository.save(members);
    }

    public void deleteMemberById( ObjectId id){
        membersRepository.delete(membersRepository.findBy_id(id).get());
    }

    public Members createMember(Members members){
        members.set_id(ObjectId.get());
        membersRepository.save(members);
        return members;
    }

}