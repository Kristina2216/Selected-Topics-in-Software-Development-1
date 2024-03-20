package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
import hr.fer.oprpp1.hw02.prob1.LexerException;

public class SmartScriptLexer {
	private char[] data; 
	private Token token; 
	private int currentIndex;
	public LexerState state;
	
	public SmartScriptLexer(String text) {
		int i;
		data=new char[text.length()];
		for(i=0;i<text.length();i++)
			data[i]=text.charAt(i);
		state=LexerState.TEXT;
		currentIndex=0;
	}
	
	public Token nextToken() {
		boolean escape=false;
		if(currentIndex>=data.length) {
			if(token.getType()==TokenType.EOF)
				throw new LexerException("File has been read!");
			else {
				token=new Token(TokenType.EOF, null);
				currentIndex++;
				return token;
			}
		}
		char c=data[currentIndex];
		String s="";
		if(this.state==LexerState.TEXT) {
			char b='a';
			if(c=='$' && currentIndex!=0) {
					b=data[currentIndex-1];
			}
			while(b!='{' && currentIndex<data.length) {
				c=data[currentIndex];
				if(c=='\\'){
					s=s+c;
					currentIndex++;
					try{
						c=data[currentIndex];
					}catch(IndexOutOfBoundsException e) {
						throw new SmartScriptParserException("Invalid escape sequence!");
					}if(c!='\\' && c!='{')
						throw new SmartScriptParserException("Invalid escape sequence!");
					else escape=true;
				}
				currentIndex++;
				if(c!='{') 
					s=s+c;
				else {
					try{
						c=data[currentIndex];
					}catch(IndexOutOfBoundsException e) {
						token=new Token(TokenType.STRING,s+c);
						return token;
					}if(c=='$') {
						if(!escape)
							break;
					}
					s=s+'{';
					escape=false;
				}
			}if(s.length()!=0) {
				token=new Token(TokenType.STRING,s);
				return token;
			}
			s=s+"{$";
			do {
				try {
					currentIndex++;
					c=data[currentIndex];
				}catch(IndexOutOfBoundsException ex) {
					throw new SmartScriptParserException();
				}
				}while(c==' ');
				if(c=='=') {
					token=new Token(TokenType.ECHOTAG, '=');
					state=LexerState.TAG;
					currentIndex++;
					return token;
				}else{
					String f=""+c+data[currentIndex+1]+data[currentIndex+2];
					if(f.toLowerCase().equals("for")) {
						token=new Token(TokenType.FORTAG, "{$FOR");
						state=LexerState.TAG;
						currentIndex=currentIndex+3;
						return token;
					}else if(f.toLowerCase().equals("end")){
						currentIndex=currentIndex+3;
						c=data[currentIndex];
							while(c==' ') {
								try {
									currentIndex++;
									c=data[currentIndex];
							}catch(IndexOutOfBoundsException ex) {
								throw new SmartScriptParserException();
							}
						}
						f=""+c;
						currentIndex++;
						c=data[currentIndex];
						while(c==' ') {
							try {
								currentIndex++;
								c=data[currentIndex];
						}catch(IndexOutOfBoundsException ex) {
							throw new SmartScriptParserException();
						}
						}
						f=f+c;
						if (f.equals("$}")) {
							token=new Token(TokenType.CLOSETAG, "{$END$}");
							currentIndex=currentIndex+2;
							return token;
						}
					}else throw new SmartScriptParserException("Invalid token name!");
				}throw new SmartScriptParserException("End tag has no elements!");
		}else {
			while(c==' ') {
				try {
					currentIndex++;
					c=data[currentIndex];
				}catch(IndexOutOfBoundsException ex) {
					throw new SmartScriptParserException();
				}
			}
			if(Character.isLetter(c)) {
				while(Character.isLetter(c)||Character.isDigit(c)||c=='_') {
					try {
						s=s+c;
						currentIndex++;
						c=data[currentIndex];
					}catch(IndexOutOfBoundsException ex) {
						throw new SmartScriptParserException("Tag not closed!");
					}
				}
				token=new Token(TokenType.VARIABLE, s);
				return token;
			}else if(Character.isDigit(c)) {
				boolean dot=false;
				while(Character.isDigit(c)) {
					try {
						s=s+c;
						currentIndex++;
						c=data[currentIndex];
						if(c=='.' && !dot) {
							s=s+c;
							currentIndex++;
							c=data[currentIndex];
							dot=true;
						}
					}catch(IndexOutOfBoundsException ex) {
						throw new SmartScriptParserException("Tag not closed!");
					}
				}
				if(dot) {
					token=new Token(TokenType.DOUBLE, Double.parseDouble(s));
				}else {
					token=new Token(TokenType.INTEGER, Integer.parseInt(s));
				}
				return token;
			}else if(c=='\"') {
				do {
					try {
						s=s+c;
						currentIndex++;
						c=data[currentIndex];
						if(c=='\\'){
							currentIndex++;
							c=data[currentIndex];
							if(c!='\\'&& c!='\"' && c!='n' && c!='r' )
								throw new SmartScriptParserException("Invalid escape sequence!");
							else if (c=='n') {
								s=s+'\n';
								currentIndex++;
								c=data[currentIndex];
							}else if (c=='r') {
								s=s+'\r';
								currentIndex++;
								c=data[currentIndex];
							}
							else{
								s=s+"\\"+c;
								currentIndex++;
								c=data[currentIndex];
							}
						}
					}catch(IndexOutOfBoundsException e) {
						throw new SmartScriptParserException("Tag not closed!");
					}
				}while(c!='\"');
				token=new Token(TokenType.STRING, s+'\"');
				currentIndex++;
				return token;
			}else if(c=='@') {
				currentIndex++;
				c=data[currentIndex];
				if(Character.isLetter(c)) {
					while(Character.isLetter(c)||Character.isDigit(c)||c=='_') {
						try {
							s=s+c;
							currentIndex++;
							c=data[currentIndex];
						}catch(IndexOutOfBoundsException ex) {
							throw new SmartScriptParserException("Tag not closed!");
						}
					}
					token=new Token(TokenType.FUNCTION, s);
					return token;
				}else throw new SmartScriptParserException();
		}else if (c=='-' && currentIndex<data.length-2) {
			s=s+c;
			currentIndex++;
			c=data[currentIndex];
			if(Character.isDigit(c)) {
				boolean dot=false;
				while(Character.isDigit(c)) {
					try {
						s=s+c;
						currentIndex++;
						c=data[currentIndex];
						if(c=='.' && !dot) {
							s=s+c;
							currentIndex++;
							c=data[currentIndex];
							dot=true;
						}
					}catch(IndexOutOfBoundsException ex) {
						throw new SmartScriptParserException("Tag not closed!");
					}
				}
				if(dot) {
					token=new Token(TokenType.DOUBLE, Double.parseDouble(s));
				}else {
					token=new Token(TokenType.INTEGER, Integer.parseInt(s));
				}
				return token;
			}else {
				token=new Token(TokenType.OPERATOR, '-');
				return token;
			}
		}else if (c=='+'||c=='*'||c=='/'||c=='^') {
			s=s+c;
			token=new Token(TokenType.OPERATOR, s);
			currentIndex++;
			return token;
		}else if(c=='$'&& currentIndex<=data.length-2) {
			s=s+c;
			currentIndex++;
			c=data[currentIndex];
			while(c==' ') {
				try {
					currentIndex++;
					c=data[currentIndex];
				}catch(IndexOutOfBoundsException ex) {
					throw new SmartScriptParserException("Tag not closed!");
				}
			}if(c=='}') {
				token=new Token(TokenType.ENDTAG,"$}");
				state=LexerState.TEXT;
				currentIndex++;
				return token;
			}else throw new SmartScriptParserException();
		}else throw new SmartScriptParserException();
	}
		
	}
	
}
