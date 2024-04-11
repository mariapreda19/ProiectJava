package src;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


public class Enemy {

    private static final int DEFAULT_SPEED = 1;
    private static final int ENEMY_CHASE_RANGE = 5;
    private int healthPoints;
    private int speed;

    private int positionX;
    private int positionY;

    public Enemy(int healthPoints, int speed, int positionX, int positionY) {
        this.healthPoints = healthPoints;
        this.speed = speed;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    // Getters
    public int getHealthPoints() {
        return healthPoints;
    }

    public int getSpeed() {
        return speed;
    }

    public int[] getPosition() {
        return new int[]{positionX, positionY};
    }

    // Setters
    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setPosition(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }


    // Interaction methods
    public void catchPlayer(Player player) {
        int[] playerPosition = {player.getPositionX(), player.getPositionY()};
        if (playerPosition[0] == positionX && playerPosition[1] == positionY) {
            player.setHealthPoints(0);
        }
    }

    public void beingShot(Player player) {
        this.healthPoints -= player.getAttackPower();
    }


    public static void drawEnemies(Graphics g, Enemy enemy) {
        int cellSize = 40;
        try {
            ImageIcon icon = new ImageIcon(Player.class.getResource("../img/enemy1.png"));
            Image image = icon.getImage();
            g.drawImage(image, enemy.getPosition()[0] * cellSize, enemy.getPosition()[1] * cellSize, cellSize, cellSize, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    // Method to move the enemy using A*
    public void move(int playerX, int playerY, int[][] mapLayout, GameMap map) {
        Node startNode = new Node(positionX, positionY);
        Node targetNode = new Node(playerX, playerY);

        List<Node> path = findPath(startNode, targetNode, mapLayout, map);

        if (!path.isEmpty()) {
            Node nextNode = path.get(0);
            positionX = nextNode.x;
            positionY = nextNode.y;
        }
    }


    private List<Node> findPath(Node start, Node target, int[][] mapLayout, GameMap map) {

        List<Node> openList = new ArrayList<>();
        List<Node> closedList = new ArrayList<>();

        openList.add(start);

        while (!openList.isEmpty()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Node currentNode = openList.get(0);
            for (int i = 1; i < openList.size(); i++) {
                if (openList.get(i).getF() < currentNode.getF() ||
                        (openList.get(i).getF() == currentNode.getF() && openList.get(i).hCost < currentNode.hCost)) {
                    currentNode = openList.get(i);
                }
            }

            openList.remove(currentNode);
            closedList.add(currentNode);

            if (currentNode.equals(target)) {
                return retracePath(start, currentNode);
            }

            List<Node> neighbors = getNeighbors(currentNode, mapLayout, map);
            for (Node neighbor : neighbors) {
                if (!closedList.contains(neighbor)) {
                    int newCostToNeighbor = currentNode.gCost + getDistance(currentNode, neighbor);
                    if (newCostToNeighbor < neighbor.gCost || !openList.contains(neighbor)) {
                        neighbor.gCost = newCostToNeighbor;
                        neighbor.hCost = getDistance(neighbor, target);
                        neighbor.parent = currentNode;

                        if (!openList.contains(neighbor)) {
                            openList.add(neighbor);
                        }
                    }
                }
            }
        }

        return new ArrayList<>();
    }

    private List<Node> retracePath(Node start, Node end) {
        List<Node> path = new ArrayList<>();
        Node currentNode = end;
        while (!currentNode.equals(start)) {
            path.add(currentNode);
            currentNode = currentNode.parent;
        }
        Collections.reverse(path);
        return path;
    }

    private List<Node> getNeighbors(Node node, int[][] mapLayout, GameMap map) {
        List<Node> neighbors = new ArrayList<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int newX = node.x + dir[0];
            int newY = node.y + dir[1];

            if (map.isWalkable(newX, newY)) {
                neighbors.add(new Node(newX, newY));
            }
        }

        return neighbors;
    }


    private int getDistance(Node nodeA, Node nodeB) {
        return Math.abs(nodeA.x - nodeB.x) + Math.abs(nodeA.y - nodeB.y);
    }

    private class Node {
        int x;
        int y;
        int gCost;
        int hCost;
        Node parent;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int getF() {
            return gCost + hCost;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Node node = (Node) obj;
            return x == node.x && y == node.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
