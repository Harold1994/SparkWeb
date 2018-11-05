package com.harold.spark.sparkweb;

import com.harold.spark.dao.CourseClickCountDAO;
import com.harold.spark.domain.CourseClickCount;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class imoocDynamicCourseCount {
    private static Map<String,String> courses = new HashMap<>();
    static {
        courses.put("259","Spark SQL慕课网日志分析");
        courses.put("3324","10小时入门大数据");
        courses.put("4234","深度学习之神经网络核心原理与算法");
        courses.put("43","强大的Node.js在Web开发的应用");
        courses.put("12","Vue+Django实战");
        courses.put("789","Web前端性能优化");
        courses.put("803","强大的Redis在Web开发的应用");
        courses.put("890","Flume实战");
    }

//    @RequestMapping(value="/dynamic_count", method = RequestMethod.GET)
//    public ModelAndView dynamicCount() throws IOException {
//        ModelAndView view = new ModelAndView("index");
//        List<CourseClickCount> list = CourseClickCountDAO.query("20181105");
//        for(CourseClickCount model: list) {
//            model.setName(courses.get(model.getName().substring(9)));
//        }
//        JSONArray json = JSONArray.fromObject(list);
//        view.addObject(json);
//        return view;
//    }

    @RequestMapping(value="/dynamic_count", method = RequestMethod.POST)
    @ResponseBody
    public List<CourseClickCount> dynamicCount() throws IOException {
        List<CourseClickCount> list = CourseClickCountDAO.query("20181104_");
        for(CourseClickCount model: list) {
            model.setName(courses.get(model.getName().substring(9)));
        }
        return list;
    }

    @RequestMapping(value="/echarts", method = RequestMethod.GET)
    public ModelAndView echats() {
        return new ModelAndView("echarts");
    }

}
