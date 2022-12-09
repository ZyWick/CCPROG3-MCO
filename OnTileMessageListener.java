public interface OnTileMessageListener {
    public void onMessagePlant(Coordinates coordinates, String seedName);
    public void onMessageUseTool(Coordinates coordinates, String toolName);
    public void onMessageHarvestCrop(Coordinates coordinates);
}
