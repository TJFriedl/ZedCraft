package org.cpre488.zedcraftplugin.classes;

public class BlockData {

    private RGBA rgba;
    private String materialName;
    private int metaData;

    public BlockData(RGBA rgba, String materialName, int metaData) {
        this.rgba = rgba;
        this.materialName = materialName;
        this.metaData = metaData;
    }

    public RGBA getRgba() { return this.rgba; }
    public String getMaterialName() { return this.materialName; }
    public int getMetaData() { return this.metaData; }

    @Override
    public String toString() {
        return "RGBA: " + this.rgba.toString() + "\nMaterial Name: "
                + this.materialName + "\nMetadata: " + this.metaData;
    }

}
