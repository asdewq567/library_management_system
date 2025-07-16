package com.library.management_system.dto;

import com.library.management_system.pojo.Address;

public record MemberResponseDto(
        Integer id,

        String name,

        String email,

        String phone,

        Address address,

        Boolean active
) {
}
