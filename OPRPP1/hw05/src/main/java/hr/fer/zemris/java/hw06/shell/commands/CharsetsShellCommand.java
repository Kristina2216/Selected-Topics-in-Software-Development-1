package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator; 
import java.util.Map; 

public class CharsetsShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.length()!=0) {
			env.writeln("Function takes no arguments!\n"+env.getPromptSymbol()+" ");
			return ShellStatus.CONTINUE;
		}
        Map<String, Charset> charsets 
            = Charset.availableCharsets(); 
  
        Iterator<Charset> iterator 
            = charsets.values().iterator(); 
  
        while (iterator.hasNext()) { 
            Charset one = (Charset)iterator.next(); 
            env.write(one.displayName() + " "); 
        } 
        env.write(env.getPromptSymbol() + " ");
        return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc=new ArrayList<String>();
		desc.add("Charsets command");
		desc.add("Lists names of supported charsets for existing Java platform");
		desc=Collections.unmodifiableList(desc);
		return desc;
	}
	
}
