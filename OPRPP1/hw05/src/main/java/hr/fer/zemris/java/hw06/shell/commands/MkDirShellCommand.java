package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellUtil;

public class MkDirShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String parsed="";
		try {
			parsed=ShellUtil.parseSinglePath(arguments);
		}catch(ShellIOException e) {
			env.writeln("Function takes only one argument!\n"+env.getPromptSymbol()+" ");
			return ShellStatus.CONTINUE;
		}
		Path src=Paths.get(parsed);
		if(Files.exists(src)){
			env.writeln("File alredy exists!\n"+env.getPromptSymbol()+" ");
			return ShellStatus.CONTINUE;
		}try {
			Files.createDirectories(src);
		}catch(IOException e) {
			env.writeln("Error occured while creating the directory!\n"+env.getPromptSymbol()+" ");
			return ShellStatus.CONTINUE;
		}env.writeln("Directory successfully created!\n"+env.getPromptSymbol()+" ");
		return ShellStatus.CONTINUE;
		
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc=new ArrayList<String>();
		desc.add("Mkdir command");
		desc.add("Creates a new directory if it doesn't alredy exist");
		desc.add("Expected directory name.");
		desc=Collections.unmodifiableList(desc);
		return desc;
	}
	

}
