-- sampleテーブルを作成
CREATE TABLE sample(
	-- idカラム: 主キー、NULL禁止、自動増加
	id INT NOT NULL AUTO_INCREMENT,
	-- nameカラム：１００文字までの文字列、NULL禁止
	name VARCHAR(100) NOT NULL,
	-- idを主キーとして設定
	PRIMARY KEY(id)
);