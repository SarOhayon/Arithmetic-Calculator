package Q1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class calculator {

	private static final Pattern WHITESPACE = Pattern.compile("^(\\s+)");
	private static final Pattern NUMBER = Pattern.compile("^(\\d+)");
	private static final Pattern OPERATOR = Pattern.compile("^(<=|>=|==|!=|\\(|\\)|\\+|\\-|\\*|/|\\^|>|<)");

	private static final Pattern[] PATTERNS = { WHITESPACE, NUMBER, OPERATOR };

	private static String nextToken(String input) {
		for (Pattern pattern : PATTERNS) {
			Matcher m = pattern.matcher(input);
			if (m.find()) {
				return m.group(1);
			}
		}
		return "";
	}

	static ArrayStack<Double> values = new ArrayStack<Double>(10);
	static ArrayStack<String> operators = new ArrayStack<String>(10);

	public static int precedence(String op) {
		switch (op) {
		case "(":
			return 6;
		case ")":
			return -1;
		case "^":
			return 5;
		case "*":
		case "/":
			return 4;
		case "+":
		case "-":
			return 3;
		case ">":
		case "<":
		case ">=":
		case "<=":
			return 2;
		case "==":
		case "!=":
			return 1;
		case "$":
			return -1;
		default:
			return -1;
		}
	}

	public static double evaluate(double val1, double val2, String op) {

		switch (op) {
		case "^":
			return Math.pow(val1, val2);
		case "*":
			return val1 * val2;
		case "/":
			return val1 / val2;
		case "+":
			return val1 + val2;
		case "-":
			return val1 - val2;
		case ">":
			return val1 > val2 ? 1 : 0;
		case "<":
			return val1 < val2 ? 1 : 0;
		case ">=":
			return val1 >= val2 ? 1 : 0;
		case "<=":
			return val1 <= val2 ? 1 : 0;
		case "==":
			return val1 == val2 ? 1 : 0;
		case "!=":
			return val1 != val2 ? 1 : 0;
		default:
			return -1;
		}
	}

	public static void evaluateStack(String op) {
		int opPrec = precedence(op);
		while (!operators.isEmpty() && precedence(operators.top()) >= opPrec) {
			String operator = operators.pop();
			if (operator.equals("(")) {
				if (op.equals(")")) {
					return;
				}
				operators.push(operator);
				return;
			}
			double first = values.pop();
			double second = values.pop();
			double result = evaluate(second, first, operator);
			values.push(result);
		}
	}

	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
			PrintWriter writer = new PrintWriter("output.txt");
			String line;

			while ((line = reader.readLine()) != null) {

				String temp = line;
				while (temp.length() > 0) {
					String token = nextToken(temp);
					temp = temp.substring(token.length());
					if (token.matches("\\s+")) {
						continue;
					}
					if (token.matches("\\d+")) {
						values.push(Double.parseDouble(token));
					} else if (token.matches("(\\(|\\)|\\+|\\-|\\*|/|\\^|>|<|<=|>=|==|!=)")) {
						evaluateStack(token);
						if (!token.equals(")")) {
							operators.push(token);
						}
					}
				}
				evaluateStack("$");
				writer.println(line + " = " + values.top());
				values = new ArrayStack<Double>(10);
				operators = new ArrayStack<String>(10);
			}
			reader.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
