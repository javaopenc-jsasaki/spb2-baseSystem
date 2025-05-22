package com.example.demo.form;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.SampleDao;
import com.example.demo.entity.EntForm;

@Controller
public class FormController {

	private final SampleDao sampledao;
	public FormController(SampleDao sampledao) {
		this.sampledao = sampledao;
	}
	
	@RequestMapping("/sample")
	public String home(Model model) {
		System.out.println("sample");
		model.addAttribute("message", "Hello World!!!!!");
		return "index";
	}
	
	@RequestMapping("/form")
	public String form(Model model, Form form) {
		System.out.println("form");
		model.addAttribute("title","サンプルフォーム");
		return "form/input";
	}
	
	@RequestMapping("/confirm")
	public String confirm(@Validated Form form, BindingResult result, Model model) {
		System.out.println("confirm" + form.getName1());
		
		if (result.hasErrors()) {
			model.addAttribute("title", "入力ページ");
			return "form/input";
		}
		model.addAttribute("title", "確認ページ");
		return "form/confirm";
	}
	
	@RequestMapping("/complete")
	public String complete(Form form, Model model) {
		System.out.println("complete");
		
		EntForm entform = new EntForm();
		entform.setName(form.getName1());

		sampledao.insertDb(entform);
		return "form/complete";
	}
	
	@RequestMapping("/view")
	public String view(Model model, Form form) {
		// 検索機能：コメントアウトされているのが旧コード
		//List<EntForm> list = sampledao.searchDb(form.getSearchString());
		List<EntForm> list = sampledao.searchDb(form.getSearchString());
		// 検索機能：新コードここまで

		model.addAttribute("dbList", list);
		model.addAttribute("title", "一覧ページ");
		return "form/view";
	}
	
	@RequestMapping("/del/{id}")
	public String destroy(@PathVariable Long id) {
		sampledao.deleteDb(id);
		return "redirect:/view";
	}
	
	@RequestMapping("/edit/{id}")
	public String editView(@PathVariable Long id, Model model) {
		EntForm entformdb = sampledao.selectOne(id);
		
		model.addAttribute("entform", entformdb);
		model.addAttribute("title", "編集ページ");
		return "form/edit";
	}
	
	@RequestMapping("/edit/{id}/exe")
	public String editExe(@PathVariable Long id, Model model, Form form) {
		//フォームの値をエンティティに入れ直し
		EntForm entform = new EntForm();
		entform.setName(form.getName1());
		
		//更新の実行
		sampledao.updateDb(id, entform);
		
		//一覧画面へリダイレクト
		return "redirect:/view";
	}
}
