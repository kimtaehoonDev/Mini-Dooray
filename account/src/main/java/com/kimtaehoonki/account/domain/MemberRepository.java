package com.kimtaehoonki.account.domain;

import com.kimtaehoonki.account.application.dto.AuthInfoServiceResponseDto;
import com.kimtaehoonki.account.presentation.dto.response.GetMemberResponseDto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<AuthInfoServiceResponseDto> findInfoByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<GetMemberResponseDto> findMemberById(Integer id);
}
