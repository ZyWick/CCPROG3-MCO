/**
 * FarmTools stores information about a particular tool
 */
public interface FarmTools {

    /** tests if a certain tool can be used on a tile of a farm
     * 
     * @param farm the farm to which you will use the tool
     * @param coordinates the coordinates of the tile
     * @return int determining the error that occurred or none
     */
    public int canUseTool(MyFarm farm, Coordinates coordinates);

    /** applies the function of the tool to a tile on the farm based on the specifications
     * 
     * @param farm the farm to which you will use the tool
     * @param coordinates the coordinates of the tile
     * @return a double array containing the yield of using the tool
     */
    public double[] useTool(MyFarm farm, Coordinates coordinates);
 
    /** gets the yield from using a tool
     * @return an array containing {cost of using the tool, amount of experience gained}
     */
    public double[] getToolCostAndYield();
   
    /** gets the name of the tool
     * @return name of the tool
     */
    public String getName();

    /** gets usage cost
     * @return cost of using the tool
     */
    public int getUsageCost();
}
