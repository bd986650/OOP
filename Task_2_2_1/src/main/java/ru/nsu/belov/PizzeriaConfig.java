package ru.nsu.belov;

import java.util.List;

public class PizzeriaConfig {
    private List<Baker> bakers;
    private List<Courier> couriers;
    private int storageCapacity;
    private int simulationTime;

    // Конструктор без параметров для Jackson
    public PizzeriaConfig() {}

    // Конструктор с параметрами для удобства создания объектов
    public PizzeriaConfig(List<Baker> bakers, List<Courier> couriers, int storageCapacity, int simulationTime) {
        this.bakers = bakers;
        this.couriers = couriers;
        this.storageCapacity = storageCapacity;
        this.simulationTime = simulationTime;
    }

    // Геттеры и сеттеры
    public List<Baker> getBakers() {
        return bakers;
    }

    public void setBakers(List<Baker> bakers) {
        this.bakers = bakers;
    }

    public List<Courier> getCouriers() {
        return couriers;
    }

    public void setCouriers(List<Courier> couriers) {
        this.couriers = couriers;
    }

    public int getStorageCapacity() {
        return storageCapacity;
    }

    public void setStorageCapacity(int storageCapacity) {
        this.storageCapacity = storageCapacity;
    }

    public int getSimulationTime() {
        return simulationTime;
    }

    public void setSimulationTime(int simulationTime) {
        this.simulationTime = simulationTime;
    }
}
