package com.mygdx.game.creator.map.dungeon;

import com.mygdx.game.Config;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.logic.Point;

import java.util.*;

public class DungeonWithRoomsCreator implements MapGenerator {

    private static final int NR_OF_SEGMENTS = 10;
    private static final int ROOM_MAX_WIDTH = Config.Dungeon.ROOMS_DUNGEON_WIDTH / NR_OF_SEGMENTS - 2;
    private static final int ROOM_MIN_WIDTH = ROOM_MAX_WIDTH / 2 + 1;
    private static final int ROOM_MAX_HEIGHT = Config.Dungeon.ROOMS_DUNGEON_HEIGHT / NR_OF_SEGMENTS - 2;
    private static final int ROOM_MIN_HEIGHT = ROOM_MAX_HEIGHT / 2 + 1;

    @Override
    public Map2D create(int nrOfRoomsNeeded) {
        Dungeon map = new Dungeon(Config.Dungeon.ROOMS_DUNGEON_WIDTH, Config.Dungeon.ROOMS_DUNGEON_HEIGHT, DungeonType.ROOMS);

        for (int i = 0; i < Config.Dungeon.ROOMS_DUNGEON_WIDTH; i++) {
            for (int j = 0; j < Config.Dungeon.ROOMS_DUNGEON_HEIGHT; j++) {
                map.setTile(i, j, Tile.FLOOR);
            }
        }
        placeRooms(map, nrOfRoomsNeeded);

        return map;
    }

    public static void main(String[] args) {
        new DungeonWithRoomsCreator().create(6);
    }

    private void connectRooms(Dungeon map, Room room1, Room room2) {

        if (room1.getX() / NR_OF_SEGMENTS > room2.getX() / NR_OF_SEGMENTS && room1.getY() / NR_OF_SEGMENTS == room2.getY() / NR_OF_SEGMENTS) {
            System.out.println("Connecting " + room1 + " " + room2);
            int start_x = room1.getX();
            int start_y = room1.getY() + Math.abs(room1.getY() - room1.getMaxY()) / 2;

            int end_x = room2.getMaxX();
            int end_y = room2.getY() + Math.abs(room2.getY() - room2.getMaxY()) / 2;

            for (int i = start_x; i >= end_x; i--) {

                map.setTile(i, end_y - 1, Tile.STONE_WALL);
                map.setTile(i, end_y, Tile.FLOOR);
                map.setTile(i, end_y + 1, Tile.STONE_WALL);
            }
        }

        if (room1.getX() / NR_OF_SEGMENTS < room2.getX() / NR_OF_SEGMENTS && room1.getY() / NR_OF_SEGMENTS == room2.getY() / NR_OF_SEGMENTS) {
            System.out.println("Connecting " + room1 + " " + room2);
            int start_x = room1.getMaxX();
            int start_y = room1.getY() + Math.abs(room1.getY() - room1.getMaxY()) / 2;

            int end_x = room2.getX();
            int end_y = room2.getY() + Math.abs(room2.getY() - room2.getMaxY()) / 2;

            for (int i = start_x; i <= end_x; i++) {

                map.setTile(i, end_y - 1, Tile.STONE_WALL);
                map.setTile(i, end_y, Tile.FLOOR);
                map.setTile(i, end_y + 1, Tile.STONE_WALL);
            }
        }

        if (room1.getX() / NR_OF_SEGMENTS == room2.getX() / NR_OF_SEGMENTS && room1.getY() / NR_OF_SEGMENTS < room2.getY() / NR_OF_SEGMENTS) {
            System.out.println("Connecting " + room1 + " " + room2);
            int start_x = room1.getX() + Math.abs(room1.getX() - room1.getMaxX()) / 2;
            int start_y = room1.getMaxY();

            int end_x = room2.getX() + Math.abs(room2.getX() - room2.getMaxX()) / 2;
            int end_y = room2.getY();

            for (int i = start_y; i <= end_y; i++) {
                map.setTile(start_x - 1, i, Tile.STONE_WALL);
                map.setTile(start_x, i, Tile.FLOOR);
                map.setTile(start_x + 1, i, Tile.STONE_WALL);
            }
        }

        if (room1.getX() / NR_OF_SEGMENTS == room2.getX() / NR_OF_SEGMENTS && room1.getY() / NR_OF_SEGMENTS > room2.getY() / NR_OF_SEGMENTS) {
            System.out.println("Connecting " + room1 + " " + room2);
            int start_x = room1.getX() + Math.abs(room1.getX() - room1.getMaxX()) / 2;
            int start_y = room1.getY();

            int end_x = room2.getX() + Math.abs(room2.getX() - room2.getMaxX()) / 2;
            int end_y = room2.getMaxY();

            for (int i = start_y; i >= end_y; i--) {
                map.setTile(start_x - 1, i, Tile.STONE_WALL);
                map.setTile(start_x, i, Tile.FLOOR);
                map.setTile(start_x + 1, i, Tile.STONE_WALL);
            }
        }

    }

    private void placeRooms(Dungeon map, int nrOfRoomsNeeded) {

        int cellWidth = Config.Dungeon.ROOMS_DUNGEON_WIDTH / NR_OF_SEGMENTS;
        int cellHeight = Config.Dungeon.ROOMS_DUNGEON_HEIGHT / NR_OF_SEGMENTS;

        Room[][] roomLayout = new Room[NR_OF_SEGMENTS][NR_OF_SEGMENTS];

        for (int i = 0; i < NR_OF_SEGMENTS; i++) {
            for (int j = 0; j < NR_OF_SEGMENTS; j++) {
                roomLayout[i][j] = new Room(0, 0, 0, 0);
            }
        }

        List<Room> open = new ArrayList<>();
        List<Room> closed = new ArrayList<>();

        int steps = 0;

        Room start = new Room(5 * cellWidth + 1,
                5 * cellHeight + 1,
                ROOM_MIN_WIDTH + new Random().nextInt(ROOM_MAX_WIDTH - ROOM_MIN_WIDTH),
                ROOM_MIN_HEIGHT + new Random().nextInt(ROOM_MAX_HEIGHT - ROOM_MIN_HEIGHT));
        start.setEmpty(false);
        roomLayout[5][5] = start;
        buildWall(map, start);
        open.add(start);

        while (!open.isEmpty()) {

            Room current = open.remove(0);
            closed.add(current);

            int x = current.getX() / cellWidth;
            int y = current.getY() / cellHeight;

            List<int[]> possibleNext = new ArrayList<>();

            if (x < NR_OF_SEGMENTS - 1 && roomLayout[x + 1][y].isEmpty()) {
                possibleNext.add(new int[]{1, 0});
            }
            if (y < NR_OF_SEGMENTS - 1 && roomLayout[x][y + 1].isEmpty()) {
                possibleNext.add(new int[]{0, 1});
            }
            if (x > 0 && roomLayout[x - 1][y].isEmpty()) {
                possibleNext.add(new int[]{-1, 0});
            }
            if (y > 0 && roomLayout[x][y - 1].isEmpty()) {
                possibleNext.add(new int[]{0, -1});
            }

            if(possibleNext.size() == 0)
                continue;

            int r = new Random().nextInt(possibleNext.size());
            addRoomInDirection(map, cellWidth, cellHeight, roomLayout, current, x, y, possibleNext, r);

            steps++;
            if (steps >= nrOfRoomsNeeded) {
                return;
            }

            // try to connect another room
            while(possibleNext.size() > 0) {
                if (new Random().nextInt(3) == 0) {

                    r = new Random().nextInt(possibleNext.size());
                    addRoomInDirection(map, cellWidth, cellHeight, roomLayout, current, x, y, possibleNext, r);

                    steps++;
                    if (steps >= nrOfRoomsNeeded) {
                        return;
                    }
                } else {
                    possibleNext.remove(0);
                }
            }

            for (int[] i : new int[][]{{0, 1}, {1, 0}, {-1, 0}, {0, -1}}) {
                if (x + i[0] >= 0 && x + i[0] < NR_OF_SEGMENTS && y + i[1] >= 0 && y + i[1] < NR_OF_SEGMENTS && !closed.contains(roomLayout[x + i[0]][y + i[1]]) && !roomLayout[x + i[0]][y + i[1]].isEmpty()) {
                    open.add(roomLayout[x + i[0]][y + i[1]]);
                }
            }

        }
    }

    private void addRoomInDirection(Dungeon map, int cellWidth, int cellHeight, Room[][] roomLayout, Room current, int x, int y, List<int[]> possibleNext, int r) {
        // create room
        Room next = new Room((x + possibleNext.get(r)[0]) * (cellWidth),
                (y + possibleNext.get(r)[1]) * (cellHeight),
                ROOM_MIN_WIDTH + new Random().nextInt(ROOM_MAX_WIDTH - ROOM_MIN_WIDTH),
                ROOM_MIN_HEIGHT + new Random().nextInt(ROOM_MAX_HEIGHT - ROOM_MIN_HEIGHT));
        next.setEmpty(false);

        roomLayout[x + possibleNext.get(r)[0]][y + possibleNext.get(r)[1]] = next;
        buildWall(map, next);
        connectRooms(map, current, next);
        possibleNext.remove(r);
    }

    private void buildWall(Dungeon map, Room room) {
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
}
