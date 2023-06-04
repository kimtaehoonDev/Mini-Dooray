package com.kimtaehoonki.account.domain;

import com.kimtaehoonki.account.application.dto.FindInfoResponseDto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<FindInfoResponseDto> findInfoByUsername(String username);
}
