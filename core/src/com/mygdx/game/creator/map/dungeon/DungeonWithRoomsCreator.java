package com.mygdx.game.creator.map.dungeon;

import com.mygdx.game.Config;
import com.mygdx.game.creator.map.Map2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DungeonWithRoomsCreator implements MapGenerator {

    private static final int NR_OF_SEGMENTS = 5;
    private static final int ROOM_MAX_WIDTH = Config.Dungeon.DUNGEON_WIDTH / NR_OF_SEGMENTS;
    private static final int ROOM_MIN_WIDTH = 5;
    private static final int ROOM_MAX_HEIGHT = Config.Dungeon.DUNGEON_HEIGHT / NR_OF_SEGMENTS;
    private static final int ROOM_MIN_HEIGHT = 5;
    private static final int ROOM_APPEARANCE_CHANCE = 60;

    @Override
    public Map2D create() {
        Dungeon map = new Dungeon(Config.Dungeon.DUNGEON_WIDTH, Config.Dungeon.DUNGEON_HEIGHT, DungeonType.ROOMS);

        for(int i = 0; i < Config.Dungeon.DUNGEON_WIDTH; i++) {
            for(int j = 0; j < Config.Dungeon.DUNGEON_HEIGHT; j++) {
                map.setTile(i, j, Tile.FLOOR);
            }
        }
        placeRooms(map);

        return map;
    }

    public static void main(String[] args) {
        new DungeonWithRoomsCreator().create();
    }

    private void connectRooms(Dungeon map, Room room1, Room room2) {

        if(room1.getX() > room2.getMaxX() && room1.getY() > room2.getMaxY()) {
            System.out.println("Connecting " + room1 +" " +  room2);
            int start_x = room1.getX();
            int start_y = room1.getY() + Math.abs(room1.getY() - room1.getMaxY()) / 2;

            int end_x = room2.getX() + Math.abs(room2.getX() - room2.getMaxX()) / 2;
            int end_y = room2.getMaxY();

            map.setTile(start_x, start_y, Tile.FLOOR);
            map.setTile(end_x, end_y, Tile.FLOOR);

            Tile symbol = Tile.STONE_WALL;

            for(int i = start_x; i >= end_x ; i--) {

                boolean invert = false;
                if(map.getTile(i, start_y-1).equals(Tile.STONE_WALL) && i < start_x) invert = true;
                if(i > end_x)
                    map.setTile(i, start_y-1, symbol);

                map.setTile(i, start_y+1, symbol);

                if(i > end_x)
                    map.setTile(i, start_y, Tile.FLOOR);

                if(invert) {
                    symbol = invert(symbol);
                }
            }

            for(int i = start_y + 1; i > end_y; i--) {

                if(map.getTile(end_x+1, i) == Tile.STONE_WALL && i <  start_y-1) symbol = invert(symbol);

                map.setTile(end_x-1, i, symbol);

                if(i < start_y)
                    map.setTile(end_x, i, Tile.FLOOR);

                if(i < start_y)
                    map.setTile(end_x+1, i, symbol);
            }
        }
    }

    private Tile invert(Tile i) {
        return i.equals(Tile.FLOOR) ? Tile.STONE_WALL : Tile.FLOOR;
    }

    private void placeRooms(Dungeon map) {
        List<Room> rooms = new ArrayList<>();

        Room[][] roomLayout = new Room[NR_OF_SEGMENTS][NR_OF_SEGMENTS];

        // generate rooms
        for(int i = 0; i < NR_OF_SEGMENTS; i++) {
            for(int j = 0; j < NR_OF_SEGMENTS; j++) {

                //if(i == 0 && j == 0 || i == 3 && j == 3) {
                if(new Random().nextInt(100) < ROOM_APPEARANCE_CHANCE) {
                    Room room = new Room(i * ((Config.Dungeon.DUNGEON_WIDTH / NR_OF_SEGMENTS)-1),
                            j * ((Config.Dungeon.DUNGEON_HEIGHT / NR_OF_SEGMENTS)-1),
                            ROOM_MIN_WIDTH + new Random().nextInt(ROOM_MAX_WIDTH - ROOM_MIN_WIDTH),
                            ROOM_MIN_HEIGHT + new Random().nextInt(ROOM_MAX_HEIGHT - ROOM_MIN_HEIGHT));
                    rooms.add(room);
                    roomLayout[i][j] = room;
                }
            }
        }

        // Build walls on map
        for(Room room: rooms) {
            int x_start = room.getX();
            int y_start = room.getY();
            int x_end = room.getMaxX();
            int y_end = room.getMaxY();

            for (int i = x_start; i <= x_end; i++) {
                map.setTile(i, y_start, Tile.STONE_WALL);
                map.setTile(i, y_end, Tile.STONE_WALL);
            }

            for (int i = y_start; i <= y_end; i++) {
                map.setTile(x_start, i, Tile.STONE_WALL);
                map.setTile(x_end, i, Tile.STONE_WALL);
            }
        }

        // connect rooms
        System.out.println(" ***************************************** ");
        for(int i = 0; i < NR_OF_SEGMENTS; i++) {
            for(int j = 0; j < NR_OF_SEGMENTS; j++) {
                if(roomLayout[i][j] != null) {
                    Room nextRoom = findNextRoom(i,j, roomLayout);
                    connectRooms(map, roomLayout[i][j], nextRoom);
                }
            }
        }
    }

    private double distance(Room room1, Room room2) {
        int x1 = room1.getX() / NR_OF_SEGMENTS;
        int x2 = room2.getX() / NR_OF_SEGMENTS;
        int y1 = room1.getY() / NR_OF_SEGMENTS;
        int y2 = room2.getY() / NR_OF_SEGMENTS;

        int a = Math.abs(x1 - x2);
        int b = Math.abs(y1 - y2);

        return Math.sqrt(a*a + b*b);
    }

    private Room findNextRoom(int x, int y, Room[][] roomLayout) {
        Room room = null;

        List<Room> tilesVisited = new ArrayList<>();
        List<Room> tilesNotVisited = new ArrayList<>();

        tilesNotVisited.add(roomLayout[x][y]);


        while(tilesNotVisited.size() > 0) {
            Room current = tilesNotVisited.remove(0);
            tilesVisited.add(current);

            if(current.getX() / NR_OF_SEGMENTS != x || current.getY() / NR_OF_SEGMENTS != y) {
                // fount a room
                return current;
            }

            for(int i = -1; i < 2; i++) {
                for(int j = -1; j < 2; j++) {
                    if(x+i >= 0 && x+i < NR_OF_SEGMENTS && y+j >=0 && y+j < NR_OF_SEGMENTS) {
                        if(roomLayout[i+x][j+y] != null && !tilesNotVisited.contains(roomLayout[x+i][y+j]))
                            tilesNotVisited.add(roomLayout[x+i][y+j]);
                    }
                }
            }
        }

        return null;
    }
}
