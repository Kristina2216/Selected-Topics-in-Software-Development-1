package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class HelpShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.length()==0) {
			for(Entry<String, ShellCommand> entry : env.commands().entrySet()) {
				env.writeln(entry.getValue().getCommandName());
			}
		}else {
			boolean found=false;
			for(Entry<String, ShellCommand> entry : env.commands().entrySet()) {
				if(entry.getKey().equals(arguments)) {
					found=true;
					env.writeln(entry.getValue().getCommandName());
					for(String line:entry.getValue().getCommandDescription())
						env.writeln(line);
				}
			}
			if(!found) {
				env.writeln("Invalid command name!"+env.getPromptSymbol()+" ");
				return ShellStatus.CONTINUE;
		}
	}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc=new ArrayList<String>();
		desc.add("help command");
		desc.add("Creates a new directory if it doesn't alredy exist");
		desc.add("Expected directory name.");
		desc=Collections.unmodifiableList(desc);
		return desc;
	}
	
}
