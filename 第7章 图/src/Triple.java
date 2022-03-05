public class Triple implements Comparable<Triple>, Addible<Triple> {
    int row;
    int column;
    int value;

    public Triple(int row, int column, int value) {
        if (row >= 0 && column >= 0) {
            this.row = row;
            this.column = column;
            this.value = value;
        } else {
            throw new IllegalArgumentException("行、列号不能为负数：row=" + row + "，column=" + column);
        }
    }

    public Triple(Triple tri) {
        this(tri.row, tri.column, tri.value);
    }

    public String toString() {
        return "(" + this.row + "," + this.column + "," + this.value + ")";
    }

    public Triple toSymmetry() {
        return new Triple(this.column, this.row, this.value);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Triple)) {
            return false;
        } else {
            Triple tri = (Triple)obj;
            return this.row == tri.row && this.column == tri.column && this.value == tri.value;
        }
    }

    public int compareTo(Triple tri) {
        if (this.value == tri.value) {
            return 0;
        } else {
            return this.value > tri.value  ? 1 : -1;
        }
    }

    public void add(Triple term) {
        if (this.compareTo(term) == 0) {
            this.value += term.value;
        } else {
            throw new IllegalArgumentException("两项的指数不同，不能相加。");
        }
    }

    public boolean removable() {
        return this.value == 0;
    }
}
