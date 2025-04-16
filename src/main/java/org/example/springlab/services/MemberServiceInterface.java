package org.example.springlab.services;

import org.example.springlab.entities.Member;

import java.util.List;

public interface MemberServiceInterface {
    List<Member> getAllMembers();
    Member getMemberById(long id);
    Member updateMember(long id, Member member);
    Member addMember(Member member);
    void removeMemberById(long id);
}
