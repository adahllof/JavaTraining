class Shape {
    public double area() {
        return 0;
    }
}

class Triangle extends Shape {
    double height;
    double base;

    public double area() {
        return this.base * this.height / 2.0;
    }
}

class Circle extends Shape {
    double radius;

    public double area() {
        return this.radius * this.radius * Math.PI;
    }
}

class Square extends Shape {
    double side;

    public double area() {
        return this.side * this.side;
    }
}

class Rectangle extends Shape {
    double width;
    double height;

    public double area() {
        return this.height * this.width;
    }
}