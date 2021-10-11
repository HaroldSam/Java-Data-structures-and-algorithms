public class MyString implements Comparable<MyString>,java.io.Serializable
{
    private final char[] value;

    public MyString()
    {
        this.value=new char[0];
    }
    public MyString(java.lang.String s)
    {
        this.value=new char[s.length()];
        for (int i = 0; i < this.value.length; i++) {
            this.value[i]=s.charAt(i);
        }
    }

    public MyString(char[] value,int i,int n)
    {
        if(i>=0&&n>=0&&i+n<=value.length)
        {
            this.value=new char[n];
            for(int j=0;j<n;j++)
                this.value[j]=value[i+j];
        }
        else
            throw new StringIndexOutOfBoundsException("i="+i+",n="+n+",i+n="+(i+n));

    }
    public MyString(char[] value)
    {
        this(value,0,value.length);
    }

    public MyString(MyString s)
    {
        this(s.value);
    }

    public int length()
    {
        return this.value.length;
    }

    public java.lang.String toString()
    {
        return new String(this.value);
    }


    public char charAt(int i)
    {
        if(i>=0&&i<this.value.length)
            return this.value[i];
        throw new StringIndexOutOfBoundsException(i);
    }


    public MyString substring(int begin,int end)
    {
        if(begin==0&&end==this.value.length)
            return this;
        return new MyString(this.value,begin,end-begin);

    }

    public MyString substring(int begin)
    {
        return substring(begin,this.value.length);
    }


    public MyString concat(MyString s)
    {
        if(s==null||s.equals(""))
            s=new MyString(this.value);
        char[] buffer=new char[this.value.length+s.length()];
        int i;
        for (i = 0; i < this.value.length; i++) {
            buffer[i]=this.value[i];
        }
        for (int j = 0; j <s.value.length ; j++) {
            buffer[i+j]=s.value[j];
        }
        return new MyString(buffer);

    }

    public int compareTo(MyString s)
    {
        for (int i = 0; i < this.value.length&&i<s.value.length; i++) {
            if (this.value[i] != s.value[i])
                return this.value[i] - s.value[i];
        }
            return this.value.length-s.value.length;
    }



    public static void main(String[] args) {
        MyString s1=new MyString();
        MyString s2=new MyString("abc");
        char[] letters={'a','b','c','d'};
        MyString s3=new MyString(letters);
        MyString s4=new MyString(s3);
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(s4);

        String s5="abcdef",s6="xyz";
        int i=3;
        String s7=s5.substring(0,i)+s6+s5.substring(i);
        System.out.println(s7);
    }










}
