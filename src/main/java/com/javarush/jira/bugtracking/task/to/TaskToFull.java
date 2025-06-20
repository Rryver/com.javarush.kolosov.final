package com.javarush.jira.bugtracking.task.to;

import com.javarush.jira.common.to.CodeTo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
public class TaskToFull extends TaskToExt {
    CodeTo parent;
    CodeTo project;
    CodeTo sprint;
    @Setter
    List<ActivityTo> activityTos;

    @Setter
    long totalTimeInProgressStatus = 0;
    @Setter
    long totalTimeInTestingStatus = 0;

    public TaskToFull(Long id, String code, String title, String description, String typeCode, String statusCode, String priorityCode,
                      LocalDateTime updated, Integer estimate, CodeTo parent, CodeTo project, CodeTo sprint, List<ActivityTo> activityTos, Set<String> tags) {
        super(id, code, title, description, typeCode, statusCode, priorityCode, updated, estimate,
                parent == null ? null : parent.getId(), project.getId(), sprint == null ? null : sprint.getId(), tags);
        this.parent = parent;
        this.project = project;
        this.sprint = sprint;
        this.activityTos = activityTos;
    }
}
