package org.sebasi.mep.connectomebuilder.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.sebasi.mep.connectomebuilder.util.FileUtil;
import org.sebasi.mep.connectomebuilder.util.GlobalStaticHelper;

public class Room extends AbstractComponent {

    int width = 30;
    int length = 20;
    int height = 3;

    // todo: The room can include some other environmental stuff in it later.
    // bodies can move around in the room. Different parts of the room can have
    // different impacts on the wellness.

    public void writeInitialDataFiles(String einDirectory) {
        try {
            FileUtil.writeRoom(
                    einDirectory,
                    GlobalStaticHelper.objectMapper.writeValueAsString(this));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Problem serializing room into json: " + e.getMessage(), e);
        }
    }

    @Override
    public void report(StringBuilder builder) {
        builder.append("\nThere is a room.");
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
