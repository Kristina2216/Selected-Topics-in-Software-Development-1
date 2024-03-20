package hr.fer.oprpp1.custom.collections.demo;
/**
 * Razred predstavlja implementaciju stoga,
 * a za internu pohranu koristi polje.
 */
import java.util.Arrays;

import hr.fer.oprpp1.custom.collections.ObjectStack;



public class StackDemo {
	public static void main(String[] args) {
		String[] all=args[0].split("\\ ");
		ObjectStack stack=new ObjectStack();
		for(String s:all) {
			try {
				int el=Integer.parseInt(s);
				stack.push(el);
			}catch (NumberFormatException e){
				if(stack.size()<2) throw new RuntimeException("Invalid argument number: too few arguments!");
				int b=(Integer)stack.pop();
				int a=(Integer)stack.pop();
				switch(s) {
				case "+":
					stack.push(a+b);
					break;
				case "-":
					stack.push(a-b);
					break;
				case "/":
					if(b==0) throw new RuntimeException("Can't divide by zero!");
					stack.push(a/b);
					break;
				case "*":
					stack.push(a*b);
					break;
				case "%":
					stack.push(a%b);
					break;
				}
			}
		}
		if(stack.size()!=1) {
			throw new RuntimeException("Invalid argument number: too many arguments!");
		}
		System.out.println("Expression evaluates to "+stack.pop()+".");
	}
}
