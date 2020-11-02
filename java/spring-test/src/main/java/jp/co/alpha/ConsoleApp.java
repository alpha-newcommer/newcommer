package jp.co.alpha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.inject.Inject;

import jp.co.alpha.model.Todo;
import jp.co.alpha.todo.TodoManager;

public class ConsoleApp implements Executer {

	@Inject
	private TodoManager manager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see jp.co.alpha.Executer#execute()
	 */
	@Override
	public void execute() {

		boolean loop = true;
		while (loop) {

			// コマンドメニュー
			System.out.print("コマンド入力（1:追加, 2:削除, 3:終了） >");
			// コンソールから入力
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

			try {
				switch (in.readLine()) {

				case "1":
					// 指示
					System.out.print("TODO内容入力 >");
					String contents = in.readLine();
					manager.add(contents);
					listVew();
					break;
				case "2":
					// 指示
					System.out.print("削除対象ID入力 >");
					String id = in.readLine();
					int todoId = Integer.parseInt(id);
					manager.remove(todoId);
					listVew();
					break;
				case "3":
					// 終了
					System.out.print("Todoアプリを終了します。");
					loop = false;
					break;

				default:
					System.out.print("メニューに有る数字1~3を選択してください。");
					listVew();
					break;

				}
			} catch (NumberFormatException e) {
				System.out.println("削除する場合はTodoのIDを入力してください。");
			} catch (IOException e) {
				System.out.println("想定外のエラーです。最初からやり直してください。");
			}
		}

	}

	/**
	 * Todo一覧表示
	 */
	private void listVew() {
		List<Todo> todoList = manager.getList();
		for (Todo todo : todoList) {
			System.out.println(todo.getId() + ":" + todo.getContents());
		}
	}

}
