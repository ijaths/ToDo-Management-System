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
public class EditController {

	@Autowired
	private TasksRepository repo;
	
	@GetMapping("/main/edit/{id}")
	public String getEdit(@PathVariable("id") Integer id,Model model) {
		model.addAttribute("task",repo.findById(id).get());
		
		return "edit";
	}
	
	@PostMapping("/main/edit/{id}")
	public String postEdit(@PathVariable("id") Integer id
			, @Validated TaskForm taskform
			, BindingResult bindingResult
			, @AuthenticationPrincipal AccountUserDetails user
			, Model model){

		// バリデーションの結果、エラーがあるかどうかチェック
//		if (bindingResult.hasErrors()) {
//			// エラーがある場合は投稿登録画面を返す
//			model.addAttribute("task",repo.findById(id).get());
//			return "edit";
//		}
		
		Tasks task = new Tasks();
		task.setId(id);
		task.setName(user.getUsername());
		task.setTitle(taskform.getTitle());
		task.setText(taskform.getText()); 
		task.setDate(LocalDate.parse(taskform.getDate()));
		task.setDone(taskform.isDone());
		repo.save(task);
		
		return "redirect:/main";
		
	}

	@PostMapping("/main/delete/{id}")
	public String delete(@PathVariable Integer id) {
		repo.deleteById(id);
		return "redirect:/main";
	}

}
