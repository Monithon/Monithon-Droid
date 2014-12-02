package org.monithon.monithon.events;

import org.json.JSONObject;

/**
 * Created by MMo on 02/12/2014.
 */
public class ProjectsFoundEvent {
    private JSONObject object;
    public ProjectsFoundEvent(JSONObject object) {
        this.setObject(object);
    }

    public JSONObject getObject() {
        return object;
    }

    public void setObject(JSONObject object) {
        this.object = object;
    }
}
