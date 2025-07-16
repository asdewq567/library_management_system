package com.library.management_system.controller;

import com.library.management_system.dto.MemberRequestDto;
import com.library.management_system.dto.MemberResponseDto;
import com.library.management_system.payload.ApiResponse;
import com.library.management_system.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/id/{memberId}")
    public ApiResponse<MemberResponseDto> getMemberById(@PathVariable("memberId") Integer memberId) {
        MemberResponseDto member = memberService.getMemberById(memberId);
        return  ApiResponse.success(member);
    }

    @GetMapping("/name/{memberName}")
    public ApiResponse<List<MemberResponseDto>> getMembersByName(@PathVariable("memberName") String memberName) {
        List<MemberResponseDto> members = memberService.getMembersByName(memberName);
        return ApiResponse.success(members);
    }

    @GetMapping("/batch")
    public ApiResponse<List<MemberResponseDto>> getAllMembers() {
        List<MemberResponseDto> members = memberService.getAllMembers();
        return ApiResponse.success(members);
    }

    @PostMapping
    public ApiResponse<MemberResponseDto> addMember(@RequestBody MemberRequestDto dto) {
        MemberResponseDto newMember = memberService.addMember(dto);
        return ApiResponse.success(newMember);
    }

    @PutMapping("/update/{memberId}")
    public ApiResponse<MemberResponseDto> updateMember(@RequestBody MemberRequestDto dto, @PathVariable("memberId") Integer memberId) {
        MemberResponseDto updatedMember = memberService.updateMember(dto, memberId);
        return ApiResponse.success(updatedMember);
    }

    @DeleteMapping("/delete/{memberId}")
    public ApiResponse<Void> deleteMember(@PathVariable("memberId") Integer memberId) {
        memberService.deleteMember(memberId);
        return ApiResponse.success();
    }

    @PatchMapping("/status/{memberId}")
    public ApiResponse<MemberResponseDto> updateMemberStatus(@PathVariable("memberId") Integer memberId) {
        MemberResponseDto updatedMember = memberService.updateMemberStatus(memberId);
        return ApiResponse.success(updatedMember);
    }
}
