package jp.co.alpha.todo;

import java.util.List;

import jp.co.alpha.model.Todo;

public interface TodoManager {

	/**
	 * Todoリストの取得.
	 * 
	 * @return Todoリスト
	 */
	public List<Todo> getList();

	/**
	 * Todoの追加.
	 * 
	 * @param contents
	 */
	public void add(String contents);

	/**
	 * Todoの削除.
	 * 
	 * @param id
	 */
	public void remove(int id);
}
