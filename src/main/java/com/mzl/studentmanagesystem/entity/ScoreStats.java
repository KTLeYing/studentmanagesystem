package com.mzl.studentmanagesystem.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   ScoreStats
 * @Description: T分数统计
 * @Author: 21989
 * @CreateDate: 2020/7/30 14:09
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
public class ScoreStats {

    private double max_score;
    private double min_score;
    private double avg_score;
    private String courseName;
}
