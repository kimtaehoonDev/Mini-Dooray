package com.kimtaehoonki.task.project.domain;

import static com.kimtaehoonki.task.project.domain.entity.QMemberInProject.memberInProject;
import static com.kimtaehoonki.task.project.domain.entity.QProject.project;

import com.kimtaehoonki.task.ProjectStatus;
import com.kimtaehoonki.task.project.application.dto.response.ProjectPreview;
import com.kimtaehoonki.task.project.application.dto.response.QProjectPreview;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberInProjectQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<ProjectPreview> findProjectsPreviewsUsingMemberId(Integer memberId) {
        return queryFactory
            .select(new QProjectPreview(
                project.id,
                project.name
            ))
            .from(memberInProject)
            .innerJoin(memberInProject.project, project)
            .where(
                memberInProject.memberId.eq(memberId),
                project.status.ne(ProjectStatus.EXIT
                ))
            .fetch();
    }
}
