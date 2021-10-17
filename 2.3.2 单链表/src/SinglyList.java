public class SinglyList<T> extends Object {
    public Node<T> head;

    public SinglyList() {
        this.head = new Node<T>();
    }

    public SinglyList(T[] values) {
        this();
        Node<T> rear = this.head;
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null) {
                rear.next = new Node<T>(values[i], null);
                rear = rear.next;
            }
        }
    }

    public boolean isEmpty() {
        return this.head.next == null;
    }

    public T get(int i) {
        Node<T> p = this.head.next;
        for (int j = 0; p != null && j < i; j++)
            p = p.next;
        return (i >= 0 && p != null) ? p.data : null;
    }
    public int size()                               //***
    {
        int i = 0;
        for (int j = 0; head != null; head = head.next) {
            j++;
            i = j - 1;
        }
        return i;
    }

    public String toString() {
        String str = this.getClass().getName() + "(";
        for (Node<T> p = this.head.next; p != null; p = p.next)
            str += p.data.toString() + (p.next != null ? "," : "");
        return str + ")";
    }

    public Node<T> insert(int i, T x) {
        if (x == null)
            return null;
        Node<T> front = this.head;
        for (int j = 0; front.next != null && j < i; j++)
            front = front.next;
        front.next = new Node<T>(x, front.next);
        return front.next;
    }

    public Node<T> insert(T x) {
        return insert(Integer.MAX_VALUE, x);
    }

    public T remove(int i) {
        Node<T> front = this.head;
        for (int j = 0; front.next != null && j < i; j++)
            front = front.next;
        if (i >= 0 && front.next != null) {
            T x = front.next.data;
            front.next = front.next.next;
            return x;
        }
        return null;
    }

    public void clear() {
        this.head.next = null;
    }

    //将list单链表逆转，将其所有结点的next指向其前驱结点
    public void reverse(SinglyList<T> list) {
        Node<T> front = list.head;
        Node<T> p = front.next;
        Node<T> succ = p.next;
        for (front = front.next; p != null; ) {
            p.next = front;
            p = succ;
        }
    }

    public SinglyList(SinglyList values) {
        this();
        Node<T> rear = this.head;
        for (Node<T> p = values.head.next; p != null; p = p.next) {
            if (p.data != null) {
                rear.next = new Node<>(p.data, null);
                rear = rear.next;
            }
        }
    }


    public SinglyList<T> intersection(SinglyList<T> list)//返回交集,不直接在this上修改，result深拷贝this ，修改后返回result
    {
        SinglyList result = new SinglyList(this);
        SinglyList temp = new SinglyList();
        Node<T> P = temp.head;
        for (Node<T> M = result.head.next; M != null; M = M.next) {
            for (Node<T> N = list.head.next; N != null; N = N.next) {
                if (M.data == N.data) {
                    P.next = new Node<T>(M.data, null);
                    P = P.next;
                }
            }
        }
        result.clear();
        result = new SinglyList(temp);
        return result;
    }


    public void main(String[] args) {
        Integer[] values1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Integer[] values2 = {3, 5, 7, 9};
        SinglyList<Integer> list1 = new SinglyList<Integer>(values1);
        SinglyList<Integer> list2 = new SinglyList<Integer>(values2);
        System.out.println((list1.intersection(list2)).toString());

    }
}

