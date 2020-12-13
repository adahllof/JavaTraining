abstract class Shape {

    abstract double getPerimeter();

    abstract double getArea();
}
class Triangle extends Shape {
    double side1;
    double side2;
    double side3;

    public Triangle(double side1, double side2, double side3) {
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }
    public double getPerimeter() {
        return side1 + side2 + side3;
    }
    public double getArea() {
        double s = getPerimeter() / 2;
        return Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));
    }
}
class Rectangle extends Shape {
    double side1;
    double side2;

    public Rectangle(double side1, double side2) {
        this.side1 = side1;
        this.side2 = side2;
    }
    public double getPerimeter() {
        return 2.0 * (side1 + side2);
    }
    public double getArea() {
        return side1 * side2;
    }
}
class Circle extends Shape {
    double radius;

    public Circle(double radius) {
        this.radius = radius;
    }
    public double getPerimeter() {
        return 2.0 * Math.PI * radius;
    }
    public double getArea() {
        return Math.PI * radius * radius;
    }
}
