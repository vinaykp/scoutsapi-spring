package com.example.scoutsapi.controllers;


import com.example.scoutsapi.exceptions.MemberNotFoundException;
import com.example.scoutsapi.model.Members;
import com.example.scoutsapi.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/members")
public class MembersController {
    @Autowired
    private MemberService memberService;

    @Autowired
    public MembersController(MemberService memberService){
        this.memberService = memberService;
    }

    @RequestMapping("/")
    @ResponseBody
    public ResponseEntity<List<Members>> getMembers() {
        List<Members> members =  memberService.getAllMembers();
        if (members.isEmpty()) {
            throw new MemberNotFoundException("Empty Members ");
        }
        return new ResponseEntity<List<Members>>(members, HttpStatus.OK);

    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Members> getMember(@PathVariable("id") String id){
        Optional<Members> member = memberService.getMemberById(id);
        if (!member.isPresent()) {
            throw new MemberNotFoundException("Member "+id+" not found");
        }
        return new ResponseEntity<Members>(member.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Members> updateMember(@PathVariable("id") String id, @Valid @RequestBody Members modifyMember){
        Optional<Members> member = memberService.getMemberById(id);
        if (!member.isPresent()) {
            throw new MemberNotFoundException("Member "+id+" not found to update");
        }
        modifyMember.setMemberId(id);
        memberService.updateMemberByID(id, modifyMember);
        return new ResponseEntity<Members>(modifyMember, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Members> deleteMember(@PathVariable("id") String id){
        Optional<Members> member = memberService.getMemberById(id);
        if (!member.isPresent()) {
            throw new MemberNotFoundException("Member "+id+" not found to delete");
        }
        memberService.deleteMemberById(id);
        return new ResponseEntity<Members>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/",consumes = {"application/json"},produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Members> createMember(@Valid @RequestBody Members members, UriComponentsBuilder builder){
        memberService.createMember(members);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/members/{id}").buildAndExpand(members.getMemberId()).toUri());
        return new ResponseEntity<Members>(members, HttpStatus.CREATED);
    }

}



