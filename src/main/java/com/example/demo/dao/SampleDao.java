package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.EntForm;

@Repository
public class SampleDao {
	private final JdbcTemplate db;
	public SampleDao(JdbcTemplate db) {
		this.db = db;
	}
	
	public void insertDb(EntForm entform) {
		db.update("INSERT INTO sample (name) VALUES (?)", entform.getName());
	}
	
	public List<EntForm> searchDb(String searchString) {
		String sql = "SELECT * FROM sample";
		
		// 検索機能：コメントアウトされているのが旧コード
		//List<Map<String, Object>> resultDb1 = db.queryForList(sql);
		List<Map<String, Object>> resultDb1 = null;
		
		if (searchString != null && searchString.length() > 0) {
			sql += " WHERE name LIKE ?";
			resultDb1 = db.queryForList(sql, "%" + searchString + "%");
		}
		else {
			//データベースから取り出したデータをresultDB1に入れる
			resultDb1 = db.queryForList(sql);
		}
		// 検索機能：新コードここまで
		
		
		//画面に表示しやすい形のList(resultDB2)を用意
		List<EntForm> resultDb2 = new ArrayList<EntForm>();
		
		for(Map<String, Object> result1 : resultDb1) {
			 //データ1件分を1つのまとまりとしたEntForm型の「entformdb」を生成
			EntForm entformdb = new EntForm();
			
			//id、nameのデータをentformdbに移す
			entformdb.setId((int)result1.get("id"));
			entformdb.setName((String)result1.get("name"));
			
			resultDb2.add(entformdb);
		}
		
		return resultDb2;
	}
	
	public void deleteDb(Long id) {
		System.out.println("削除しました。id:" + id);
		db.update("DELETE FROM sample WHERE id = ?", id);
	}
	
	public EntForm selectOne(Long id) {
		 //コンソールに表示
		System.out.println("編集画面を出します");
		
		String sql = "SELECT * FROM sample WHERE id=?";
		 //データベースから目的の1件を取り出して、そのままresultDB1に入れる
		List<Map<String, Object>> resultDb1 = db.queryForList(sql, id);
		
		 //データ1件分を1つのまとまりとするので、EntForm型の「entformdb」を生成
		EntForm entformdb = new EntForm();
		
		 //id、nameのデータをentformdbに移す
		Map<String, Object> result1 = resultDb1.getFirst();
		entformdb.setId((int)result1.get("id"));
		entformdb.setName((String)result1.get("name"));
		return entformdb;
	}
	
	public void updateDb(Long id, EntForm entform) {
		 //コンソールに表示
		System.out.println("編集の実行" + entform.getName());
		
		 //UPDATEを実行
		db.update("UPDATE sample SET name = ? WHERE id = ?",entform.getName(), id);
	}
}
