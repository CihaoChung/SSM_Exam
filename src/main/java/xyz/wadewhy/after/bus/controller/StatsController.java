package xyz.wadewhy.after.bus.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import xyz.wadewhy.after.bus.service.ExamPaperService;
import xyz.wadewhy.after.bus.service.ExamService;

/**
 * @Author 钟子豪
 * @Date 2020/3/22
 * @description
 No such property: code for class: Script1
 * @Return
 */
@RequestMapping("/after/stats")
@Controller
public class StatsController {

	@Autowired
	private ExamService examService;
	@Autowired
	private ExamPaperService examPaperService;
	/**
	 * 成绩统计页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/exam_stats",method=RequestMethod.GET)
	public ModelAndView stats(ModelAndView model){
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("offset", 0);
		queryMap.put("pageSize", 99999);
		model.addObject("examList", examService.findList(queryMap));
		model.setViewName("after/business/stats/exam_stats");
		return model;
	}


	/**
	 * 根据考试信息统计结果
	 * @param examId
	 * @return
	 */
	@RequestMapping(value="/get_stats",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getStats(Integer examId){
		Map<String, Object> ret = new HashMap<String, Object>();
		if(examId == null){
			ret.put("type", "error");
			ret.put("msg", "选择要统计的考试信息！");
			return ret;
		}
		List<Map<String, Object>> examStats = examPaperService.getExamStats(examId);
		ret.put("type", "success");
		ret.put("msg", "统计成功！");
		ret.put("studentList", getListByMap(examStats, "sname"));
		ret.put("studentScore", getListByMap(examStats, "score"));
		return ret;
	}

	private List<Object> getListByMap(List<Map<String, Object>> mapList,String key){
		List<Object> ret = new ArrayList<Object>();
		for(Map<String, Object> map:mapList){
			ret.add(map.get(key));
		}
		return ret;
	}
}
