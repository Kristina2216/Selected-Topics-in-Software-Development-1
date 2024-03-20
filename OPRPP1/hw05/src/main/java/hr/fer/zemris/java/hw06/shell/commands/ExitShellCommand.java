package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class ExitShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.length()==0) {
			return ShellStatus.TERMINATE;
		}
		env.writeln("There can't be any arguments for the exit command!");
		env.write(env.getPromptSymbol()+" ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc=new ArrayList<String>();
		desc.add("Exit command");
		desc.add("Terminates the shell");
		desc=Collections.unmodifiableList(desc);
		return desc;
	}
	
	

}
