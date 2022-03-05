public class LinkedMatrix {
    int rows;
    int columns;
    SeqList<SortedSinglyList<Triple>> rowlist;

    public LinkedMatrix(int m, int n) {
        if (m > 0 && n > 0) {
            this.rows = m;
            this.columns = n;
            this.rowlist = new SeqList();

            for(int i = 0; i < m; ++i) {
                this.rowlist.insert(new SortedSinglyList());
            }

        } else {
            throw new IllegalArgumentException("矩阵行列数不能≤0，m=" + m + "，n=" + n);
        }
    }

    public LinkedMatrix(int m) {
        this(m, m);
    }

    public LinkedMatrix(int m, int n, Triple[] tris) {
        this(m, n);

        for(int i = 0; i < tris.length; ++i) {
            this.set(tris[i]);
        }

    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public int get(int i, int j)
    {
        if (i >= 0 && i < this.rows && j >= 0 && j < this.columns) {
            Node<Triple> find = ((SortedSinglyList)this.rowlist.get(i)).search(new Triple(i, j, 0));
            return find != null ? ((Triple)find.data).value : 0;
        } else {
            throw new IndexOutOfBoundsException("i=" + i + "，j=" + j);
        }
    }

    public void set(int i, int j, int x) {
        if (i >= 0 && i < this.rows && j >= 0 && j < this.columns) {
            SortedSinglyList<Triple> link = (SortedSinglyList)this.rowlist.get(i);
            if (x == 0) {
                link.remove(new Triple(i, j, 0));
            } else {
                Triple tri = new Triple(i, j, x);
                Node<Triple> find = link.search(tri);
                if (find != null) {
                    ((Triple)find.data).value = x;
                } else {
                    link.insert(tri);
                }
            }

        } else {
            throw new IndexOutOfBoundsException("i=" + i + "，j=" + j);
        }
    }

    public void set(Triple tri) {
        this.set(tri.row, tri.column, tri.value);
    }

    public String toString() {
        String str = "";

        for(int i = 0; i < this.rowlist.size(); ++i) {
            str = str + i + " -> " + ((SortedSinglyList)this.rowlist.get(i)).toString() + "\n";
        }

        return str;
    }

    public void printMatrix() {
        System.out.println("矩阵" + this.getClass().getName() + "（" + this.rows + "×" + this.columns + "）：");

        for(int i = 0; i < this.rows; ++i) {
            Node<Triple> p = ((SortedSinglyList)this.rowlist.get(i)).head.next;

            for(int j = 0; j < this.columns; ++j) {
                if (p != null && j == ((Triple)p.data).column) {
                    System.out.print(String.format("%4d", ((Triple)p.data).value));
                    p = p.next;
                } else {
                    System.out.print(String.format("%4d", 0));
                }
            }

            System.out.println();
        }

    }

    public void printMatrix2() {
        System.out.println("矩阵" + this.getClass().getName() + "（" + this.rows + "×" + this.columns + "）：");

        for(int i = 0; i < this.rows; ++i) {
            for(int j = 0; j < this.columns; ++j) {
                System.out.print(String.format("%4d", this.get(i, j)));
            }

            System.out.println();
        }

    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof LinkedMatrix)) {
            return false;
        } else {
            LinkedMatrix mat = (LinkedMatrix)obj;
            return this.rows == mat.rows && this.columns == mat.columns && this.rowlist.equals(mat.rowlist);
        }
    }

    public void setRowsColumns(int m, int n) {
        if (m > 0 && n > 0) {
            if (m > this.rows) {
                for(int i = this.rows; i < m; ++i) {
                    this.rowlist.insert(new PolySinglyList());
                }
            }

            this.rows = m;
            this.columns = n;
        } else {
            throw new IllegalArgumentException("矩阵行列数不能≤0，m=" + m + "，n=" + n);
        }
    }

    public void addAll(LinkedMatrix mat) {
        if (this.rows == mat.rows && this.columns == mat.columns) {
            for(int i = 0; i < this.rows; ++i) {
                ((SortedSinglyList)this.rowlist.get(i)).addAll((SinglyList)mat.rowlist.get(i));
            }

        } else {
            throw new IllegalArgumentException("两个矩阵阶数不同，不能相加");
        }
    }

    public LinkedMatrix(LinkedMatrix mat) {
        this(mat.rows, mat.columns);

        for(int i = 0; i < this.rows; ++i) {
            SortedSinglyList<Triple> link = new SortedSinglyList((SortedSinglyList)mat.rowlist.get(i));

            for(Node p = link.head.next; p != null; p = p.next) {
                p.data = new Triple((Triple)p.data);
            }

            this.rowlist.set(i, link);
        }

    }

    public LinkedMatrix union(LinkedMatrix mat) {
        LinkedMatrix matc = new LinkedMatrix(this);
        matc.addAll(mat);
        return matc;
    }

    public LinkedMatrix transpose() {
        LinkedMatrix trans = new LinkedMatrix(this.columns, this.rows);

        for(int i = 0; i < this.rows; ++i) {
            for(Node p = ((SortedSinglyList)this.rowlist.get(i)).head.next; p != null; p = p.next) {
                trans.set(((Triple)p.data).toSymmetry());
            }
        }

        return trans;
    }
}