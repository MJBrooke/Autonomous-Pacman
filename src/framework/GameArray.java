package framework;

public class GameArray{

    //TODO - add an extra 2 to each, to create walls on either sides?
    public static final int WIDTH = 26, HEIGHT = 29;

    private Node[][] array = new Node[WIDTH][HEIGHT];

    public void addNode(Node node){
        array[node.getX()][node.getY()] = node;
    }

    public void addNode(int x, int y, Node node){
        array[x][y] = node;
    }

    public Node getNode(int x, int y){
        return array[x][y];
    }

    public Node getNode(Tuple tuple){
        return array[tuple.first][tuple.second];
    }

    public void togglePill(int x, int y){
        array[x][y].setWall(false);
    }
}
