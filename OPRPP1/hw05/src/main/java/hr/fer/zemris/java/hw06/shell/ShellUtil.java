package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ShellUtil {
	
	public static String parseSinglePath(String path){
		String ret="";
		char c;
		int i;
		for(i=0;i<path.length();i++) {
			c=path.charAt(i);
			if(i==0 && c!='\"') {
				if(path.indexOf(" ")!=-1) {
					throw new ShellIOException("Function takes a single argument");
				}else
					return path;
			}
			if(i==0)
				i++;
			c=path.charAt(i);
			if(c=='\\') {
				i++;
				c=path.charAt(i);
				if(c=='\"' || c=='\\' ) {
					ret+=c;
				}else
					ret=ret+'\\'+c;
				
			}else if(c=='\"') {
				break;
			}else
				ret+=c;
		}
		if(i!=path.length()-1) {
			throw new ShellIOException();
		}else
			return ret;
	}
	
	public static List<String> parseMultiplePaths(String path){
		String ret="";
		List<String> paths=new ArrayList<String>();
		char c;
		int i;
		for(i=0;i<path.length();i++) {
			c=path.charAt(i);
			if(ret.equals("") && Character.isWhitespace(c)) {
				path=path.substring(1);
				i=-1;
				continue;
			}
			if(ret.equals("") && c!='\"') {
				if(path.indexOf(" ")!=-1) {
					paths.add(path.substring(0,path.indexOf(" ")));
					path=path.substring(path.indexOf(" ")+1);
					i=-1;
					ret="";
					continue;
				}else {
					paths.add(path);
					return paths;
				}
			}
			if(i==0)
				i++;
			c=path.charAt(i);
			if(c=='\\') {
				i++;
				c=path.charAt(i);
				if(c=='\"' || c=='\\' ) {
					ret+=c;
				}else
					ret=ret+'\\'+c;
				
			}else if(c=='\"') {
				paths.add(ret);
				path=path.substring(i+1);
				i=-1;
				ret="";
				continue;
			}else
				ret+=c;
		}
		return paths;
	}
	
	
	/*public static void main(String[] args) {
		List<String> check=ShellUtil.parseMultiplePaths("\"C:\\Program Files\\Program1\\info.txt\" C:\\tmp\\informacije.txt");
		for(String s:check)
		System.out.println(s);
	}*/

}
