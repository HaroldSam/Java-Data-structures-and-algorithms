public abstract class AbstractGraph<T> implements Graph<T> {
    protected static final int MAX_WEIGHT = 0x0000fffff;
    protected SeqList<T> vertexlist;

    public AbstractGraph() {
        this.vertexlist = new SeqList<T>();
    }

    public AbstractGraph(int length) {
        this.vertexlist = new SeqList<T>(length);
    }

    public int vertexCount() {
        return this.vertexlist.size();
    }

    public String toString() {
        return "顶点集合：" + this.vertexlist.toString() + "\n";
    }

    public T get(int i) {
        return this.vertexlist.get(i);
    }

    public void set(int i, T x) {
        this.vertexlist.set(i, x);
    }

    public int search(T key) {
        return this.vertexlist.search(key);
    }
}




