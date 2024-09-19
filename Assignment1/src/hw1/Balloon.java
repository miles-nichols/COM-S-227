package hw1;

/**
 * This class balloon is used to represent a hot air balloon.
 * 
 * @author Miles Nichols
 * */
public class Balloon {

	/** Rate of fuel burning per second kBTU/s. */
	private double B;

	/** Balloon mass */
	private double mass;

	/** Time for simulation in seconds */
	private int simTime;

	/** Gravity constant */
	private final double G = 9.81;

	/** Heat loss constant */
	private final double H = .1;

	/** Volume constant */
	private final double V = 61234;

	/** Zero degrees Kelvin in Celsius constant */
	private final double K = 273.15;

	/** Gas constant */
	private final double R = 287.05;

	/** Standard pressure constant in hPa. */
	private final double P = 1013.25;

	/** The velocity o the balloon */
	private double velocity;
	
	/** The altitude of the balloon */
	private double altitude;
	
	/** The temperature inside of the balloon */
	private double balloonTemp;
	
	/** The length of the tether */
	private double tetherLength;
	
	/** Amount of fuel remaining */
	private double fuelRemaining;
	
	/** The direction of the wind in degrees */
	private double windDirection;
	
	/** Temperature outside of the balloon */
	private double outsideAirTemp;

	/** Used in the reset method and initial construction to set equal to outsideAirTemp*/
	private final double origOutsideAirTemp;

	/** Used to in the reset method and also in the initial construction of the balloon*/
	private final double origWindDirection;

	/**
	 * Constructs a new balloon simulation. The simulation starts with the given
	 * airTemp (outside air temperature in C) and windDirection (in degrees). It is
	 * assumed windDirection is between 0 (inclusive) and 360 (exclusive). The
	 * balloon temperature (air inside the balloon) is initialized to the same
	 * temperature as the outside air. The simulation time is initialized to the
	 * default value 0. The balloon’s altitude, remaining fuel, fuel burn rate,
	 * balloon mass, velocity, and tether length are all initialized to 0.
	 * 
	 * @param outsideAirTemp is the temperature of the air in C
	 * @param windDirection  is the direction of the wind in degrees from 0-359
	 */
	public Balloon(double outsideAirTemp, double windDirection) {
		this.origOutsideAirTemp = outsideAirTemp;
		this.outsideAirTemp = outsideAirTemp;
		this.balloonTemp = outsideAirTemp;
		this.origWindDirection = windDirection;
		this.windDirection = windDirection;

		this.simTime = 0;
		this.altitude = 0;
		this.fuelRemaining = 0;
		this.B = 0;
		this.mass = 0;
		this.velocity = 0;
		this.tetherLength = 0;
	}

	/**
	 * Gets remaining fuel that is used to heat the balloon
	 * 
	 * @return fuelRemaining the amount of fuel left
	 */
	public double getFuelRemaining() {
		return fuelRemaining;
	}

	/**
	 * Sets remaining fuel that is used to heat the balloon
	 * 
	 * @param fuel the input for remainingFuel
	 */
	public void setFuelRemaning(double fuel) {
		this.fuelRemaining = fuel;
	}

	/**
	 * The mass of the balloon
	 * 
	 * @return mass the mass of the balloon
	 */
	public double getBalloonMass() {
		return mass;
	}

	/**
	 * Sets the mass of the balloon 
	 * 
	 * @param mass the mass of the balloon
	 */
	public void setBalloonMass(double mass) {
		this.mass = mass;
	}

	/**
	 * Gets the outside air temperature 
	 * 
	 * @return outsideAirTemp the temperature outside of the balloon
	 */
	public double getOutsideAirTemp() {

		return outsideAirTemp;
	}

	/**
	 * Sets the outside air temperature
	 * 
	 * @param outsideAirTemp the air temperature outside of the a balloon
	 */
	public void setOutsideAirTemp(double outsideAirTemp) {

		this.outsideAirTemp = outsideAirTemp;
	}

	/**
	 * Setting the burn rate of fuel (B)
	 * 
	 * @param burnRate the rate that the fuel burns
	 */
	public void setFuelBurnRate(double burnRate) {

		this.B = burnRate;
	}

	/**
	 * Gets the fuel burn rate (B)
	 * 
	 * @return B burn rate
	 */
	public double getFuelBurnRate() {

		return B;
	}

	/**
	 * Gets the balloon temperature inside of the balloon
	 * 
	 * @return the temperature inside of the balloon
	 */
	public double getBalloonTemp() {
		return balloonTemp;
	}

	/**
	 * Sets the balloon temperature
	 * 
	 * @param temp the temperature of the balloon
	 */
	public void setBalloonTemp(double temp) {
		this.balloonTemp = temp;
	}

	/**
	 * Gets the balloon velocity (v)
	 * 
	 * @return velocity the velocity of the balloon
	 */
	public double getVelocity() {
		return velocity;
	}

	/**
	 * Gets the balloon altitude
	 * 
	 * @return altitude the altitude of the balloon
	 */
	public double getAltitude() {
		return altitude;
	}

	/**
	 * Gets the length of the tether
	 * 
	 * @return tetherLength - altitude or 0
	 */
	public double getTetherLength() {
		return Math.max(0, tetherLength - altitude);
	}

	/**
	 * Gets the length of the tether minus the current altitude of the balloon
	 * 
	 * @return getTetherLength() - getAltitude() the amount of tether remaining
	 */
	public double getTetherRemaining() {
		return getTetherLength() - getAltitude();
	}

	/**
	 * Sets the length of the tether
	 * 
	 * @param length the length of the tether
	 */
	public void setTetherLength(double length) {
		this.tetherLength = length;
	}

	/**
	 * Gets the direction of the wind in degrees, a number 0-359
	 * 
	 * @return windDirectionthat with modulus 360 and + 360 so it can handle
	 *         negative numbers
	 */
	public double getWindDirection() {
		return windDirection % 360;
	}

	/**
	 * Changes the windDirection by adding the users input for degrees to the
	 * current windDirection
	 * 
	 * @param deg the degree change for the windDirection
	 */
	public void changeWindDirection(double deg) {
		this.windDirection = this.windDirection + deg + 360;
	}

	/**
	 * Gets the number of full minutes that have passed in the simulation. For
	 * example, if the simulation time is 179, minutes = 2 and seconds = 59.
	 * 
	 * @return sumTime time divided 60 to find the minutes
	 */
	public long getMinutes() {
		return simTime / 60;
	}

	/**
	 * Gets the number of seconds passed the number of full minutes. For example, if
	 * the simulation time is 179, minutes = 2 and seconds = 59. The seconds should
	 * always be between 0 and 59 inclusive
	 * 
	 * @return sumTime time modulus(%) 60 to give the seconds
	 */
	public long getSeconds() {

		return simTime % 60;
	}

	/**
	 * Calling this method represents 1 second of simulated time passing.
	 * Specifically, the simulation time is incremented by 1. The fuel remaining is
	 * consumed at the set fuel burn rate, but it can never drop below 0. If the
	 * fuel burn rate is more than the available amount of fuel then consume as much
	 * fuel as is available but no more. The temperature inside of the balloon is
	 * updated (see formulas in the Overview section). The velocity and position of
	 * the balloon is also updated based on the formulas. Note that the calculations
	 * for velocity and position at time n depend on the velocity and position at
	 * time n-1 (one second ago). In other words, each calculation of velocity and
	 * position depends on the previous calculation. There are two exceptions to the
	 * calculation of the position of the balloon; the balloon can never drop below
	 * ground level (an altitude of 0) and can never rise above the length of the
	 * tether. That is to say, the ground level and the tether length are the
	 * minimum and maximum altitudes respectively. The velocity and altitude can
	 * only be calculated after updating the balloon temperature
	 */
	public void update() {
		simTime += 1;

		B = Math.min(B, fuelRemaining);
		fuelRemaining -= B;

		double tempChange = B + ((outsideAirTemp - balloonTemp) * H);
		balloonTemp = balloonTemp + tempChange;

		double densitySurrounding = P / (R * (outsideAirTemp + K));
		double densityBalloon = P / (R * (balloonTemp + K));
		double liftForce = V * (densitySurrounding - densityBalloon) * G;
		double gravForce = (mass * G);
		double netForce = liftForce - gravForce;
		double netAccel = netForce / mass;
		velocity += netAccel;

		/// calculate altitude
		altitude = altitude + velocity;
		altitude = Math.max(altitude, 0);
		altitude = Math.min(altitude, tetherLength);

	}

	/**
	 * Calling this method resets the simulation to its initial state (the same
	 * state it had immediately after the constructor was called). Pay attention
	 * that the outside air temperature and wind direction are reset to the values
	 * that were provided to the constructor.
	 */
	public void reset() {
		this.balloonTemp = this.origOutsideAirTemp;
		this.outsideAirTemp = this.origOutsideAirTemp;
		this.windDirection = this.origWindDirection;
		this.simTime = 0;
		this.altitude = 0;
		this.fuelRemaining = 0;
		this.B = 0;
		this.mass = 0;
		this.velocity = 0;
		this.tetherLength = 0;
	}

}
