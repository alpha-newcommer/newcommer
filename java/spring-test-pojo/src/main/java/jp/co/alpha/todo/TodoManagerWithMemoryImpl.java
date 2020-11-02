/**
 * 
 */
package jp.co.alpha.todo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.alpha.model.Todo;

/**
 * @author U582659
 *
 */
public class TodoManagerWithMemoryImpl implements TodoManager {

	// インメモリでTodoを保管
	private Map<Integer, Todo> todoMap = new HashMap<>();

	private int todoIdCnt = 0;

	@Override
	public List<Todo> getList() {
		return new ArrayList<>(todoMap.values());
	}

	@Override
	public void add(String contents) {
		Todo todo = new Todo();
		todo.setContents(contents);
		todo.setId(++todoIdCnt);
		todoMap.put(todo.getId(), todo);
	}

	@Override
	public void remove(int id) {
		todoMap.remove(id);
	}
}
