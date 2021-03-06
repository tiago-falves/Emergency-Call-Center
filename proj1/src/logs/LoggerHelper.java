package logs;

import behaviours.VehicleBehaviour;
import utils.Emergency;
import utils.Point;
import utils.VehicleType;

import java.io.IOException;
import java.util.Date;
import java.util.logging.*;

public class LoggerHelper {
    private final static String FILEPATH = System.getProperty("user.dir") + "/src/logs/proj.log";
    private final Logger logger;
    private static boolean simpleFlag = false;

    private static LoggerHelper instance;

    public static LoggerHelper get() {
        if (instance == null) {
            try {
                instance = new LoggerHelper();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return instance;
    }

    private LoggerHelper() throws IOException {
        logger = Logger.getLogger(LoggerHelper.class.getName());
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.INFO);

        SimpleFormatter formatter = new SimpleFormatter(){

            @Override
            public synchronized String format(LogRecord lr) {
                if(simpleFlag) return String.format("%s %n",lr.getMessage());
                return String.format(
                        "[%1$tF %1$tT] [%2$-7s] %3$s %n",
                        new Date(lr.getMillis()),
                        lr.getLevel().getLocalizedName(),
                        lr.getMessage()
                );
            }
        };

        FileHandler fileTxt = new FileHandler(FILEPATH);
        fileTxt.setFormatter(formatter);
        logger.addHandler(fileTxt);

        ConsoleHandler cs = new ConsoleHandler();
        cs.setFormatter(formatter);
        logger.addHandler(cs);

        logger.info("--------------- STARTED NEW EXECUTION ---------------");
    }

    public void logInfo(String text) {
        logger.info(text);
    }

    public void logError(String text) {
        logger.severe(text);
    }

    public void logWarning(String text) {
        logger.warning(text);
    }

    public void logCreateVehicles(int numberInem, int numberFire, int numberPolice) {
        int total = numberFire + numberInem + numberPolice;

        logger.info("START - Going to start a total of " + total + " vehicles (" +
                "Inem=" + numberInem + ", firemen=" + numberFire + ", police=" + numberPolice + ")");
    }

    public void logCreatedEmergency(Emergency emergency) {
        logger.info(
                (simpleFlag ? (getIDOut(emergency.getId())) : "")+
                "Client has sent to tower emergency: " + emergency
        );
    };

    public void logReceivedEmergency(Emergency emergency) {
        logger.info (
                (simpleFlag ? (getIDOut(emergency.getId())) : "")+
                "Tower received emergency: " + emergency
        );
    }

    public void logNotEnoughVehicles(Emergency emergency) {
        logger.info(
                "Not enough available vehicles for emergency " + emergency
        );
    }

    public void logStartVehicle(String vehicleName, VehicleType vehicleType, VehicleBehaviour vehicle) {
        logger.info(
                "VEHICLE - Name: " + vehicleName + "; " +
                vehicleType + " - " + vehicle
        );
    }

    public void logAlreadyOccupied(String vehicleName) {
        if(!simpleFlag) {
            logger.info(
                    vehicleName + " - Can't accept emergency because I am occupied"
            );
        }
    }


    public void logRefueling(String vehicleName) {
        if(!simpleFlag) {
            logger.info(
                    vehicleName + " - Can't accept emergency because I am refueling"
            );
        }
    }

    public void logFuelInsuf(String vehicleName) {
        if(!simpleFlag) {
            logger.info(
                    vehicleName + " - Can't accept emergency because I don't have enough fuel for it"
            );
        }
    }

    public void logRejectProposalOccupied(String vehicleName) {
        if(!simpleFlag) {
            logger.info(
                    vehicleName + " - Tower did not accept because I was occupied"
            );
        }
    }


    public void logRejectProposalRefueling(String vehicleName) {
        if(!simpleFlag) {
            logger.info(
                    vehicleName + " - Tower did not accept because I was refueling"
            );
        }
    }


    public void logRejectProposal(String vehicleName, Point coordinates) {
        if(!simpleFlag) {
            logger.info(
                    vehicleName + " - Tower refused my service; my location is " + coordinates
            );
        }
    }

    public void logSendingCfpTo(String DFName) {
        if(!simpleFlag) {
            logInfo("Tower - sending cfp to " + DFName + " vehicles");
        }
    }

    public void logAcceptProposal(String vehicleName, Point coordinates) {
        if(!simpleFlag) {
            logger.info(
                    vehicleName + " - Tower selected me for the emergency! My location is " + coordinates
            );
        }
    }

    public void logOccupied(String vehicleName, double duration) {
        if(!simpleFlag) {
            logger.info(
                    vehicleName + " - Will be occupied for " + duration + " seconds"
            );
        }
    }

    public void logEmployeeChange(String vehicleName, int numberEmployees) {
        if(!simpleFlag) {
            logger.info(
                    vehicleName + " - Changed number employees to " + numberEmployees
            );
        }
    }

    public void logRejectedConsecutiveMax(String vehicleName, int maxConsecutiveRejections) {
        if(!simpleFlag) {
            logger.info(
                    vehicleName + " - Rejected max number of consecutive emergencies:  " + maxConsecutiveRejections
            );
        }
    }

    public void logUnoccupied(String vehicleName, int fuel, int numberEmployees) {
        if(!simpleFlag) {
            logger.info(
                    vehicleName + " - Done with previous emergency! " +
                            "Remaining fuel: " + fuel + "; Employees = " + numberEmployees
            );
        }
    }

    public void logNeedRefuel(String vehicleName, int fuel) {
        if(!simpleFlag) {
            logger.info(
                    vehicleName + " - Going to refuel: " + fuel + " remaining"
            );
        }
    }

    public void logDoneRefuel(String vehicleName, int fuel) {
        if(!simpleFlag) {
            logger.info(
                    vehicleName + " - Done refueling: " + fuel + " filled up"
            );
        }
    }

    public void logHandleCfp(String vehicleName) {
        if(!simpleFlag) {
            logger.info(
                    vehicleName + " - received CFP from tower"
            );
        }
    }

    public void logReceiveVehiclePropose(String vehicleName, double value) {
        if(!simpleFlag) {
            logger.info(
                    "Tower - Received propose from vehicle " +
                            vehicleName +
                            "; value = " + value
            );
        }
    }

    public void logReceiveVehicleRefuse(String vehicleName) {
        if(!simpleFlag) {
            logger.info(
                    "Tower - " + vehicleName + " has refused"
            );
        }
    }

    public void logAcceptVehicle(int id, String vehicleName, double value) {
        logger.info(
                (simpleFlag ? (getIDOut(id)) : "")+ "Tower - Going to accept vehicle " +
                vehicleName +
                ", value = " +
                value
        );
    }

    public void logMaxRetriesEmergency(Emergency emergency, int maxTries) {
        logger.warning(
                "Tower - Surpassed the max number of tries (" + maxTries + ") for emergency" + emergency
        );
    }

    public boolean simpleLog() {
        return simpleFlag;
    }
    public String getIDOut(int id){ return "[" + id + "] ";}

    public static void setSimpleLog(){
        System.out.println("Showing simplified output");
        simpleFlag = true;
    }
}
