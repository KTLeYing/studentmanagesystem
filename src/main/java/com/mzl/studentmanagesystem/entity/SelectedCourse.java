package com.mzl.studentmanagesystem.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.util.IntrospectorCleanupListener;

/**
 * @ClassName :   SelectedCourse
 * @Description: 选课
 * @Author: 21989
 * @CreateDate: 2020/7/30 14:04
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
public class SelectedCourse {

    private Integer id;
    private Integer studentId;
    private Integer courseId;
}
