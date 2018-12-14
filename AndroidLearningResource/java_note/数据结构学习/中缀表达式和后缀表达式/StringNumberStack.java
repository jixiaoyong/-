package shujujiegou;

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: www.jixiaoyong.github.io
 * date: 2018/12/12
 * description: 保存运算数字的栈
 */
public class StringNumberStack {

    private final int stepSize = 10;
    private StringNumber[] items = new StringNumber[stepSize];
    private int TOP = -1;

    public void insert(StringNumber c) {
        if (TOP >= items.length - 1) {
            // 拓展数组
            StringNumber[] newArray = new StringNumber[items.length + stepSize];
            for (int i = 0; i < items.length; i++) {
                newArray[i] = items[i];
            }
            items = newArray;
        }
        items[++TOP] = c;
    }

    public StringNumber pop() throws Exception {
        if (TOP < 0) {
            throw new Exception("Strcak is empty");
        }
        return items[TOP--];
    }
    
    public StringNumber peek() throws Exception{
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
            stringBuilder.append(items[i].value());
        }
        return stringBuilder.toString();
    }
}