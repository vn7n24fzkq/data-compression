package quadtree;


import java.awt.*;

public class QuadTree {
    private ImageMeasure measure;
    private double threshold;
    public Node root;
    public Color defaultValue;

    public QuadTree(Color data[][], ImageMeasure measure, double threshold) {
        this(data, measure, threshold, null);
    }

    public QuadTree(Color data[][], ImageMeasure measure, double threshold, Color defaultValue) {
        this.measure = measure;
        this.threshold = threshold;
        this.defaultValue = defaultValue;
        root = new Node(data, 0, 0, data.length, data[0].length);
    }

    public Color get(int i, int j) {
        return root.get(i, j);
    }

    public int getWidth() {
        return root.width;
    }

    public int getHeight() {
        return root.height;
    }

    public class Node {

        public Color value;
        public Node children[];
        private int x, y, width, height;

        public Node(Color data[][], int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            if ((width == 1) || (height == 1) || (measure.measureDetail(data, x, y, width, height) <= threshold)) {
                value = measure.approximate(data, x, y, width, height);
            } else {
                children = new Node[4];

                children[0] = new Node(data, x, y, width / 2, height / 2);
                children[1] = new Node(data, x + width / 2, y, width - width / 2, height / 2);
                children[2] = new Node(data, x, y + height / 2, width / 2, height - height / 2);
                children[3] = new Node(data, x + width / 2, y + height / 2, width - width / 2, height - height / 2);
            }

        }

        public Color get(int i, int j) {
            if (value == null) {//this is not a leaf

                if (i < x + width / 2) {
                    if (j < y + height / 2) {
                        return children[0].get(i, j);
                    } else {
                        return children[2].get(i, j);
                    }
                } else {
                    if (j < y + height / 2) {
                        return children[1].get(i, j);
                    } else {
                        return children[3].get(i, j);
                    }
                }
            } else {
                if (((i == x) || (j == y)) && (defaultValue != null))
                    return defaultValue;
                else
                    return value;
            }
        }
    }

}
