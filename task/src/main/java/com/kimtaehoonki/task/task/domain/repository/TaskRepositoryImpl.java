package com.kimtaehoonki.task.task.domain.repository;

import static com.kimtaehoonki.task.milestone.domain.QMilestone.milestone;
import static com.kimtaehoonki.task.tag.domain.QTag.tag;
import static com.kimtaehoonki.task.tagtask.QTagTask.tagTask;
import static com.kimtaehoonki.task.task.domain.QTask.task;
import static com.querydsl.jpa.JPAExpressions.select;

import com.kimtaehoonki.task.milestone.domain.Milestone;
import com.kimtaehoonki.task.milestone.domain.QMilestone;
import com.kimtaehoonki.task.task.application.dto.TaskPreview;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<TaskPreview> getTasksPreview(Long projectId, Pageable pageable) {
        return queryFactory
            .selectFrom(tagTask)
            .leftJoin(tagTask.tag, tag)
            .rightJoin(tagTask.task, task)
            .leftJoin(task.mileStone, milestone)
            .where(
                task.project.id.eq(projectId)
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .transform(
                groupBy(task.id).list(
                    Projections.constructor(TaskPreview.class,
                        task.id,
                        task.project.id,
                        task.indexInProject,
                        task.title,
                        task.contents,
                        task.writerId,
                        task.writerName,
                        task.createdAt,
                        Projections.constructor(TaskPreview.MilestoneInGetTaskResponseDto.class,
                            task.mileStone.name),
                        list(
                            Projections.constructor(TaskPreview.TagsInGetTaskResponseDto.class,
                                tag.name, tag.colorCode)))));
    }
}