package com.mygdx.game.creator.dungeon;

import com.mygdx.game.Config;
import com.mygdx.game.dto.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DungeonWithRoomsCreator implements DungeonCreator {

    private static final int NR_OF_SEGMENTS = 5;
    private static final int ROOM_MAX_WIDTH = Config.DungeonConfig.DUNGEON_WIDTH / NR_OF_SEGMENTS;
    private static final int ROOM_MIN_WIDTH = 5;
    private static final int ROOM_MAX_HEIGHT = Config.DungeonConfig.DUNGEON_HEIGHT / NR_OF_SEGMENTS;
    private static final int ROOM_MIN_HEIGHT = 5;
    private static final int ROOM_APPEARANCE_CHANCE = 60;
    public static final int CORRIDOR_WALL = 1;


    @Override
    public int[][] create() {
        int[][] map = new int[Config.DungeonConfig.DUNGEON_WIDTH][Config.DungeonConfig.DUNGEON_HEIGHT];

        for(int i = 0; i < Config.DungeonConfig.DUNGEON_WIDTH; i++) {
            for(int j = 0; j < Config.DungeonConfig.DUNGEON_HEIGHT; j++) {
                map[i][j] = 0;
            }
        }
        placeRooms(map);

        return  map;
    }

    public static void main(String[] args) {
        new DungeonWithRoomsCreator().create();
    }

    private void connectRooms(int[][] map, Room room1, Room room2) {

        if(room1.getX() > room2.getMaxX() && room1.getY() > room2.getMaxY()) {
            System.out.println("Connecting " + room1 +" " +  room2);
            int start_x = room1.getX();
            int start_y = room1.getY() + Math.abs(room1.getY() - room1.getMaxY()) / 2;

            int end_x = room2.getX() + Math.abs(room2.getX() - room2.getMaxX()) / 2;
            int end_y = room2.getMaxY();

            map[start_x][start_y] = 0;
            map[end_x][end_y] = 0;

            int symbol = CORRIDOR_WALL;

            for(int i = start_x; i >= end_x ; i--) {

                boolean invert = false;
                if(map[i][start_y-1] == CORRIDOR_WALL && i < start_x) invert = true;
                if(i > end_x)
                    map[i][start_y-1] = symbol;

                map[i][start_y+1] = symbol;

                if(i > end_x)
                    map[i][start_y] = 0;

                if(invert) {
                    symbol = invert(symbol);
                }
            }

            for(int i = start_y + 1; i > end_y; i--) {

                if(map[end_x+1][i] == CORRIDOR_WALL && i <  start_y-1) symbol = invert(symbol);

                map[end_x-1][i] = symbol;

                if(i < start_y)
                    map[end_x][i] = 0;

                if(i < start_y)
                    map[end_x+1][i] = symbol;
            }
        }
    }

    private int invert(int i) {
        return i == 0 ? CORRIDOR_WALL : 0;
    }

    private void placeRooms(int[][] map) {
        List<Room> rooms = new ArrayList<>();

        Room[][] roomLayout = new Room[NR_OF_SEGMENTS][NR_OF_SEGMENTS];

        // generate rooms
        for(int i = 0; i < NR_OF_SEGMENTS; i++) {
            for(int j = 0; j < NR_OF_SEGMENTS; j++) {

                //if(i == 0 && j == 0 || i == 3 && j == 3) {
                if(new Random().nextInt(100) < ROOM_APPEARANCE_CHANCE) {
                    Room room = new Room(i * ((Config.DungeonConfig.DUNGEON_WIDTH / NR_OF_SEGMENTS)-1),
                            j * ((Config.DungeonConfig.DUNGEON_HEIGHT / NR_OF_SEGMENTS)-1),
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
                map[i][y_start] = 1;
                map[i][y_end] = 1;
            }

            for (int i = y_start; i <= y_end; i++) {
                map[x_start][i] = 1;
                map[x_end][i] = 1;
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
        for(int i = x-1; i >= 0; i--) {
            for(int j = y-1; j>= 0; j--) {
                if(roomLayout[i][j] != null) {
                    if (room == null) room = roomLayout[i][j];
                    else {
                        if (distance(roomLayout[x][y], roomLayout[i][j]) < distance(roomLayout[x][y], room)) {
                            room = roomLayout[i][j];
                        }
                    }
                }
            }
        }

        for(int i = x+1; i < roomLayout.length; i++) {
            for(int j = y-1; j>= 0; j--) {
                if(roomLayout[i][j] != null) {
                    if (room == null) room = roomLayout[i][j];
                    else {
                        if (distance(roomLayout[x][y], roomLayout[i][j]) < distance(roomLayout[x][y], room)) {
                            room = roomLayout[i][j];
                        }
                    }
                }
            }
        }

        for(int i = x+1; i < roomLayout.length; i++) {
            for(int j = y+1; j< roomLayout[0].length; j++) {
                if(roomLayout[i][j] != null) {
                    if (room == null) room = roomLayout[i][j];
                    else {
                        if (distance(roomLayout[x][y], roomLayout[i][j]) < distance(roomLayout[x][y], room)) {
                            room = roomLayout[i][j];
                        }
                    }
                }
            }
        }

        for(int i = x-1; i >= 0; i--) {
            for(int j = y+1; j < roomLayout[0].length; j++) {
                if(roomLayout[i][j] != null) {
                    if (room == null) room = roomLayout[i][j];
                    else {
                        if (distance(roomLayout[x][y], roomLayout[i][j]) < distance(roomLayout[x][y], room)) {
                            room = roomLayout[i][j];
                        }
                    }
                }
            }
        }

        return room;
    }
}
