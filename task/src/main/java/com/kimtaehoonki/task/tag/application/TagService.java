package com.kimtaehoonki.task.tag.application;

public interface TagService {

    Long registerTag(String name, Long projectId);

    void deleteTag(Long tagId);
}
