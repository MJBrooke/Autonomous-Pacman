package framework.obj;

import framework.intf.Renderable;
import framework.search.AStarSearch;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.LinkedList;

public class GameArray implements Renderable {

    public static final int WIDTH = 32, HEIGHT = 32;
    public static final int WORLD_WIDTH = 27, WORLD_HEIGHT = 30;

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

    public void clearValues(){
        for(int i = 1; i <= WORLD_WIDTH; i++) {
            for (int j = 1; j <= WORLD_HEIGHT; j++) {
                Node node = array[i][j];
                node.setParentNode(null);
                node.setCostToGetHereSoFarG(0F);
                node.setDistanceToGoalH(0F);
                node.setNodeCostF(0F);
            }
        }
    }

    private LinkedList<Node> getListOfUneatenPills(){

        LinkedList<Node> listOfNodes = new LinkedList<>();

        for(int i = 1; i <= WORLD_WIDTH; i++) {
            for (int j = 1; j <= WORLD_HEIGHT; j++) {
                Node newNode = array[i][j];

                if(newNode.getPill() != null && !newNode.getWall()){
                    listOfNodes.add(newNode);
                }
            }
        }

        return listOfNodes;
    }

    public Tuple getClosestPillLocation(int x, int y){

        LinkedList<Node> listOfNodes = getListOfUneatenPills();
        int closestX = 0, closestY = 0;

        if(!listOfNodes.isEmpty()) {
            Node currentClosestNode = listOfNodes.remove();
            Node currentLocation = array[x][y];

            for (Node node : listOfNodes) {

                if (AStarSearch.getDistanceBetweenNodes(currentLocation, currentClosestNode)
                        >= AStarSearch.getDistanceBetweenNodes(currentLocation, node)) {
                    currentClosestNode = node;
                }

            }

            closestX = currentClosestNode.getX();
            closestY = currentClosestNode.getY();
        }

        return new Tuple(closestX, closestY);
    }

    @Override
    public void render(@NotNull Graphics2D g) {
        for(int x = 0; x <= WORLD_WIDTH; x++){
            for(int y = 0; y <= WORLD_HEIGHT; y++){

                Node node = array[x][y];

                if(node.getWall()) {
                    g.setColor(Color.blue);
                    g.fillRect(x * 32, y * 32, 32, 32);
                }
                else {
                    if(node.getPill() != null){
                        node.getPill().render(g);
                    }
                }
            }
        }
    }
}
