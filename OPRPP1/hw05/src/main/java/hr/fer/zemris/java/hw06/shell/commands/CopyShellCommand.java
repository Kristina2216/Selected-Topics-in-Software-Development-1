package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellUtil;

public class CopyShellCommand implements ShellCommand{
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> paths=ShellUtil.parseMultiplePaths(arguments);
		if(paths.size()!=2) {
			env.writeln("Function takes 2 arguments!\n"+env.getPromptSymbol()+" ");
			return ShellStatus.CONTINUE;
		}
		Path src = Paths.get(paths.get(0));
		if(!Files.exists(src)) {
			env.writeln("File not found!\n"+env.getPromptSymbol()+" ");
			return ShellStatus.CONTINUE;
		}
		if(Files.isDirectory(src)) {
			env.writeln("First argument must be a file!\n"+env.getPromptSymbol()+" ");
			return ShellStatus.CONTINUE;
		}
		Path destination = Paths.get(paths.get(1));
		if(Files.isDirectory(destination)) {
			destination = Paths.get(destination.toString() + "\\"+src.toString().split("\\\\")[src.toString().split("\\\\").length-1] );
		}
		File dest=new File(destination.toString());
		if(!dest.exists()){
			try {
				dest.createNewFile();
			}catch(IOException e) {
			}
		}else {
			env.writeln("The Destination file alredy exists. Do you want to"
					+ "overwrite it?(yes/no)\n"+env.getPromptSymbol()+" ");
			String answer = env.readLine();
			if(answer.equals("no")) {
				env.write(env.getPromptSymbol()+" ");
				return ShellStatus.CONTINUE;
			}
		}
		try(InputStream is = Files.newInputStream(src);
				OutputStream os= Files.newOutputStream(destination)){
			byte[] buff = new byte[4096];
			int numberRead=is.read(buff);
			while(numberRead>1) {
					os.write(Arrays.copyOf(buff, numberRead));	
					numberRead=is.read(buff);
			}
			
		}catch(IOException e) {
			env.writeln("Error while copying file!\n"+env.getPromptSymbol()+" ");
			return ShellStatus.CONTINUE;
		}
		env.writeln("File successfully copied\n"+env.getPromptSymbol()+" ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc=new ArrayList<String>();
		desc.add("Copy command");
		desc.add("Copies the source file into the destination file");
		desc.add("Expected two arguments: Source (must be a file!) and Destination (Directory or a file)");
		desc=Collections.unmodifiableList(desc);
		return desc;
	}
	
	
}
