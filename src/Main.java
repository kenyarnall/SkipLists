public class Main {

    public static void main(String[] args) {
        SkipList<Integer> ssl = new SkipList<>();

        ssl.add(43);
        ssl.add(12);
        ssl.add(83);
        ssl.add(2);
        ssl.add(41);
        ssl.add(19);

        int x = ssl.remove(2);
        System.out.println(x);

        Integer y = ssl.remove(45);
        System.out.println(y);


    }
}
