package jp.co.alpha.repository.todo;

import java.util.List;

import jp.co.alpha.model.Todo;

/**
 * TodoRepository.
 *
 */
/**
 * @author U582659
 *
 */
public interface TodoRepository {

	/**
	 * Todoリストの取得.
	 * 
	 * @return Todoリスト
	 */
	public List<Todo> getAll();

	/**
	 * Todoの追加.
	 * 
	 * @param todo
	 */
	public void add(Todo todo);

	/**
	 * Todoの削除.
	 * 
	 * @param id
	 */
	public void remove(int id);

}
