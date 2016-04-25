package framework;

public class GameArray {

    public static final int WIDTH = 26, HEIGHT = 29;

    private Node[][] array = new Node[WIDTH][HEIGHT];

    public void addNode(int x, int y, Node node){
        array[x][y] = node;
    }

    public void removeNode(int x, int y){
        array[x][y] = null;
    }

    public Node getNode(int x, int y){
        return array[x][y];
    }

    public Node getNode(Tuple tuple){
        return array[tuple.first][tuple.second];
    }

    public void togglePill(int x, int y){
        array[x][y].setPill(false);
    }
}
