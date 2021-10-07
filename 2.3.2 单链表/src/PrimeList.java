public class PrimeList
{
    private int range;
    private SinglyList<Integer> list;
    public PrimeList(int range)
    {
        if(range<=1)
            throw new java.lang.IllegalArgumentException("range="+range);
        this.range=range;
        this.list=new SinglyList<Integer>();
        this.list.insert(2);
        Node<Integer> rear=this.list.head.next;

        for(int key=3;key<=range;key+=2)
        {
            if(this.isPrime(key))
            {
                rear.next=new Node<Integer>(key,null);
                rear=rear.next;
            }
        }
    }

    public boolean isPrime(int key)
    {
        if(key<=1)
            throw new IllegalArgumentException("key="+key);
        int sqrt=(int)Math.sqrt(key);
        for(Node<Integer> p=this.list.head.next;p!=null&&p.data<=sqrt;p=p.next)
            if(key%p.data==0)
                return false;
            return true;
    }

    public String toString()
    {
        return "2-"+range+"s素数集合 :"+list.toString();
    }


    public static void main(String[] args) {
        System.out.println(new PrimeList(100).toString());
    }
}
