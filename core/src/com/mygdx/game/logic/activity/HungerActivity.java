package com.mygdx.game.logic.activity;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Food;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.registry.ItemRegistry;

public class HungerActivity implements Activity, CooldownActivity {

    private ItemRegistry itemRegistry = ItemRegistry.INSTANCE;

    private PathFinder pathFinder = new PathFinder();
    private boolean firstRun = true;
    private int priority = 99;
    private int counter = 0;
    private MovementActivity movementActivity;

    private final Actor actor;
    private Food food;
    private boolean suspended = false;

    public HungerActivity(Actor actor, Food food) {
        this.actor = actor;
        this.food = food;
    }

    @Override
    public void countDown() {
        counter = (counter + 1) % actor.getMovementSpeed();
    }

    @Override
    public boolean isTriggered() {
        return counter == actor.getMovementSpeed() -1;
    }

    @Override
    public boolean isDone() {
        return actor.getX() == food.getX() && actor.getY() == food.getY() || actor.getInventory().has(Food.class);
    }

    @Override
    public void update() {
        movementActivity.update();
    }

    @Override
    public void init() {
        firstRun = false;
        this.movementActivity = new MovementActivity(actor, food.getX(), food.getY(), pathFinder);
        movementActivity.init();
    }

    @Override
    public void cancel() {
        System.out.println(actor + " cancelled.");
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public boolean isFirstRun() {
        return firstRun;
    }

    @Override
    public void suspend() {
        suspended = true;
    }

    @Override
    public void resume() {
        suspended = false;
        if(itemRegistry.getAllItems(actor.getCurrentMap()).contains(food)) {
            movementActivity.init();
        }
    }

    @Override
    public int compareTo(Activity o) {
        return Integer.compare(priority, o.getPriority());
    }

    @Override
    public boolean isSuspended() {
        return suspended;
    }

    @Override
    public void clear() {
        if(actor.getInventory().has(Food.class)) {
            this.food = actor.getInventory().get(Food.class);
            System.out.println("I will eat this from my inventory: " + food);
        }
        System.out.println(" I ate " + food.getNutritionAmount() + " " + actor.getHungerLevel());
        actor.decreaseHunger(food.getNutritionAmount());
        itemRegistry.getAllItems(actor.getCurrentMap()).remove(food);
        System.out.println(actor.getHungerLevel());
    }

    @Override
    public boolean isCancellable() {
        // food is gone
        return !itemRegistry.getAllItems(actor.getCurrentMap()).contains(food);
    }
}
