package shujujiegou.InfixAndPostfix;

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2018/12/12
 * description: 中缀表达式转化为后缀表达式 CovertInfixToPostfix
 */
public class CovertInfixToPostfix {
    public static void main(String[] args) {

        String math = "1+2*(3+5)-2*3-9/2";
        String[] mathResultStrs = covertInfixToPostfix(math);
        for (String string : mathResultStrs) {
            System.out.print(string + " ");
        }
        System.out.println("\n------------start math------\n");
        float result = operationPostfix(mathResultStrs);
        System.out.println("result is " + result);
    }

    /**
     * 运算后缀表达式
     * @param postfix 后缀表达式
     * @return 后缀表达式结果
     */
    public static float operationPostfix(String[] postfix) {
        float result = 0.0f;
        StringNumberStack numberStack = new StringNumberStack();

        for (String s : postfix) {
            if ("+".equals(s)) {
                try {
                    StringNumber last = numberStack.pop();
                    StringNumber first = numberStack.pop();
                    numberStack.insert(first.add(last));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if ("-".equals(s)) {
                try {
                    StringNumber last = numberStack.pop();
                    StringNumber first = numberStack.pop();
                    numberStack.insert(first.sub(last));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if ("*".equals(s)) {
                try {
                    StringNumber last = numberStack.pop();
                    StringNumber first = numberStack.pop();
                    numberStack.insert(first.multi(last));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if ("/".equals(s)) {
                try {
                    StringNumber last = numberStack.pop();
                    StringNumber first = numberStack.pop();
                    numberStack.insert(first.divide(last));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                numberStack.insert(new StringNumber(s));
            }
        }
        try {
            result = numberStack.pop().value();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 中缀表达式转化为后缀表达式
     * @param infix 中缀表达式 1+2
     * @return 后缀表达式 1 2 +
     */
    public static String[] covertInfixToPostfix(String infix) {
        StringBuilder stringBuilder = new StringBuilder();
        OperatorStack operatorStack = new OperatorStack();
        for (char item : infix.toCharArray()) {
            // 数字
            if (item >= '0' && item <= '9') {
                stringBuilder.append(item + ",");
            } else if (item == '(') {
                operatorStack.insert(item);
            } else if (item == ')') {
                int size = operatorStack.size();
                for (int i = 0; i < size; i++) {
                    try {
                        char pop = operatorStack.pop();
                        if (pop == '(') {
                            break;
                        } else {
                            stringBuilder.append(pop + ",");
                        }
                    } catch (Exception e) {
                        System.out.println("栈为空");
                    }
                }
            } else {
                try {
                    char peek = operatorStack.peek();
                    if (peek == '(') {
                        operatorStack.insert(item);
                    } else if (isPre(item, peek) > 0) {
                        operatorStack.insert(item);
                    } else {
                        int size = operatorStack.size();
                        for (int i = 0; i < size; i++) {
                            try {
                                char pop = operatorStack.peek();
                                if (pop == '(') {
                                    break;
                                } else if (isPre(item, peek) > 0) {
                                    break;
                                } else {
                                    stringBuilder.append(pop + ",");
                                    operatorStack.pop();
                                }
                            } catch (Exception e) {
                                break;
                            }
                        }
                        operatorStack.insert(item);
                    }
                } catch (Exception e) {
                    System.err.println("栈为空");
                    operatorStack.insert(item);
                }
            }
        }

        int size = operatorStack.size();
        for (int i = 0; i < size; i++) {
            try {
                stringBuilder.append(operatorStack.pop() + ",");
            } catch (Exception e) {
                System.out.println("栈为空");
                break;
            }
        }

        String result = stringBuilder.toString();

        return result.split(",");
    }

    /**
     * 计算两个运算符优先级
     * @param first 运算符
     * @param last 运算符
     * @return 1 first优先,0 优先级相同,-1 last优先
     */
    public static int isPre(char first, char last) {
        if ((first == '+' || first == '-')) {
            if (last == '/' || last == '*') {
                return -1;
            } else {
                return 0;
            }
        } else {
            if (last == '/' || last == '*') {
                return 0;
            } else {
                return 1;
            }
        }
    }
}
