class Cat {

    private String name;
    private int age;
    private static int count;

    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
        count++;
        if (count > 5) {
            System.out.println("You have too many cats");
        }
    }

    public static int getNumberOfCats() {
        return count;
    }
}