package com.library.management_system.repository;

import com.library.management_system.pojo.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    List<Member> findAllByNameContainingIgnoreCase(String name);
}
