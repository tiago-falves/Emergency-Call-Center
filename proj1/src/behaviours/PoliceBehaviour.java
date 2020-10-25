package behaviours;

import Messages.InformStatus;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;
import utils.AgentTypes;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class PoliceBehaviour extends VehicleBehaviour {

    public PoliceBehaviour(Agent agent, MessageTemplate msgTemp) {
        super(agent, msgTemp);
        System.out.println("Police created with distance = " + distance);
    }

    @Override
    public ACLMessage handleCfp(ACLMessage cfp) {
        ACLMessage vehicleReply = cfp.createReply();
        vehicleReply.setPerformative(ACLMessage.PROPOSE);
        try {
            vehicleReply.setContentObject(new InformStatus(distance,occupied));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vehicleReply;
    }

    @Override
    public void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
        if(occupied) System.out.println("Tower did not accept because I was occupied");
        else System.out.println("Tower did not accept my distance of " + distance);
    }

    @Override
    public ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) {
        System.out.println("Tower accepted my distance of " + distance + "!!");
        occupied = true;
        return null;
    }

    @Override
    public AgentTypes.AgentType getAgentType() {
        return AgentTypes.AgentType.POLICE;
    }
}
