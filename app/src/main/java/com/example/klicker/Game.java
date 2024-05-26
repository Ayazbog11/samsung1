package com.example.klicker;

public class Game {
    private int clicks;
    private int level;
    private int clicksPerClick;
    private int clicksToNextLevel;
    private boolean isSuperActive;
    private long superEndTime;
    private int superDuration;
    private int superCooldown;

    public Game() {
        this.clicks = 0;
        this.level = 1;
        this.clicksPerClick = 1;
        this.clicksToNextLevel = 100;
        this.isSuperActive = false;
        this.superEndTime = 0;
        this.superDuration = 20 * 1000; // 20 секунд
        this.superCooldown = 5 * 60 * 1000; // 5 минут
    }

    public int getClicks() {
        return clicks;
    }

    public int getLevel() {
        return level;
    }

    public int getClicksPerClick() {
        return isSuperActive ? clicksPerClick * 10 : clicksPerClick;
    }

    public int getClicksToNextLevel() {
        return clicksToNextLevel;
    }

    public boolean isSuperActive() {
        return isSuperActive;
    }

    public long getSuperEndTime() {
        return superEndTime;
    }

    public void click() {
        clicks += getClicksPerClick();
        checkLevelUp();
    }

    private void checkLevelUp() {
        if (clicks >= clicksToNextLevel) {
            level++;
            clicksToNextLevel = 100 * level * level;
        }
    }

    public void spendClicks(int amount) {
        clicks -= amount;
    }

    public void addClicksPerClick(int amount) {
        clicksPerClick += amount;
    }

    public boolean activateSuper() {
        long currentTime = System.currentTimeMillis();
        if (currentTime >= superEndTime) {
            isSuperActive = true;
            superEndTime = currentTime + superDuration;
            return true;
        }
        return false;
    }

    public void updateSuper() {
        if (isSuperActive && System.currentTimeMillis() > superEndTime) {
            isSuperActive = false;
            superEndTime = System.currentTimeMillis() + superCooldown;
        }
    }

    public long getSuperCooldown() {
        return superCooldown;
    }

    public void upgradeSuperDuration(int amount) {
        superDuration += amount;
    }

    public void upgradeSuperCooldown(int amount) {
        superCooldown -= amount;
    }
}