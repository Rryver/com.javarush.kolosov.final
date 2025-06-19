package com.javarush.jira.bugtracking.task;

import com.javarush.jira.AbstractControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Month;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest extends AbstractControllerTest {
    @Autowired
    TaskService taskService;
    MockedStatic<LocalDateTime> localDateTimeMockedStatic;

    @Test
    void calculateInProgressTime() {
        LocalDateTime mockNow = LocalDateTime.of(2023, Month.JUNE, 14, 12, 5, 45);
        localDateTimeMockedStatic = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS);
        Mockito.when(LocalDateTime.now()).thenReturn(mockNow);

        //В комментариях указано в каких статусах находилась/находится задача (какие записи есть в таблице Activity)
        Assertions.assertEquals(35, taskService.calculateTimeSpentInStatus(taskService.get(8L), "in_progress")); // now in 'in_progress'
        Assertions.assertEquals(5, taskService.calculateTimeSpentInStatus(taskService.get(9L), "in_progress")); // 'in_progress' -> 'ready_for_review'
        Assertions.assertEquals(25, taskService.calculateTimeSpentInStatus(taskService.get(10L), "in_progress")); // 'in_progress' -> 'ready_for_review' -> 'in_progress'
        Assertions.assertEquals(5, taskService.calculateTimeSpentInStatus(taskService.get(11L), "in_progress")); // 'in_progress' -> 'canceled'

        Assertions.assertEquals(35, taskService.calculateTimeSpentInStatus(taskService.get(12L), "test")); // 'test'
        Assertions.assertEquals(15, taskService.calculateTimeSpentInStatus(taskService.get(13L), "test")); // 'test' -> 'done'
        Assertions.assertEquals(5, taskService.calculateTimeSpentInStatus(taskService.get(14L), "test")); // 'test' -> 'canceled'

        localDateTimeMockedStatic.close();
    }
}