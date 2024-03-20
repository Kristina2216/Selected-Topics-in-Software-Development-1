package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class ChangeSymbolShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] command=arguments.split(" ");
		if(command.length<1 || command.length>2) {
			  env.writeln("Invalid command\n"+env.getPromptSymbol() + " ");
              return ShellStatus.CONTINUE;
		}
		switch (command[0]) {
		case "PROMPT":
			if(command.length==1) {
				env.writeln("Symbol for PROMPT is \'"+env.getPromptSymbol()+"\'\n");
				return ShellStatus.CONTINUE;
			}else {
				env.writeln("Symbol for PROMPT changed from \'" +env.getPromptSymbol()+"\' to \'"+command[1]+"\'");
				env.setPromptSymbol(command[1].charAt(0));
				env.write(env.getPromptSymbol()+" ");
				return ShellStatus.CONTINUE;
			}
		case "MORELINES":
			if(command.length==1) {
				env.writeln("Symbol for MORELINES is \'"+env.getMorelinesSymbol()+"\'\n");
				return ShellStatus.CONTINUE;
			}else {
				env.writeln("Symbol for MORELINES changed from \'" +env.getMorelinesSymbol()+"\' to \'"+command[1]+"\'");
				env.setMorelinesSymbol(command[1].charAt(0));
				env.write(env.getPromptSymbol()+" ");
				return ShellStatus.CONTINUE;
			}
		case "MULTILINE":
			if(command.length==1) {
				env.writeln("Symbol for MULTILINE is \'"+env.getMultilineSymbol()+"\'\n");
				return ShellStatus.CONTINUE;
			}else {
				env.writeln("Symbol for MULTILINE changed from \'" +env.getMultilineSymbol()+"\' to \'"+command[1]+"\'");
				env.setMultilineSymbol(command[1].charAt(0));
				env.write(env.getPromptSymbol()+" ");
				return ShellStatus.CONTINUE;
			}
		default:
            env.writeln("Invalid symbol!\n"+env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
			
		}
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc=new ArrayList<String>();
		desc.add("Symbol command");
		desc.add("Writes/Changes symbol");
		desc.add("Expected one or two arguments.\nThe first is the desired character symbol. \nSecond is a character to be used instead.");
		desc=Collections.unmodifiableList(desc);
		return desc;
	}

}
