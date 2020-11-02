package jp.co.alpha;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * コンソールTodoアプリ
 *
 */
public class App {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		Executer exe = context.getBean(ConsoleApp.class);
		exe.execute();
	}
}
