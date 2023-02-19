package com.dmm.task;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TasksRepository;
import com.dmm.task.service.AccountUserDetails;
import com.dmm.task.service.CreateCalendar;


@Controller
public class MainController {
	
	@Autowired
	private TasksRepository repo;
	
	@GetMapping("/main")
	public String getMain(@ModelAttribute("date") String date
			, @AuthenticationPrincipal AccountUserDetails user
			, Model model) {

		LocalDate ldate = LocalDate.now();
		
		if (!date.isEmpty()) {
			ldate = LocalDate.parse(date);
		} 
		
		// カレンダークラスをインスタンス
		CreateCalendar calendar = new CreateCalendar(ldate);
		// taskをデータベースから取得
		List<Tasks> ltasks;
		if (user.getUser().roleName.equals("ADMIN")) {
			ltasks = repo.findByDateBetween(calendar.getFromdate(), calendar.getTodate());
		} else {
			ltasks = repo.findByDateBetween(calendar.getFromdate(), calendar.getTodate(), user.getUsername());
		}
		model.addAttribute("matrix", calendar.getCalendarMatrix());
		model.addAttribute("month", calendar.getCalendarYm());
		model.addAttribute("prev", ldate.minusMonths(1));
		model.addAttribute("next", ldate.plusMonths(1));
		model.addAttribute("tasks", calendar.getMapTasks(ltasks));
		return "main";
	}

}
