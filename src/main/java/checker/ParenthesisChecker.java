package checker;

import lombok.NonNull;

import java.util.Stack;

public class ParenthesisChecker {
    
    public boolean validate(@NonNull String lispCode) {
        Stack<Character> stack = new Stack<>();

        for(char c: lispCode.toCharArray()){
            if(c == '(') {
                stack.push(c);
            }
            if(c == ')') {
                if(!stack.empty()) {
                    stack.pop();
                }
                else return false;
            }
        }
        return stack.empty();
    }
}
