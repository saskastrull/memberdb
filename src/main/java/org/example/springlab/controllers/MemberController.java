package org.example.springlab.controllers;

import org.example.springlab.entities.Member;
import org.example.springlab.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // Postman GET
    @RequestMapping("/members")
    @ResponseBody
    public ResponseEntity<List<Member>>  getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    // Postman GET specific ID
    @RequestMapping("/member/{id}")
    @ResponseBody
    public Member getMemberById(@PathVariable long id) {
        return memberService.getMemberById(id);
    }

    // Postman PUT
    @PutMapping("/updatemember/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable long id, @RequestBody Member member) {
        return ResponseEntity.ok(memberService.updateMember(id, member));
    }

    // Postman POST
    @PostMapping("/addmember")
    public ResponseEntity<Member> addMember(@RequestBody Member member) {
        return new ResponseEntity<>(memberService.addMember(member), HttpStatus.CREATED);
    }

    // Postman DELETE
    @DeleteMapping("/deletemember/{id}")
    public ResponseEntity<String> deleteMemberById(@PathVariable long id) {
        memberService.removeMemberById(id);
        return ResponseEntity.ok("Member with id " + id + " removed");
    }

    // Thymeleaf DELETE-PAGE
    @GetMapping("/deletemember")
    public String showDeletePage(Model model) {
        List<Member> members = memberService.getAllMembers();
        model.addAttribute("members", members);
        return "deletepage";
    }

    // Thymeleaf DELETE
    @GetMapping("/deletememberbyid/{id}")
    public String deleteMemberByIdRedirect(@PathVariable long id) {
        memberService.removeMemberById(id);
        return "redirect:/admin/deletemember";
    }
}
