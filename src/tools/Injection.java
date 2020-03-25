package tools;

import java.util.function.Consumer;

public class Injection {

	public static void run(Runnable action) {
		if(action != null)
			action.run();
	}
	
	public static <ArgType> void run(Consumer<ArgType> action, ArgType arg) {
		if(action != null)
			action.accept(arg);
	}
}
