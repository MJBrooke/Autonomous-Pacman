package framework.obj;

import framework.intf.Renderable;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class GameArray implements Renderable {

    public static final int WIDTH = 32, HEIGHT = 32;

    private Node[][] array = new Node[WIDTH][HEIGHT];

    public void addNode(Node node){
        array[node.getX()][node.getY()] = node;
    }

    public Node getNode(int x, int y){
        return array[x][y];
    }

    public Node getNode(Tuple tuple){
        return array[tuple.first][tuple.second];
    }

    @Override
    public void render(@NotNull Graphics2D g) {
        g.setColor(Color.blue);

        for(int x = 0; x <= 27; x++){
            for(int y = 0; y <= 30; y++){
                if(array[x][y].getWall())
                    g.fillRect(x*32, y*32, 32, 32);
            }
        }
    }
}
