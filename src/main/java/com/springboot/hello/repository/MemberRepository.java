package com.springboot.hello.repository;

import com.springboot.hello.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByEmail(String email);
}
