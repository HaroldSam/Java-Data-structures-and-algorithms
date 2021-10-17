public class Test {
  public  Test()
    {
        Integer[] values1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Integer[] values2 = {3, 5, 7, 9,1,110};
        SinglyList<Integer> list1 = new SinglyList<Integer>(values1);
        SinglyList<Integer> list2 = new SinglyList<Integer>(values2);
        System.out.println((list1.intersection(list2)).toString());
    }
    public static void main(String[] args) {

       new Test();

    }
}
