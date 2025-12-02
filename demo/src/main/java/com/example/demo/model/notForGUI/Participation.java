package com.example.demo.model.notForGUI;

public class Participation {
    private int id;
    private int clientId;
    private int eventId;
    private int placement;

    public Participation(int id, int clientId, int eventId, int placement) {
        this.id = id;
        this.clientId = clientId;
        this.eventId = eventId;
        this.placement = placement;
    }
}
