package hr.fer.zemris.java.hw06.shell;

import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ChangeSymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkDirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;


public class ShellEnvironment implements Environment{
	private Scanner s;
	private char moreLinesSymbol;
	private char multiLineSymbol;
	private char promptSymbol;
	private TreeMap<String, ShellCommand> commands;
	
	public ShellEnvironment() {
		s=new Scanner(System.in);
		moreLinesSymbol='\\';
		multiLineSymbol='|';
		promptSymbol='>';
		commands=new TreeMap<String, ShellCommand>();
		commands.put("cat", new CatShellCommand());
        commands.put("charsets", new CharsetsShellCommand());
        commands.put("copy",new CopyShellCommand());
        commands.put("exit", new ExitShellCommand());
        commands.put("help", new HelpShellCommand());
        commands.put("hexdump", new HexdumpShellCommand());
        commands.put("ls", new LsShellCommand());
        commands.put("mkdir", new MkDirShellCommand());
        commands.put("tree", new TreeShellCommand());
        commands.put("symbol", new ChangeSymbolShellCommand());
	}

	
	@Override
	public String readLine() throws ShellIOException {
		String input=s.nextLine();
		while(input.endsWith(moreLinesSymbol+"")) {
			input=input.substring(0,input.length()-1);
			write(multiLineSymbol + " ");
			input=input+s.nextLine();
		}
		return input;
	}

	@Override
	public void write(String text) throws ShellIOException {
		System.out.println(text);
		
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
		
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return commands;
	}

	@Override
	public Character getMultilineSymbol() {
		return multiLineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		this.multiLineSymbol=symbol;
		
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		promptSymbol=symbol;
		
	}

	@Override
	public Character getMorelinesSymbol() {
		return moreLinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		moreLinesSymbol=symbol;
		
	}
	

}
