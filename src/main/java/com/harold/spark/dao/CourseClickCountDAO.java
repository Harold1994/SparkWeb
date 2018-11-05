package com.harold.spark.dao;

import com.harold.spark.domain.CourseClickCount;
import com.harold.spark.util.HBaseUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据访问层
 */
@Component
public class CourseClickCountDAO {
    public static List<CourseClickCount> query(String day) throws IOException {
        List<CourseClickCount> list = new ArrayList<>();
        Map<String, Long> map = HBaseUtils.getInstance().query("imooc_course_clickcount",day);
        for(Map.Entry<String, Long> entry:map.entrySet()) {
            CourseClickCount model = new CourseClickCount();
            model.setName(entry.getKey());
            model.setValue(entry.getValue());
            list.add(model);
        }
        return list;
    }
}
