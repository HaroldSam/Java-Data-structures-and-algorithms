public class AdjListGraph<T> extends AbstractGraph<T> {
    protected LinkedMatrix linkmat;

    public AdjListGraph(int n) {
        super(n);
        this.linkmat = new LinkedMatrix(n);
    }

    public AdjListGraph(T[] vertices) {
        this(vertices.length);

        for (int i = 0; i < vertices.length; ++i) {
            this.insertVertex(vertices[i]);
        }
    }

    public AdjListGraph(T[] vertices, Triple[] edges) {
        this(vertices);
        for (int i = 0; i < edges.length; i++) {
            this.insertEdge(edges[i]);
        }

    }
    public int vertexCount(){
        return this.vertexlist.size();
    }
    @Override
    public void insert(int i, int j, int w) {
        insertEdge(i, j, w);
    }

    @Override
    public T remove(int i) {
        remove(i);
        return null;
    }

    @Override
    public void remove(int i, int j) {
        removeEdge(i, j);
    }


    public int insertVertex(T x) {
        int i = this.vertexlist.insert(x);
        if (i >= this.linkmat.getRows()) {
            this.linkmat.setRowsColumns(i + 1, i + 1);
        }

        return i;
    }

    public void insertEdge(int i, int j, int weight) {
        if (i == j) {
            throw new IllegalArgumentException("不能插入自身环，i=" + i + "，j=" + j);
        } else {
            if (weight < 0 || weight >= 99999) {
                weight = 0;
            }

            this.linkmat.set(i, j, weight);
        }
    }

    public void insertEdge(Triple edge) {
        this.insertEdge(edge.row, edge.column, edge.value);
    }

    public void removeEdge(int i, int j) {
        if (i != j) {
            this.linkmat.set(new Triple(i, j, 0));
        }

    }

    public void removeEdge(Triple edge) {
        this.removeEdge(edge.row, edge.column);
    }

    public void removeVertex(int i) {
        int n = this.vertexCount();
        if (i >= 0 && i < n) {
            SortedSinglyList<Triple> link = (SortedSinglyList<Triple>) this.linkmat.rowlist.get(i);

            for (Node<Triple> p = link.head.next; p != null; p = p.next) {
                this.removeEdge((p.data).toSymmetry());
            }

            --n;
            this.linkmat.rowlist.remove(i);
            this.linkmat.setRowsColumns(n, n);

            for (int j = 0; j < n; ++j) {
                link = (SortedSinglyList<Triple>) this.linkmat.rowlist.get(j);

                for (Node<Triple> p = link.head.next; p != null; p = p.next) {
                    if ((p.data).row > i) {
                        --(p.data).row;
                    }

                    if ((p.data).column > i) {
                        --(p.data).column;
                    }
                }
            }

            this.vertexlist.remove(i);
        } else {
            throw new IndexOutOfBoundsException("i=" + i);
        }
    }

    public int weight(int i, int j) {
        if (i == j) {
            return 0;
        } else {
            int weight = this.linkmat.get(i, j);
            return weight != 0 ? weight : 99999;
        }
    }
    public String toString()
    {
        return super.toString()+"出边表:\n"+this.linkmat.toString();
    }

    protected int next(int i, int j) {
        int n = this.vertexCount();
        if (i >= 0 && i < n && j >= -1 && j < n && i != j) {
            SortedSinglyList<Triple> link = (SortedSinglyList<Triple>) this.linkmat.rowlist.get(i);
            Node<Triple> find = link.head.next;
            if (j == -1) {
                return find != null ? ((Triple) find.data).column : -1;
            }

            find = link.search(new Triple(i, j, 0));
            if (find != null) {
                find = find.next;
                if (find != null) {
                    return ((Triple) find.data).column;
                }
            }
        }

        return -1;
    }

    public Triple minWeightEgde() //返回带权图中权值最小的边
    {
        AbstractGraph<T> graph=(AbstractGraph<T>)this;
        Triple MinValue = new Triple(0, 0, MAX_WEIGHT);
        for (int i = 0; i < linkmat.rows; i++)
            for (int j = 0; j < linkmat.columns; j++)
                if (MinValue.value>linkmat.get(i,j) && linkmat.get(i, j) != 0) {
                    MinValue.row = i;
                    MinValue.column = j;
                    MinValue.value = linkmat.get(i, j);
                }
        return MinValue;
    }
    public AdjListGraph(AdjListGraph<T> graph) //拷贝构造方法，深拷贝
    {
        this(graph.vertexCount());
        this.linkmat = new LinkedMatrix(graph.linkmat.rows, graph.linkmat.columns);
        for (int i = 0; i < this.linkmat.rows; i++)
            for (int j = 0; j < this.linkmat.columns; j++)
                this.linkmat.set(new Triple(i, j, graph.linkmat.get(i, j)));
        this.vertexlist = new SeqList<T>((T[]) graph.vertexlist.element);
    }

    public boolean equals(Object obj) //比较两个图是否相等，忽略顶点次序
    {
        if (obj == this)
            return true;
        else if (!(obj instanceof AdjListGraph))
        {
            System.out.println("两者不相等");
            return false;
        }
        AdjListGraph<T> graph = (AdjListGraph<T>) obj;
        if(this.linkmat.getColumns()==graph.linkmat.getColumns()) {//new
            for (int i = 0; i < this.linkmat.rows; i++)
                for (int j = 0; j < this.linkmat.columns; j++) {
                    if (this.linkmat.get(i, j) != MAX_WEIGHT) {
                        if(graph.linkmat.get(i,j)!=this.linkmat.get(i,j))
                            return false;
                        }
                    }
                }
        return true;
    }



    public boolean isPath(SinglyList<T> pathlink)//判断由pathlink存储的顶点序列是否是图的一条路径
    {
        Node<T> p = pathlink.head.next;
        if(this.vertexlist.search(p.data) == -1)
            return false;
        int index;
        for(int i=0;i<pathlink.size()-1;i++)
        {
            index = this.vertexlist.search(p.data);
            T data = p.data;
            p = p.next;

            if(this.vertexlist.search(p.data) == -1)
            {
                System.out.println("不存在点,不是路径");
                return false;
            }

            if(this.linkmat.get(index,this.vertexlist.search(p.data))==0)
            {
                System.out.println("这两点之间没有边： " + data.toString() + " 和 " + p.data.toString());
                return false;
            }
        }
        return true;
    }

    public boolean isCyclePath(int[] vertexs)//判断是否为简单回路（每个顶点仅可被访问一次）
    {
        boolean[] Visited = new boolean[this.vertexCount()];
        int index = vertexs[0];
        for(int i = 0; i < vertexs.length; i++)
            if (vertexs[i] > this.vertexCount() - 1)
            {
                System.out.println("图中不存在该点");
                return false;
            }

        for(int i=1;i<vertexs.length;i++)
        {
            if(this.linkmat.get(index,vertexs[i]) == 0)
            {
                System.out.println("两点之间没有边： " + index + " 与 " + vertexs[i]);
                return false;
            }
            index = vertexs[i];
        }

        for(int i = 0;i<Visited.length;i++)
        {
            Visited[vertexs[i]]=true;
            if(vertexs[0]!=vertexs[vertexs.length-1])
            {
                System.out.println("不是回路");
                return false;
            }
        }

        return true;
    }

    public boolean isSubgraph(AbstractGraph<T> graph)//判断graph是否this的子图
    {

        for(int i=0;i<graph.vertexCount();i++){
            if(this.vertexlist.search((T)graph.vertexlist.element[i]) == -1)
            {
                System.out.println("graph中的顶点集不在vertexlist中" );
                return false;
            }
        }
        AdjListGraph<T>graph1=(AdjListGraph<T>) graph;
        for (int i = 0; i < graph1.linkmat.getRows(); i++) {
            for (int j = 0; j < graph1.linkmat.getColumns(); j++) {
                if(graph1.linkmat.get(i,j)!=0)
                {
                    if(this.linkmat.get(i,j)!=0||this.linkmat.get(i,j)!=graph1.linkmat.get(i,j))
                    {
                        System.out.println("graph的边集不在this中");
                        return false;
                    }

                }
            }
        }

        return true;
    }
    public boolean isSpanSubgraph(AbstractGraph<T> graph)//判断graph是否this的生成子图
    {
        if(isSubgraph(graph) && graph.vertexCount() == this.vertexCount()){//判断顶点集是否相同
            for(int i=0;i<graph.vertexCount();i++)
                if(this.vertexlist.search((T)graph.vertexlist.element[i]) == -1)
                    return false;
            return true;
        }
        return false;
    }
    public static void main(String[] args) {
        String[] vertices = new String[]{"A", "B", "C", "D", "E"};

        Triple[] edges = new Triple[]{new Triple(0, 1, 10), new Triple(0, 3, 30), new Triple(0, 4, 99), new Triple(1, 2, 50), new Triple(1, 3, 40), new Triple(2, 4, 10), new Triple(3, 2, 20), new Triple(3, 4, 60), new Triple(4,0,30)};
        AdjListGraph<String> graph = new AdjListGraph(vertices, edges);
        System.out.println(graph.toString());
        //Triple minWeightEgde()
        System.out.println("test1");
        System.out.println(graph.minWeightEgde().toString());

        //AdjListGraph(AdjListGraph<T> graph)
        System.out.println("test2");
        AdjListGraph<String> deep = new AdjListGraph<String>(graph);
        for (int i = 0; i < deep.vertexCount(); i++) {
            System.out.print(deep.vertexlist.element[i]);
        }


        //boolean equals(Object obj)
        AdjListGraph<String> graph_equal = new AdjListGraph<String>(graph);
        System.out.println("test3");
        System.out.println(graph.equals(graph_equal));


        //boolean isPath(SinglyList<T> pathlink)
        System.out.println("test4");
        SinglyList<String> path_true = new SinglyList<String>(new String[]{"A","B","C"});
        SinglyList<String> path_false = new SinglyList<String>(new String[]{"A","C","B"});
        System.out.println(graph.isPath(path_true));
        System.out.println(graph.isPath(path_false));

        //boolean isCyclePath(int[] vertexs)
        System.out.println("test5");
        int[] Cirpath_true = new int[]{0,1,3,4,0};
        int[] Cirpath_false = new int[]{0,1,3,4};
        System.out.println(graph.isCyclePath(Cirpath_true));
        System.out.println(graph.isCyclePath(Cirpath_false));

        //boolean isSubgraph(AbstractGraph<T> graph)
        System.out.println("test6");
        AdjListGraph<String> subgraph_true = new AdjListGraph<String>(graph);
        AdjListGraph<String> subgraph_false = new AdjListGraph<String>(graph);
        subgraph_true.removeVertex(4);
        subgraph_false.insertVertex("G");
        System.out.println(graph.isSubgraph(subgraph_true));
        System.out.println(graph.isSubgraph(subgraph_false));

        //boolean isSpanSubgraph(AbstractGraph<T> graph)
        System.out.println("test7");
        AdjListGraph<String> Spansubgraph_true = new AdjListGraph<String>(graph);
        AdjListGraph<String> Spansubgraph_false = new AdjListGraph<String>(graph);
        Spansubgraph_false.removeEdge(2,4);
        Spansubgraph_false.removeEdge(3,4);
        Spansubgraph_false.removeVertex(4);
        Spansubgraph_true.removeEdge(new Triple(4,0,30));
        System.out.println(graph.isSpanSubgraph(Spansubgraph_false));
        System.out.println(graph.isSpanSubgraph(Spansubgraph_true));
    }


}

