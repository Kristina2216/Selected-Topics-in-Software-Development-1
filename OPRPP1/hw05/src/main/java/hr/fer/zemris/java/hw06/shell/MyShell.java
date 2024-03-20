package hr.fer.zemris.java.hw06.shell;

import java.util.Scanner;

public class MyShell {

	
	public static void main(String[] args) {
		Environment env=new ShellEnvironment();
		ShellStatus status=ShellStatus.CONTINUE;
		System.out.println("Welcome to MyShell v 1.0\r\n"
				+ ">");
		while(status==ShellStatus.CONTINUE) {
			 String l = env.readLine();
			 String commandName="";
			 String arguments="";
			 if(l.contains(" ")) {
				 commandName = l.substring(0,l.indexOf(" "));
				 arguments = l.substring(commandName.length()+1);
			 }
			 else {
				commandName=l;
			 }
	        ShellCommand command = env.commands().get(commandName);

	         if (command == null) {
	              env.writeln("Invalid command!\n"+env.getPromptSymbol() + " ");
	         } else {
	             status = command.executeCommand(env, arguments);
	        }
		}

		
	}
}
