public class PolySinglyList<T extends Comparable<? super T> & Addible<T>> extends SortedSinglyList<T> {
    public PolySinglyList() {
    }

    public PolySinglyList(T[] terms) {
        super(terms);
    }

    public PolySinglyList(PolySinglyList<T> list) {
        super(list);
    }

    public void addAll(PolySinglyList<T> list) {
        Node<T> front = this.head;
        Node<T> p = front.next;
        Node q = list.head.next;

        while(p != null && q != null) {
            if (((Comparable)p.data).compareTo(q.data) == 0) {
                ((Addible)((Addible)p.data)).add((Comparable)q.data);
                if (((Addible)((Addible)p.data)).removable()) {
                    front.next = p.next;
                    p = front.next;
                } else {
                    front = p;
                    p = p.next;
                }

                q = q.next;
            } else if (((Comparable)p.data).compareTo(q.data) < 0) {
                front = p;
                p = p.next;
            } else {
                front.next = new Node((Comparable)q.data, p);
                q = q.next;
            }
        }

        while(q != null) {
            front.next = new Node((Comparable)q.data, (Node)null);
            front = front.next;
            q = q.next;
        }

    }
}