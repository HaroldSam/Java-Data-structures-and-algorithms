public class SortedSeqList <T extends Comparable<T>> extends SeqList
{
    public SortedSeqList()
    {
        super();
    }

    public SortedSeqList(int length)
    {
        super(length);
    }

    public SortedSeqList(T[] values)
    {
        super(values.length);
        for (int i = 0; i < values.length; i++)
        {
            this.insert(values[i]);
        }
    }

    public void set(int i,T x)
    {
        throw new UnsupportedOperationException("set(int i,T x)");
    }
    public void insert(int i,T x)
    {
        throw new UnsupportedOperationException("insert(int i,T x)");
    }

    public int insert(T x)
    {
        int i=0;
        if(this.isEmpty()||x.compareTo((T) this.get(this.n-1))>0)
            i=this.n;
        else
            while(i<this.n&&x.compareTo((T) this.get(i))>=0)
                i++;
            super.insert(i,x);
            return i;
    }


    public static void main(String[] args) {
        Integer[] values={70,20,80,30,60};
        SeqList<Integer> list1=new SeqList<Integer>(values);
        SortedSeqList<Integer> slist1=new SortedSeqList<Integer>(values);
        list1.insert(50);
        slist1.insert(50);
        list1.insert(0,10);
        System.out.println("list1="+list1.toString()+"\nslist1="+slist1.toString());
        slist1.insert(0,10);
    }




















}
