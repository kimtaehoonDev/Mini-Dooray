package com.kimtaehoonki.account.domain;

import com.kimtaehoonki.account.application.dto.response.FindMemberPasswordDto;
import com.kimtaehoonki.account.presentation.dto.response.MemberInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<MemberInfo> findMemberById(Integer id);

    <T> Optional<T> findByUsername(String username, Class<T> type);

    <T> Optional<T> findById(Integer id, Class<T> type);
}
