package com.kimtaehoonki.task.project.domain.repository;

import static com.kimtaehoonki.task.project.domain.entity.QMemberInProject.memberInProject;
import static com.kimtaehoonki.task.project.domain.entity.QProject.project;

import com.kimtaehoonki.task.project.ProjectStatus;
import com.kimtaehoonki.task.project.application.dto.response.ProjectPreview;
import com.kimtaehoonki.task.project.application.dto.response.QProjectPreview;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberInProjectRepositoryImpl implements MemberInProjectRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
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
