public interface Graph<T>
{
    int vertexCount();
    T get(int i);
    void set(int i,T x);
    void insert(int i,int j,int w);
    T remove(int i);
    void remove(int i,int j);
    int search(T key);
    int weight(int i,int j);

}
