package com.javarush.jira.bugtracking.task.to;

import com.javarush.jira.common.util.validation.NoHtml;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskTagTo {
    @NotNull
    private Long taskId;

    @NotBlank
    @NoHtml
    @Size(min = 2, max = 32)
    private String tag;
}
