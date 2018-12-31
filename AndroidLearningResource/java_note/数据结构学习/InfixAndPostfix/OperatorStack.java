package shujujiegou.InfixAndPostfix;

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2018/12/12
 * description: 保存运算符号的栈
 */
public class OperatorStack {

    private final int stepSize = 10;
    private char[] items = new char[stepSize];
    private int TOP = -1;

    public void insert(char c) {
        if (TOP >= items.length - 1) {
            // 拓展数组
            char[] newArray = new char[items.length + stepSize];
            for (int i = 0; i < items.length; i++) {
                newArray[i] = items[i];
            }
            items = newArray;
        }
        items[++TOP] = c;
    }

    public char pop() throws Exception {
        if (TOP < 0) {
            throw new Exception("Strcak is empty");
        }
        return items[TOP--];
    }
    
    public char peek() throws Exception{
        if (TOP < 0) {
            throw new Exception("Strcak is empty");
        }
        return items[TOP];
    }

    public int size() {
        return TOP + 1;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < TOP + 1; i++) {
            stringBuilder.append(items[i]);
        }
        return stringBuilder.toString();
    }
}