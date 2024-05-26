package com.example.klicker;

public class Upgrade {
    private int cost;
    private int costIncrease;
    private int additionalClicksPerClick;

    public Upgrade(int initialCost, int costIncrease, int additionalClicksPerClick) {
        this.cost = initialCost;
        this.costIncrease = costIncrease;
        this.additionalClicksPerClick = additionalClicksPerClick;
    }

    public int getCost() {
        return cost;
    }

    public int getAdditionalClicksPerClick() {
        return additionalClicksPerClick;
    }

    public boolean applyUpgrade(Game game) {
        if (game.getClicks() >= cost) {
            game.spendClicks(cost);
            game.addClicksPerClick(additionalClicksPerClick);
            cost += costIncrease; // Увеличиваем цену апгрейда
            return true;
        }
        return false;
    }
}

