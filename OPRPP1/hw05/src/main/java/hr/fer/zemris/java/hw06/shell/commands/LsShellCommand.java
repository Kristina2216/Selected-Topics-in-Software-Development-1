package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellUtil;

public class LsShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String parsed="";
		try {
			parsed=ShellUtil.parseSinglePath(arguments);
		}catch(ShellIOException e) {
			env.writeln("Function takes only one argument!\n"+env.getPromptSymbol()+" ");
			return ShellStatus.CONTINUE;
		}
		Path dir = Paths.get(parsed);
		 if (!Files.isDirectory(dir)) {
	            env.writeln("Given path is not a directory!\n"+env.getPromptSymbol()+" ");
	            return ShellStatus.CONTINUE;
	        }
		 
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			BasicFileAttributes attributes;
			BasicFileAttributeView faView;
			for (Path file : stream) {
				int size=String.valueOf(Files.size(file)).length();
				String fileData="";
				faView = Files.getFileAttributeView(
				file, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
				attributes = faView.readAttributes();
				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
				if (Files.isDirectory(file)) {
                    fileData+="d";
                } else {
                	fileData+="-";
                }

                if (Files.isReadable(file)) {
                	fileData+="r";
                } else {
                	fileData+="-";
                }

                if (Files.isWritable(file)) {
                	fileData+="w";
                } else {
                	fileData+="-";
                }

                if (Files.isExecutable(file)) {
                	fileData+="x";
                } else {
                	fileData+="-";
                }
                fileData+=new String(new char[10-size]).replace("\0", " ")+String.valueOf(Files.size(file))
                +" "+formattedDateTime+" "+file.getFileName();
                env.write(fileData);
            }
				  	    
		}catch(IOException e) {
			env.writeln("Reading from the directory failed!");
		    env.write(env.getPromptSymbol() + " ");
		    return ShellStatus.CONTINUE;
		}
		 env.write(env.getPromptSymbol() + " ");
		 return ShellStatus.CONTINUE;
	
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc=new ArrayList<String>();
		desc.add("Ls command");
		desc.add("Lists files and directories inside the given directory");
		desc=Collections.unmodifiableList(desc);
		return desc;
	}
	
	

}
