package com.dmm.task.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.dmm.task.data.entity.Tasks;

public class CreateCalendar {
	// 現在の日付の定義
	private LocalDate date;
	// カレンダーの日付の定義（月初を取得）
	private LocalDate fromdate;
	// カレンダー終了日付の定義（月末を取得）
	private LocalDate todate;

	public CreateCalendar(LocalDate date) {
		this.date = date;
		this.fromdate = this.date.withDayOfMonth(1);
		this.todate = this.date.withDayOfMonth(this.date.lengthOfMonth());
		
		// 日曜日（7）でない場合は日曜日まで遡る
		if (fromdate.getDayOfWeek().getValue() != 7) {
			fromdate = fromdate.minusDays(fromdate.getDayOfWeek().getValue());
		} 
		// 土曜日（6）でない場合は土曜日まで進む
		if (todate.getDayOfWeek().getValue() < 6) {
			todate = todate.plusDays(6 - todate.getDayOfWeek().getValue());
		} else if (todate.getDayOfWeek().getValue() == 7) {
			todate = todate.plusDays(6);
		}
	}
	
	public List<List<LocalDate>> getCalendarMatrix() {
		// カレンダー開始日から終了日まで進める日付
		LocalDate movedate = fromdate;

		List<List<LocalDate>> matrix = new ArrayList<>();
		List<LocalDate> weeklist = new ArrayList<>();
		
		// movedateをtodateまでリストに格納する
		while (!movedate.isAfter(todate)) {
			
			if (movedate.getDayOfWeek().getValue() == 7) {
				weeklist = new ArrayList<>();
			}
			
			weeklist.add(movedate);
			
			if (movedate.getDayOfWeek().getValue() == 6) {
				matrix.add(weeklist);
			}
			
			// 日付を1日進める
			movedate = movedate.plusDays(1);
		}
		
		date.lengthOfMonth();
		
		return matrix;
	}
	
	// カレンダーに表示する年月を取得
	public String getCalendarYm() {
        int m = date.getMonthValue();
        return  date.getYear() + "年"
                    + (m < 10 ? "0" + m : m) + "月";
	}
	
	//　タスクをlistをmapに変換
	public MultiValueMap<LocalDate, Tasks> getMapTasks(List<Tasks> ltasks){
		MultiValueMap<LocalDate, Tasks> mtasks = new LinkedMultiValueMap<>();
		for (Tasks tasks: ltasks) {
			mtasks.add(tasks.getDate(), tasks);
		}
		return mtasks;
	}

	public LocalDate getFromdate() {
		return fromdate;
	}

	public LocalDate getTodate() {
		return todate;
	}
	
}
