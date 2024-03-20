package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
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

public class TreeShellCommand implements ShellCommand{

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
		if(!Files.isDirectory(src)) {
			env.writeln("The given path is not a directory!\n"+env.getPromptSymbol()+" ");
			return ShellStatus.CONTINUE;
		}
		List<Path> currentLevel=new ArrayList<Path>();
		currentLevel.add(src);
		searchDirectory(src, 0, env);
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc=new ArrayList<String>();
		desc.add("Tree command");
		desc.add("Displays directory paths and (optionally) files in each subdirectory.");
		desc.add("Expected one argument: directory path.");
		desc=Collections.unmodifiableList(desc);
		return desc;
	}
	
	private static ShellStatus searchDirectory(Path dir, int level, Environment env){
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)){
			for (Path file : stream) {
				env.write(new String(new char[level]).replace("\0", " ")+file.getFileName().toString());
				if(Files.isDirectory(file))
					searchDirectory(file, level+1,env);
			}
		}catch(IOException e) {
			env.writeln("Reading from the directory failed!");
		    env.write(env.getPromptSymbol() + " ");
		    return ShellStatus.CONTINUE;
		}
		return ShellStatus.CONTINUE;
	}
	
	

}
