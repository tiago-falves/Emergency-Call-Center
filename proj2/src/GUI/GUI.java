package GUI;

import agents.VehicleAgent;
import uchicago.src.sim.gui.OvalNetworkItem;
import uchicago.src.sim.network.DefaultDrawableNode;
import utils.VehicleType;

import java.awt.*;

public class GUI {
    public static Color parseColor(VehicleAgent agent){
        switch (agent.getType()){
            case FIREMAN:
                return Color.red;
            case INEM:
                return Color.WHITE;
            case POLICE: return Color.blue;
            default: return Color.BLACK;
        }
    }

    public static DefaultDrawableNode generateNode(String label, Color color, double x, double y) {
        OvalNetworkItem oval = new OvalNetworkItem(x,y);
        oval.allowResizing(false);
        oval.setHeight(5);
        oval.setWidth(5);

        DefaultDrawableNode node = new DefaultDrawableNode(label, oval);
        node.setColor(color);

        return node;
    }
}