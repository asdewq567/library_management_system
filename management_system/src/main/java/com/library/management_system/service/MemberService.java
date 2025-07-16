package com.library.management_system.service;

import com.library.management_system.dto.MemberRequestDto;
import com.library.management_system.dto.MemberResponseDto;

import java.util.List;

public interface MemberService {
    MemberResponseDto getMemberById(Integer memberId);
    List<MemberResponseDto> getMembersByName(String name);
    List<MemberResponseDto> getAllMembers();
    MemberResponseDto addMember(MemberRequestDto dto);
    MemberResponseDto updateMember(MemberRequestDto dto, Integer memberId);
    void deleteMember(Integer memberId);
    MemberResponseDto updateMemberStatus(Integer memberId);
}
