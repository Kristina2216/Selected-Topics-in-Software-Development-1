package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
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

public class CatShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> paths=ShellUtil.parseMultiplePaths(arguments);
		Charset charset = Charset.defaultCharset();
		if(paths.size()==2) {
			String charSet = paths.get(1);
			 try {
	               charset = Charset.forName(charSet);
	            } catch (IllegalCharsetNameException | UnsupportedCharsetException ex) {
	                env.writeln("Charset argument is incorrect!\n"+env.getPromptSymbol() + " ");
	                return ShellStatus.CONTINUE;
	            }
		}
		Path src = Paths.get(paths.get(0));
		if(!Files.exists(src)) {
			env.writeln("File not found!\n"+env.getPromptSymbol()+" ");
			return ShellStatus.CONTINUE;
		}
		if(Files.isDirectory(src)) {
			env.writeln("First argument must be a file!\n"+env.getPromptSymbol()+" ");
			return ShellStatus.CONTINUE;
		}try(InputStream is = Files.newInputStream(src)){
			byte[] buff = new byte[4096];
			int numberRead=is.read(buff);
			while(numberRead>1) {
					env.writeln(new String(Arrays.copyOf(buff, numberRead), charset));	
					numberRead=is.read(buff);
			}
		}catch(IOException e) {
			env.writeln("Error while reading the file!\n"+env.getPromptSymbol()+" ");
			return ShellStatus.CONTINUE;
		}
		env.writeln("\n"+env.getPromptSymbol()+" ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc=new ArrayList<String>();
		desc.add("Cat command");
		desc.add("Displays the source file in the shell");
		desc.add("Expected one or two arguments.\nThe first is the source destination (must be a file). \nSecond is a preffered charset.");
		desc=Collections.unmodifiableList(desc);
		return desc;
	}
	

}
