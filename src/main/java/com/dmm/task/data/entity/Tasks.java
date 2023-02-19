package com.dmm.task.data.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Tasks {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // idがMySQLのauto_incrementの場合、自動生成させるためにアノテーションを付ける
	// タスクテーブルのid
	public Integer id;
	// タイトル
	public String title;
	// 名前
	public String name;
	// 内容
	public String text;
	// 日付
	public LocalDate date;
	// 完了チェック
	public boolean done;
}
