package com.library.management_system.service;

import com.library.management_system.dto.MemberResponseDto;
import com.library.management_system.mapper.MemberMapper;
import com.library.management_system.pojo.Member;
import com.library.management_system.dto.MemberRequestDto;
import com.library.management_system.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberMapper memberMapper, MemberRepository memberRepository) {
        this.memberMapper = memberMapper;
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberResponseDto getMemberById(Integer memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("Member with ID " + memberId + " not found"));
        return memberMapper.toMemberResponseDto(member);
    }

    @Override
    public List<MemberResponseDto> getMembersByName(String name) {
        List<Member> members = memberRepository.findAllByNameContainingIgnoreCase(name);
        return memberMapper.toMemberResponseDtoList(members);
    }

    @Override
    public List<MemberResponseDto> getAllMembers(){
        List<Member> members = memberRepository.findAll();
        return memberMapper.toMemberResponseDtoList(members);
    }

    @Override
    public MemberResponseDto addMember(MemberRequestDto dto){
        Member member = memberMapper.toMember(dto);
        member.setActive(true);
        Member savedMember = memberRepository.save(member);
        return memberMapper.toMemberResponseDto(savedMember);
    }

    @Override
    public MemberResponseDto updateMember(MemberRequestDto dto, Integer memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("Member with ID " + memberId + " not found"));
        memberMapper.updateMemberFromDto(dto, member);
        Member savedMember = memberRepository.save(member);
        return memberMapper.toMemberResponseDto(savedMember);
    }

    @Override
    public void deleteMember(Integer memberId) {
        memberRepository.deleteById(memberId);
    }

    @Override
    public MemberResponseDto updateMemberStatus(Integer memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("Member with ID " + memberId + " not found"));
        member.setActive(!member.isActive());
        Member savedMember = memberRepository.save(member);
        return memberMapper.toMemberResponseDto(savedMember);
    }
}