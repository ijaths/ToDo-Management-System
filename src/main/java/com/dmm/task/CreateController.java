package com.dmm.task;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TasksRepository;
import com.dmm.task.form.TaskForm;
import com.dmm.task.service.AccountUserDetails;


@Controller
public class CreateController {
	

	@Autowired
	private TasksRepository repo;
	
	@GetMapping("/main/create/{date}")
	public String getCreate(@PathVariable("date") String date ,Model model) {
		model.addAttribute("date", LocalDate.parse(date));
		return "create";
	}
	
	@PostMapping("/main/create")
	public String create(@Validated TaskForm taskform
			, BindingResult bindingResult
			, @AuthenticationPrincipal AccountUserDetails user
			, Model model){
		// バリデーションの結果、エラーがあるかどうかチェック
//		if (bindingResult.hasErrors()) {
//			// エラーがある場合は投稿登録画面を返す
//			model.addAttribute("title", taskform.getTitle());
//			model.addAttribute("date", LocalDate.parse(taskform.getDate()));
//			model.addAttribute("text", taskform.getText());
//			return "create";
//		}
		
		Tasks task = new Tasks();
		task.setName(user.getUsername());
		task.setTitle(taskform.getTitle());
		task.setText(taskform.getText()); 
		task.setDate(LocalDate.parse(taskform.getDate()));
		repo.save(task);
		
		model.addAttribute("date", taskform.getDate());
		return "redirect:/main";
	}

}
