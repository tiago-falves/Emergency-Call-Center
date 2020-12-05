import agents.*;
import jade.core.Profile;
import jade.core.ProfileImpl;
import logs.LoggerHelper;
import sajas.core.Runtime;
import sajas.wrapper.AgentController;
import sajas.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import sajas.sim.repast3.Repast3Launcher;
import uchicago.src.sim.engine.SimInit;

public class RepastLauncher extends Repast3Launcher {
    // ******************************************************
    // batch mode variables
    private static final boolean BATCH_MODE = false;
    private final boolean runInBatchMode;

    // ******************************************************
    // width and height variables
    private static final int WIDTH = 200;
    private static final int HEIGHT = 200;

    // ******************************************************
    // Common vehicle variables
    private int MIN_NUM_EMPLOYEES= 1;
    private int MAX_NUM_EMPLOYEES= 6;
    private int REFUEL_DURATION_MS = 20000;
    private int EMPLOYEE_CHANGE_PROB = 10; // 1 in 10 chance of changing number employees

    private double MULTIPLIER_EMPLOYEE = 2.5;
    private double MULTIPLIER_DISTANCE = -1.0;
    private double MULTIPLIER_FUEL = 0.3;
    private double MULTIPLIER_EMPLOYEE_FUEL = 0.1;

    public int getMIN_NUM_EMPLOYEES() {
        return MIN_NUM_EMPLOYEES;
    }

    public void setMIN_NUM_EMPLOYEES(int MIN_NUM_EMPLOYEES) {
        if (MIN_NUM_EMPLOYEES > MAX_NUM_EMPLOYEES)
            MIN_NUM_EMPLOYEES = MAX_NUM_EMPLOYEES;
        if (MIN_NUM_EMPLOYEES < 1)
            MIN_NUM_EMPLOYEES = 1;
        this.MIN_NUM_EMPLOYEES = MIN_NUM_EMPLOYEES;
    }

    public int getMAX_NUM_EMPLOYEES() {
        return MAX_NUM_EMPLOYEES;
    }

    public void setMAX_NUM_EMPLOYEES(int MAX_NUM_EMPLOYEES) {
        if (MIN_NUM_EMPLOYEES > MAX_NUM_EMPLOYEES)
            MAX_NUM_EMPLOYEES = MIN_NUM_EMPLOYEES;
        if (MAX_NUM_EMPLOYEES < 1)
            MAX_NUM_EMPLOYEES = 1;
        this.MAX_NUM_EMPLOYEES = MAX_NUM_EMPLOYEES;
    }

    public int getREFUEL_DURATION_MS() {
        return REFUEL_DURATION_MS;
    }

    public void setREFUEL_DURATION_MS(int REFUEL_DURATION_MS) {
        if (REFUEL_DURATION_MS < 0)
            REFUEL_DURATION_MS = 0;
        this.REFUEL_DURATION_MS = REFUEL_DURATION_MS;
    }

    public int getEMPLOYEE_CHANGE_PROB() {
        return EMPLOYEE_CHANGE_PROB;
    }

    public void setEMPLOYEE_CHANGE_PROB(int EMPLOYEE_CHANGE_PROB) {
        if (EMPLOYEE_CHANGE_PROB < 1)
            EMPLOYEE_CHANGE_PROB = 1;
        this.EMPLOYEE_CHANGE_PROB = EMPLOYEE_CHANGE_PROB;
    }

    public double getMULTIPLIER_EMPLOYEE() {
        return MULTIPLIER_EMPLOYEE;
    }

    public void setMULTIPLIER_EMPLOYEE(double MULTIPLIER_EMPLOYEE) {
        this.MULTIPLIER_EMPLOYEE = MULTIPLIER_EMPLOYEE;
    }

    public double getMULTIPLIER_DISTANCE() {
        return MULTIPLIER_DISTANCE;
    }

    public void setMULTIPLIER_DISTANCE(double MULTIPLIER_DISTANCE) {
        this.MULTIPLIER_DISTANCE = MULTIPLIER_DISTANCE;
    }

    public double getMULTIPLIER_FUEL() {
        return MULTIPLIER_FUEL;
    }

    public void setMULTIPLIER_FUEL(double MULTIPLIER_FUEL) {
        this.MULTIPLIER_FUEL = MULTIPLIER_FUEL;
    }

    public double getMULTIPLIER_EMPLOYEE_FUEL() {
        return MULTIPLIER_EMPLOYEE_FUEL;
    }

    public void setMULTIPLIER_EMPLOYEE_FUEL(double MULTIPLIER_EMPLOYEE_FUEL) {
        this.MULTIPLIER_EMPLOYEE_FUEL = MULTIPLIER_EMPLOYEE_FUEL;
    }

    // ******************************************************
    // Inem vehicle variables
    private int NUM_INEM = 2;
    private int MAX_FUEL_INEM = 700;
    private int SPARE_FUEL_LEVEL_INEM = 100;
    private double FUEL_RATE_INEM = 3.0;

    public int getNUM_INEM() {
        return NUM_INEM;
    }

    public void setNUM_INEM(int NUM_INEM) {
        if (NUM_INEM < 0)
            NUM_INEM = 0;
        this.NUM_INEM = NUM_INEM;
    }

    public int getMAX_FUEL_INEM() {
        return MAX_FUEL_INEM;
    }

    public void setMAX_FUEL_INEM(int MAX_FUEL_INEM) {
        if (MAX_FUEL_INEM < SPARE_FUEL_LEVEL_INEM)
            MAX_FUEL_INEM = SPARE_FUEL_LEVEL_INEM;
        if (MAX_FUEL_INEM < 1)
            MAX_FUEL_INEM = 1;
        this.MAX_FUEL_INEM = MAX_FUEL_INEM;
    }

    public int getSPARE_FUEL_LEVEL_INEM() {
        return SPARE_FUEL_LEVEL_INEM;
    }

    public void setSPARE_FUEL_LEVEL_INEM(int SPARE_FUEL_LEVEL_INEM) {
        if (MAX_FUEL_INEM < SPARE_FUEL_LEVEL_INEM)
            SPARE_FUEL_LEVEL_INEM = MAX_FUEL_INEM;
        if (SPARE_FUEL_LEVEL_INEM < 1)
            SPARE_FUEL_LEVEL_INEM = 1;
        this.SPARE_FUEL_LEVEL_INEM = SPARE_FUEL_LEVEL_INEM;
    }

    public double getFUEL_RATE_INEM() {
        return FUEL_RATE_INEM;
    }

    public void setFUEL_RATE_INEM(double FUEL_RATE_INEM) {
        if (FUEL_RATE_INEM < 0.0)
            FUEL_RATE_INEM = 0.0;
        this.FUEL_RATE_INEM = FUEL_RATE_INEM;
    }

    // ******************************************************
    // Fire vehicle variables
    private int NUM_FIRE = 2;
    private int MAX_FUEL_FIRE = 1500;
    private int SPARE_FUEL_LEVEL_FIRE = 200;
    private double FUEL_RATE_FIRE = 6.0;

    public int getNUM_FIRE() {
        return NUM_FIRE;
    }

    public void setNUM_FIRE(int NUM_FIRE) {
        if (NUM_FIRE < 0)
            NUM_FIRE = 0;
        this.NUM_FIRE = NUM_FIRE;
    }

    public int getMAX_FUEL_FIRE() {
        return MAX_FUEL_FIRE;
    }

    public void setMAX_FUEL_FIRE(int MAX_FUEL_FIRE) {
        if (MAX_FUEL_FIRE < SPARE_FUEL_LEVEL_FIRE)
            MAX_FUEL_FIRE = SPARE_FUEL_LEVEL_FIRE;
        if (MAX_FUEL_FIRE < 1)
            MAX_FUEL_FIRE = 1;
        this.MAX_FUEL_FIRE = MAX_FUEL_FIRE;
    }

    public int getSPARE_FUEL_LEVEL_FIRE() {
        return SPARE_FUEL_LEVEL_FIRE;
    }

    public void setSPARE_FUEL_LEVEL_FIRE(int SPARE_FUEL_LEVEL_FIRE) {
        if (MAX_FUEL_FIRE < SPARE_FUEL_LEVEL_FIRE)
            SPARE_FUEL_LEVEL_FIRE = MAX_FUEL_FIRE;
        if (SPARE_FUEL_LEVEL_FIRE < 1)
            SPARE_FUEL_LEVEL_FIRE = 1;
        this.SPARE_FUEL_LEVEL_FIRE = SPARE_FUEL_LEVEL_FIRE;
    }

    public double getFUEL_RATE_FIRE() {
        return FUEL_RATE_FIRE;
    }

    public void setFUEL_RATE_FIRE(double FUEL_RATE_FIRE) {
        if (FUEL_RATE_FIRE < 0.0)
            FUEL_RATE_FIRE = 0.0;
        this.FUEL_RATE_FIRE = FUEL_RATE_FIRE;
    }

    // ******************************************************
    // Police vehicle variables
    private int NUM_POLICE = 2;
    private int MAX_FUEL_POLICE = 350;
    private int SPARE_FUEL_LEVEL_POLICE = 60;
    private double FUEL_RATE_POLICE = 2.0;

    public int getNUM_POLICE() {
        return NUM_POLICE;
    }

    public void setNUM_POLICE(int NUM_POLICE) {
        if (NUM_POLICE < 0)
            NUM_POLICE = 0;
        this.NUM_POLICE = NUM_POLICE;
    }

    public int getMAX_FUEL_POLICE() {
        return MAX_FUEL_POLICE;
    }

    public void setMAX_FUEL_POLICE(int MAX_FUEL_POLICE) {
        if (MAX_FUEL_POLICE < SPARE_FUEL_LEVEL_POLICE)
            MAX_FUEL_POLICE = SPARE_FUEL_LEVEL_POLICE;
        if (MAX_FUEL_POLICE < 1)
            MAX_FUEL_POLICE = 1;
        this.MAX_FUEL_POLICE = MAX_FUEL_POLICE;
    }

    public int getSPARE_FUEL_LEVEL_POLICE() {
        return SPARE_FUEL_LEVEL_POLICE;
    }

    public void setSPARE_FUEL_LEVEL_POLICE(int SPARE_FUEL_LEVEL_POLICE) {
        if (MAX_FUEL_POLICE < SPARE_FUEL_LEVEL_POLICE)
            SPARE_FUEL_LEVEL_POLICE = MAX_FUEL_POLICE;
        if (SPARE_FUEL_LEVEL_POLICE < 1)
            SPARE_FUEL_LEVEL_POLICE = 1;
        this.SPARE_FUEL_LEVEL_POLICE = SPARE_FUEL_LEVEL_POLICE;
    }

    public double getFUEL_RATE_POLICE() {
        return FUEL_RATE_POLICE;
    }

    public void setFUEL_RATE_POLICE(double FUEL_RATE_POLICE) {
        if (FUEL_RATE_POLICE < 0.0)
            FUEL_RATE_POLICE = 0.0;
        this.FUEL_RATE_POLICE = FUEL_RATE_POLICE;
    }

    // ******************************************************
    // Emergency variables
    private int SECOND_BETWEEN_CALLS = 1;
    private int MIN_VEHICLES_EMERGENCY = 1;
    private int MAX_VEHICLES_EMERGENCY = 3;
    private int MIN_DURATION_MS = 2000;
    private int MAX_DURATION_MS = 5000;

    public int getMIN_DURATION_MS() {
        return MIN_DURATION_MS;
    }

    public void setMIN_DURATION_MS(int MIN_DURATION_MS) {
        if (MIN_DURATION_MS > MAX_DURATION_MS)
            MIN_DURATION_MS = MAX_DURATION_MS;
        if (MIN_DURATION_MS < 0)
            MIN_DURATION_MS = 0;
        this.MIN_DURATION_MS = MIN_DURATION_MS;
    }

    public int getMAX_DURATION_MS() {
        return MAX_DURATION_MS;
    }

    public void setMAX_DURATION_MS(int MAX_DURATION_MS) {
        if (MAX_DURATION_MS < MIN_DURATION_MS)
            MAX_DURATION_MS = MIN_DURATION_MS;
        if (MAX_DURATION_MS < 0)
            MAX_DURATION_MS = 0;
        this.MAX_DURATION_MS = MAX_DURATION_MS;
    }

    public int getMIN_VEHICLES_EMERGENCY() {
        return MIN_VEHICLES_EMERGENCY;
    }

    public void setMIN_VEHICLES_EMERGENCY(int MIN_VEHICLES_EMERGENCY) {
        if (MIN_VEHICLES_EMERGENCY > MAX_VEHICLES_EMERGENCY)
            MIN_VEHICLES_EMERGENCY = MAX_VEHICLES_EMERGENCY;
        if (MIN_VEHICLES_EMERGENCY < 1)
            MIN_VEHICLES_EMERGENCY = 1;

        this.MIN_VEHICLES_EMERGENCY = MIN_VEHICLES_EMERGENCY;
    }

    public int getMAX_VEHICLES_EMERGENCY() {
        return MAX_VEHICLES_EMERGENCY;
    }

    public void setMAX_VEHICLES_EMERGENCY(int MAX_VEHICLES_EMERGENCY) {
        if (MAX_VEHICLES_EMERGENCY < MIN_VEHICLES_EMERGENCY)
            MAX_VEHICLES_EMERGENCY = MIN_VEHICLES_EMERGENCY;
        if (MAX_VEHICLES_EMERGENCY < 1)
            MAX_VEHICLES_EMERGENCY = 1;

        this.MAX_VEHICLES_EMERGENCY = MAX_VEHICLES_EMERGENCY;
    }

    public int getSECOND_BETWEEN_CALLS() {
        return SECOND_BETWEEN_CALLS;
    }

    public void setSECOND_BETWEEN_CALLS(int SECOND_BETWEEN_CALLS) {
        if (SECOND_BETWEEN_CALLS < 0)
            SECOND_BETWEEN_CALLS = 0;
        this.SECOND_BETWEEN_CALLS = SECOND_BETWEEN_CALLS;
    }

    // ******************************************************
    // launch arguments (simple logs and deterministic emergencies i.e. reading from file)
    private boolean SIMPLE = true;
    private boolean DETERMINISTIC = true;

    public boolean isSIMPLE() {
        return SIMPLE;
    }

    public void setSIMPLE(boolean SIMPLE) {
        this.SIMPLE = SIMPLE;
    }

    public boolean isDETERMINISTIC() {
        return DETERMINISTIC;
    }

    public void setDETERMINISTIC(boolean DETERMINISTIC) {
        this.DETERMINISTIC = DETERMINISTIC;
    }

    // ******************************************************

    @Override
    public String[] getInitParam() {
        return new String[] {
            "MIN_NUM_EMPLOYEES",
            "MAX_NUM_EMPLOYEES",
            "REFUEL_DURATION_MS",
            "EMPLOYEE_CHANGE_PROB",

            "MULTIPLIER_EMPLOYEE",
            "MULTIPLIER_DISTANCE",
            "MULTIPLIER_FUEL",
            "MULTIPLIER_EMPLOYEE_FUEL",

            "NUM_INEM",
            "MAX_FUEL_INEM",
            "SPARE_FUEL_LEVEL_INEM",
            "FUEL_RATE_INEM",

            "NUM_FIRE",
            "MAX_FUEL_FIRE",
            "SPARE_FUEL_LEVEL_FIRE",
            "FUEL_RATE_FIRE",

            "NUM_POLICE",
            "MAX_FUEL_POLICE",
            "SPARE_FUEL_LEVEL_POLICE",
            "FUEL_RATE_POLICE",

            "SECONDS_BETWEEN_CALLS",
            "MIN_VEHICLES_EMERGENCY",
            "MAX_VEHICLES_EMERGENCY",
            "MIN_DURATION_MS",
            "MAX_DURATION_MS",

            "SIMPLE",
            "DETERMINISTIC",
        };
    }

    public RepastLauncher(boolean runInBatchMode) {
        super();
        this.runInBatchMode = runInBatchMode;
    }

    @Override
	public void setup() {
		super.setup();
	}

    @Override
    public void begin() {
        super.begin();
        // display surfaces, spaces, displays, plots, ...
        if(!runInBatchMode) {
            buildAndScheduleDisplay();
        }
    }

    private void buildAndScheduleDisplay() {
        // TODO: fazer graficos e esquemas, para dar display
    }

    @Override
    public String getName() {
        return "Emergency Service";
    }

    @Override
    protected void launchJADE() {
        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        ContainerController container = rt.createMainContainer(p);

        if (SIMPLE) LoggerHelper.setSimpleLog();

        try {
            // ----------------------------------------------------
            // starting control tower agent
            ControlTowerAgent controlTowerAgent = new ControlTowerAgent();
            AgentController controlTower = container.acceptNewAgent(ControlTowerAgent.getDFName(), controlTowerAgent);
            LoggerHelper.get().logInfo("START - Started control tower");
            controlTower.start();

            // ----------------------------------------------------
            // starting vehicle agents
            VehicleAgent[] vehicles = createVehicles(NUM_INEM, NUM_FIRE, NUM_POLICE);
            startVehicles(vehicles, container);

            // ----------------------------------------------------
            // starting client agent
            ClientAgent clientAgent = new ClientAgent("johnny", ControlTowerAgent.getDFName(), DETERMINISTIC);
            AgentController client = container.acceptNewAgent(clientAgent.getClientName(), clientAgent);
            String deterministicInfo = DETERMINISTIC ? "deterministic" : "random";
            LoggerHelper.get().logInfo("CLIENT - Started " + deterministicInfo + " client " + clientAgent.getClientName());
            client.start();

        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }

    private static VehicleAgent[] createVehicles(int numberInem, int numberFire, int numberPolice){
        LoggerHelper.get().logCreateVehicles(numberInem, numberFire, numberPolice);

        int total = numberFire + numberInem + numberPolice;
        VehicleAgent[] vehicles  = new VehicleAgent[total];

        for (int i = 0; i < numberInem; i++) {
            String name = "Inem" + i;
            VehicleAgent vehicleAgent = new InemAgent(name);
            vehicles[i] = vehicleAgent;
        }
        for (int i = numberInem; i < numberInem + numberFire; i++) {
            String name = "Fireman" + i;
            VehicleAgent vehicleAgent = new FiremanAgent(name);
            vehicles[i] = vehicleAgent;
        }
        for (int i = numberInem + numberFire; i < total; i++) {
            String name = "Police" + i;
            VehicleAgent vehicleAgent = new PoliceAgent(name);
            vehicles[i] = vehicleAgent;
        }
        return vehicles;
    }

    private static void startVehicles(VehicleAgent[] vehicleAgents, ContainerController container) {
        for (VehicleAgent vehicleAgent : vehicleAgents){
            AgentController vehicle = null;
            try {
                vehicle = container.acceptNewAgent(vehicleAgent.getVehicleName(), vehicleAgent);
                vehicle.start();
            } catch (StaleProxyException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Launching Repast3
     * @param args
     */
    public static void main(String[] args) {
        boolean runMode = BATCH_MODE;

        SimInit init = new SimInit();
        init.setNumRuns(1);   // works only in batch mode
        init.loadModel(new RepastLauncher(runMode), null, runMode);
    }

}
