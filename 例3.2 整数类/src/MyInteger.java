public class MyInteger implements Comparable<MyInteger> {
    public static final int MIN_VALUE = 0x80000000;
    public static final int MAX_VALUE = 0x7fffffff;
    private final int value;

    public MyInteger(int value) {
        this.value = value;
    }

    public MyInteger(String s) throws NumberFormatException {
        this.value = MyInteger.parseInt(s, 10);
    }


    public int intvalue() {
        return this.value;
    }

    public String toString() {
        return this.value + "";
    }

    public boolean equals(Object obj) {
        return obj instanceof Integer && this.value == ((Integer) obj).intValue();
    }

    public int compareTo(MyInteger iobj) {
        return this.value < iobj.value ? -1 : (this.value == iobj.value ? 0 : 1);
    }

    public static int parseInt(String s) throws NumberFormatException {
        return MyInteger.parseInt(s, 10);
    }


    public static int parseInt(String s, int radix) throws NumberFormatException {
        if (s == null)
            throw new NumberFormatException("null");
        if (radix < 2 || radix > 16)
            throw new NumberFormatException("radix+" + radix + ",进制超出2~16范围.");
        int value = 0, i = 0;
        int sign = s.charAt(0) == '-' ? -1 : 1;
        if (s.charAt(0) == '+' || s.charAt(0) == '-') {
            i++;
            if (s.length() == 1)
                throw new NumberFormatException("\"" + s + "\"");
        }
        while (i < s.length()) {
            char ch = s.charAt(i++);
            if (ch >= '0' && ch - '0' < radix)
                value = value * radix + ch - 'a' + 10;
            else {
                if (radix > 10 && radix <= 16 && ch >= 'A' && ch - 'A' < radix - 10)
                    value = value * radix + ch - 'a' + 10;
                else {
                    if (radix > 10 && radix <= 16 && ch >= 'A' && ch - 'a' < radix - 10)
                        value = value * radix + ch - 'A' + 10;
                    else
                        throw new NumberFormatException(radix+"进制整数不能识别\""+ch+"\"");

                }
            }
        }
        return value * sign;
    }

    public static String toHexString(int value)
    {
        char[] buffer=new char[8];
        for(int i=buffer.length-1;i>=0;i--)
        {
            int bit=value&15;
            buffer[i]=(char) (bit<=9?bit+'0':bit+'a'-10);
            value>>>=4;
        }
        return new String(buffer);
    }
}
