package org.example.springlab.services;

import org.example.springlab.entities.Member;
import org.example.springlab.exceptions.InvalidResourceException;
import org.example.springlab.exceptions.ResourceNotFoundException;
import org.example.springlab.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService implements MemberServiceInterface {

    private MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member getMemberById(long id) {
        Optional<Member> schrodingersMember = memberRepository.findById(id);
        if(schrodingersMember.isPresent()){
            return schrodingersMember.get();
        }
        throw new ResourceNotFoundException("Member", "Id", id);
    }

    @Override
    public void removeMemberById(long id) {
        memberRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Member", "Id", id));
        memberRepository.deleteById(id);
    }

    @Override
    public Member addMember(Member member) {
        validateMember(member);
        return memberRepository.save(member);
    }

    @Override
    public Member updateMember(long id, Member member) {
        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "Id", id));

        validateMember(member);

        existingMember.setFirstName(member.getFirstName());
        existingMember.setLastName(member.getLastName());
        existingMember.setEmail(member.getEmail());
        existingMember.setPhone(member.getPhone());
        existingMember.setDateOfBirth(member.getDateOfBirth());

        if (member.getAddress() != null) {
            if (existingMember.getAddress() != null) {
                existingMember.getAddress().setStreet(member.getAddress().getStreet());
                existingMember.getAddress().setPostalCode(member.getAddress().getPostalCode());
                existingMember.getAddress().setCity(member.getAddress().getCity());
            } else {
                existingMember.setAddress(member.getAddress());
            }
        }
        return memberRepository.save(existingMember);
    }

    private void validateMember(Member member) {
        if (member.getFirstName() == null || member.getFirstName().isEmpty()) {
            throw new InvalidResourceException("Member must have a first name");
        }

        if (member.getLastName() == null || member.getLastName().isEmpty()) {
            throw new InvalidResourceException("Member must have a last name");
        }

        if (member.getEmail() == null || member.getEmail().isEmpty()) {
            throw new InvalidResourceException("Member must have an email");
        }

        if (member.getDateOfBirth() == null || member.getDateOfBirth().isEmpty()) {
            throw new InvalidResourceException("Member must have a date of birth");
        }

        if (member.getAddress() == null) {
            throw new InvalidResourceException("Member must have an address");
        }

        if (member.getAddress().getStreet() == null || member.getAddress().getStreet().isEmpty()) {
            throw new InvalidResourceException("Address must have a street");
        }

        if (member.getAddress().getPostalCode() == null || member.getAddress().getPostalCode().isEmpty()) {
            throw new InvalidResourceException("Address must have a postal code");
        }

        if (member.getAddress().getCity() == null || member.getAddress().getCity().isEmpty()) {
            throw new InvalidResourceException("Address must have a city");
        }
    }
}
