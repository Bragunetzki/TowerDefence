package com.mygdx.towerdefence.events;

public class BuildingTouchedEvent implements ViewEvent {
    private final int refID, id;

    public BuildingTouchedEvent(int refID, int id) {
        this.refID = refID;
        this.id = id;
    }

    @Override
    public void execute(ViewHolder view) {
        view.showBuildingDialog(refID, id);
    }
}
