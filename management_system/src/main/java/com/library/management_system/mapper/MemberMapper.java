package com.library.management_system.mapper;

import com.library.management_system.dto.MemberRequestDto;
import com.library.management_system.dto.MemberResponseDto;
import com.library.management_system.pojo.Member;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberMapper {
    public Member toMember(MemberRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Member member = new Member();

        updateMemberFromDto(dto, member);

        return member;
    }

    public void updateMemberFromDto(MemberRequestDto dto, Member member) {
        member.setName(dto.name());
        member.setEmail(dto.email());
        member.setPhone(dto.phone());
        member.setAddress(dto.address());
    }

    public MemberResponseDto toMemberResponseDto(Member member){
        if (member == null) {
            return null;
        }
        return new MemberResponseDto(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getPhone(),
                member.getAddress(),
                member.isActive()
        );
    }

    public List<MemberResponseDto> toMemberResponseDtoList(List<Member> members){
        if (members == null) {
            return null;
        }
        List<MemberResponseDto> list = new ArrayList<>(members.size());
        for (Member member : members) {
            list.add(toMemberResponseDto(member));
        }

        return list;
    }
}