package com.example.scoutsapi.controllers;


import com.example.scoutsapi.exceptions.MemberNotFoundException;
import com.example.scoutsapi.model.Members;
import com.example.scoutsapi.services.MemberService;
import org.bson.types.ObjectId;
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
    public ResponseEntity<?> getMember(@PathVariable("id") ObjectId id){
        Optional<Members> member = memberService.getMemberById(id);
        if (!member.isPresent()) {
            throw new MemberNotFoundException("Member "+id+" not found: ");
        }
        return new ResponseEntity<Members>(member.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> updateMember(@PathVariable("id") ObjectId id, @Valid @RequestBody Members modifyMember){
        Optional<Members> member = memberService.getMemberById(id);
        if (!member.isPresent()) {
            throw new MemberNotFoundException("Member "+id+" not found: ");
        }
        memberService.updateMemberByID(id, modifyMember);
        return new ResponseEntity<Members>(modifyMember, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteMember(@PathVariable("id") ObjectId id){
        Optional<Members> member = memberService.getMemberById(id);
        if (!member.isPresent()) {
            throw new MemberNotFoundException("Member "+id+" not found");
        }
        memberService.deleteMemberById(id);
        return new ResponseEntity<Members>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/",consumes = {"application/json"},produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<?> createMember(@Valid @RequestBody Members members, UriComponentsBuilder builder){
        memberService.createMember(members);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/members/{id}").buildAndExpand(members.get_id()).toUri());
        return new ResponseEntity<Members>(headers, HttpStatus.CREATED);
    }

}



