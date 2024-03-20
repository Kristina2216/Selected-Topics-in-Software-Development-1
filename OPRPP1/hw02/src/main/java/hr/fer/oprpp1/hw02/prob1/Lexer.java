package hr.fer.oprpp1.hw02.prob1;


public class Lexer {
	private char[] data; 
	private Token token; 
	private int currentIndex;
	private LexerState state;
	public Lexer(String text) {
		int i;
		data=new char[text.length()];
		for(i=0;i<text.length();i++) {
			data[i]=text.charAt(i);
		}
		if (data.length>0) {
			if(Character.isDigit(data[0])) 
				token=new Token(TokenType.NUMBER, null);
			else if(Character.isLetter(data[0])) 
				token=new Token(TokenType.WORD, null);
			else 
				token=new Token(TokenType.SYMBOL, null);
		}else
			token=new Token(TokenType.WORD, null);
		currentIndex=0;
		state=LexerState.BASIC;
	}
	public void setState(LexerState state) {
		if(state==null)
			throw new NullPointerException();
		this.state=state;
	}
	
	public Token nextToken() {
		if(currentIndex>=data.length) {
			if(token.getType()==TokenType.EOF)
				throw new LexerException("File has been read!");
			else {
				token=new Token(TokenType.EOF, null);
				currentIndex++;
				System.out.println("EOF"+" "+token.getValue());
				return token;
			}
		}
	
		String s="";
		boolean escape=false;
		char c=data[currentIndex];
		TokenType t;
		if(c=='#') {
			if(this.state==LexerState.BASIC) {
				this.setState(LexerState.EXTENDED);
				token=new Token(TokenType.SYMBOL,'#');
				System.out.println("SYMBOL: #");
				currentIndex++;
				return token;
			}else {
				this.state=LexerState.BASIC;
				token=new Token(TokenType.SYMBOL,'#');
				System.out.println("SYMBOL: #");
				currentIndex++;
				return token;
				}
		}
		if(this.state==LexerState.EXTENDED) {
			do{
				if(data[currentIndex]!='\n'&& data[currentIndex]!='\r'&& data[currentIndex]!='\t'&& data[currentIndex] !=' ') 
					s=s+data[currentIndex];
				currentIndex++;
				System.out.println(currentIndex);
				if(data[currentIndex]==' ' && s.length()!=0) {
					token=new Token(TokenType.WORD,s);
					currentIndex++;
					return token;
				}
			}while(currentIndex<(data.length-1) && data[currentIndex]!='#');
			if(s.length()==0 &&currentIndex>=data.length-1) {
				token=new Token(TokenType.EOF, null);
				System.out.println("Prazan");
				currentIndex++;
				return token;
			}
			token=new Token(TokenType.WORD,s);
			System.out.println("WORD: "+s);
			return token;
		}
		while(c=='\n'||c=='\r'||c=='\t'|| c==' ') {
			currentIndex++;
			try {
				c=data[currentIndex];
			}catch(IndexOutOfBoundsException e) {
				token=new Token(TokenType.EOF, null);
				currentIndex++;
				System.out.println("EOF"+" "+token.getValue());
				return token;
			}
		}
		if(c=='\\') {
			escape=true;
		}
		if(Character.isDigit(c))
			t=TokenType.NUMBER;
		else t=TokenType.WORD;
		while(true) {
			if(!escape) 
				s=s+Character.toString(c);
			currentIndex++;
			try {
				c=data[currentIndex];
				if(c=='\\') {
					if(!escape) {
						escape=true;
						if(t==TokenType.NUMBER) {
							token=new Token(TokenType.NUMBER, Long.parseLong(s));
							System.out.println("NUMBER"+" "+token.getValue());
							return token;
						}	
						currentIndex++;
						c=data[currentIndex];
						if(c=='\\') {
						currentIndex--;
						continue;
							}
					}else {
						escape=false;
						continue;
					}
					
				}
			}catch(IndexOutOfBoundsException e) {
				c=data[currentIndex-1];
				if(escape)
					throw new LexerException();
				if(s.length()==1 && !Character.isLetter(c)) {
					token=new Token(TokenType.SYMBOL,s.charAt(0));
					System.out.println("simbol"+" "+token.getValue());
					return token;
				}else if(t==TokenType.NUMBER){
					try {
						long l=Long.parseLong(s);
						token=new Token(t, Long.parseLong(s));
						currentIndex++;
						System.out.println("nesto"+" "+token.getValue());
						return token;
					}catch(NumberFormatException ex) {
						throw new LexerException();
					}
				}else {
					token=new Token(t, s);
					currentIndex++;
					System.out.println("nesto"+" "+token.getValue());
					return token;
				}
			}
			switch(t) {
				case NUMBER:
					try {
						Long.parseLong(s+Character.toString(c));
						break;
					}catch(NumberFormatException e) {
						if(Character.isDigit(c))
							throw new LexerException();
						token=new Token(TokenType.NUMBER, Long.parseLong(s));
						System.out.println("NUMBER"+" "+token.getValue());
						return token;
					}
				default:
					if(Character.isDigit(c)) {
						if(!escape) {
							if(s.length()==1 && (!Character.isDigit(s.charAt(0)))) {
								token=new Token(TokenType.SYMBOL, s.charAt(0));
								System.out.println("SYMBO1L"+" "+token.getValue());
								return token;
							}else {
								token=new Token(TokenType.WORD, s);
								System.out.println("WORD1"+" "+token.getValue());
								return token;
							}
						}else {
							escape=false;
							break;
						}
					}else if(c=='\n'||c=='\r'||c=='\t'|| c==' ') {
						if(s.length()==1 && (!Character.isDigit(s.charAt(0)))) {
							token=new Token(TokenType.SYMBOL, s.charAt(0));
							System.out.println("SYMBOL2"+" "+token.getValue());
							return token;
						}else {
							token=new Token(TokenType.WORD, s);
							System.out.println("WORD2"+" "+token.getValue());
							return token;
						}
					}else if(Character.isLetter(c)) {
						if(escape)
							throw new LexerException();
						break;
					}
					else {
						if(s.length()==1 && (!Character.isDigit(s.charAt(0)))) {
							if(c=='#') {
								this.setState(LexerState.EXTENDED);
								token=new Token(TokenType.SYMBOL,'#');
								System.out.println("SYMBOL: #");
								return token;
							}
							token=new Token(TokenType.SYMBOL, s.charAt(0));
							System.out.println("SYMBOL3"+" "+token.getValue());
							return token;
						}else {
							token=new Token(TokenType.WORD, s);
							System.out.println("WORD3"+" "+token.getValue());
							return token;
						}
					}
			}
		}
	}
	public Token getToken() {
		return token;
	}
	
}
