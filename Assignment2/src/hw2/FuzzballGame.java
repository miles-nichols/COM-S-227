package hw2;

/**
 * Models a simplified baseball-like game called Fuzzball.
 * 
 * @author Miles Nichols
 */
public class FuzzballGame {
	/**
	 * Number of strikes causing a player to be out.
	 */
	public static final int MAX_STRIKES = 2;

	/**
	 * Number of balls causing a player to walk.
	 */
	public static final int MAX_BALLS = 5;

	/**
	 * Number of outs before the teams switch.
	 */
	public static final int MAX_OUTS = 3;

	/**
	 * variable to set the maximum the number of innings
	 */
	private int numInnings;

	/**
	 * variable to keep track of the current number of innings
	 */
	private int currentInning = 1;

	/**
	 * variable that is used to keep track of who is at bat
	 */
	private boolean topOfInning = true;

	/**
	 * variable to keep track of team 0's score
	 */
	private int team0Score = 0;

	/**
	 * variable to keep track of team 1's score
	 */
	private int team1Score = 0;

	/**
	 * variable that keeps track of the number of balls the current batter has
	 */
	private int ballCount = 0;

	/**
	 * variable that keeps track of the number of strikes the current batter has
	 */
	private int calledStrikes = 0;

	/**
	 * variable that keeps track of the number of outs the current batting team has
	 */
	private int currentOuts = 0;

	/**
	 * variable to keep track of the platers on base
	 */
	private boolean[] bases = new boolean[3]; // First, Second, Third

	/**
	 * Constructs a game that has the given number of innings and starts at the top
	 * of inning 1
	 *
	 * @param givenNumInnings - maximum number of innings set by the user
	 */
	public FuzzballGame(int givenNumInnings) {
		this.numInnings = givenNumInnings;
		this.currentInning = 1; // initializing
		this.topOfInning = true; // initializing
	}

	/**
	 * Method called to indicate a bad pitch at which the batter did not swing. This
	 * method adds 1 the the batter's count of balls, possibly resulting in a walk.
	 * This method does nothing if the game has ended.
	 */
	public void ball() {
		if (!gameEnded()) {
			ballCount++;
			if (ballCount >= MAX_BALLS) {
				advanceRunners(true); // walk
				resetBatter();
			}
		}
	}

	/**
	 * Method called to indicate that the batter is out due to a caught fly. This
	 * method does nothing if the game has ended.
	 */
	public void caughtFly() {
		if (!gameEnded()) {
			currentOuts++;
			resetBatter();
		}
		checkInningProgress();
	}

	/**
	 * Returns true if the game is over, false otherwise. Returns: true if the game
	 * is over, false otherwise
	 * 
	 * @return true or false depending on if it is equal to or greater than the
	 *         maximum number of innings and also not the top of the inning or if it
	 *         is top of the inning the current inning is more than the maximum
	 *         number of innings.
	 */
	public boolean gameEnded() {
		return (currentInning >= numInnings) && (!topOfInning || (topOfInning && currentInning > numInnings));
	}

	/**
	 * Returns the count of balls for the current batter.
	 * 
	 * @return ballCount - current number of balls
	 */
	public int getBallCount() {
		return ballCount;
	}

	/**
	 * Returns the number of called strikes for the current batter.
	 * 
	 * @return calledStrikes - current number of strikes
	 */
	public int getCalledStrikes() {
		return calledStrikes;
	}

	/**
	 * Returns the number of outs for the team currently at bat.
	 * 
	 * @return currentOuts - current number of outs
	 */
	public int getCurrentOuts() {
		return currentOuts;
	}

	/**
	 * Returns the score for team 0.
	 * 
	 * @return team0Score - score for team 0
	 */
	public int getTeam0Score() {
		return team0Score;
	}

	/**
	 * Returns the score for team 1.
	 * 
	 * @return team1Score - the score for team 1
	 */
	public int getTeam1Score() {
		return team1Score;
	}

	/**
	 * Method called to indicate that the batter hit the ball. The interpretation of
	 * the distance parameter is as follows: less than 15: the hit is a foul and the
	 * batter is immediately out. at least 15, but less than 150: the hit is a
	 * single. An imaginary runner advances to first base, and all other runners on
	 * base advance by 1. If there was a runner on third base, the score increases
	 * by 1. at least 150, but less than 200: the hit is a double. An imaginary
	 * runner advances to second base, and all other runners on base advance by 2.
	 * If there were runners on second or third, the score increases by 1 or 2. at
	 * least 200, but less than 250: the hit is a triple. An imaginary runner
	 * advances to third base, and all other runners on base advance by 3. If there
	 * were runners on first, second, or third, the score is increased by 1, 2, or
	 * 3. at least 250: the hit is a home run. All imaginary runners currently on
	 * base advance to home. The score is increased by 1 plus the number of runners
	 * on base. This method does nothing if the game has ended.
	 * 
	 * @param distance - the ball travels (possibly negative)
	 */
	public void hit(int distance) {
		if (!gameEnded()) {
			if (distance < 15) {
				// Foul, batter is out
				currentOuts++;
			} else if (distance < 150) {
				// Single
				advanceRunners(false);
				bases[0] = true; // Runner on first base
			} else if (distance < 200) {
				// Double
				advanceRunners(false);
				advanceRunners(false);
				bases[1] = true; // Runner on second base
				bases[0] = false;
			} else if (distance < 250) {
				// Triple
				advanceRunners(false);
				advanceRunners(false);
				advanceRunners(false);
				bases[2] = true; // Runner on third base
				bases[1] = false;
				bases[0] = false;
			} else {
				// Home run
				advanceRunners(false);
				advanceRunners(false);
				advanceRunners(false);
				scoreRun(); // Batter scores
				bases[2] = false;
				bases[1] = false;
				bases[0] = false;
			}
			resetBatter();
		}
		checkInningProgress();
	}

	/**
	 * Returns true if it's the first half of the inning (team 0 is at bat).
	 * 
	 * @return topOfInning - true if it's the first half of the inning, false
	 *         otherwise
	 */
	public boolean isTopOfInning() {
		return topOfInning;
	}

	/**
	 * Returns true if there is a runner on the indicated base, false otherwise.
	 * Returns false if the given argument is anything other than 1, 2, or 3.
	 * 
	 * @param which - base number to check Returns: true if there is a runner on the
	 *              indicated base
	 * 
	 * @return the current runner on base
	 */
	public boolean runnerOnBase(int which) {
		if (which < 0 || which > 3) {
			return false; // Invalid base number
		} else {
			return bases[which - 1]; // Adjust index to access bases array
		}
	}

	/**
	 * Method called to indicate a strike for the current batter. If the swung
	 * parameter is true, the batter is immediately out. Otherwise, 1 is added to
	 * the batters current count of called strikes (possibly resulting in the batter
	 * being out). This method does nothing if the game has ended.
	 * 
	 * @param swung - true if the batter swung at the pitch, false if it's a
	 *              "called" strike.
	 */
	public void strike(boolean swung) {
		if (!gameEnded()) {
			if (swung) {
				currentOuts++;
				resetBatter();
			} else {
				calledStrikes++;
				if (calledStrikes >= MAX_STRIKES) {
					currentOuts++;
					resetBatter();
				}
			}
		}
		checkInningProgress();
	}

	/**
	 * Returns the current inning. Innings are numbered starting at 1. When the game
	 * is over, this method returns the game's total number of innings, plus one.
	 * 
	 * @return currentInning - current inning, or the number of innings plus one in
	 *         case the game is over
	 */
	public int whichInning() {
		if (!gameEnded()) {
			return currentInning;
		} else {
			return currentInning + 1;
		}
	}

	/**
	 * Returns a three-character string representing the players on base, in the
	 * order first, second, and third, where 'X' indicates a player is present and
	 * 'o' indicates no player. For example, the string "oXX" means that there are
	 * players on second and third but not on first.
	 * 
	 * @return three-character string showing players on base
	 */

	public String getBases() {
		return (runnerOnBase(1) ? "X" : "o") + (runnerOnBase(2) ? "X" : "o") + (runnerOnBase(3) ? "X" : "o");
	}

	/**
	 * Returns a one-line string representation of the current game state. The
	 * format is:
	 * 
	 * <pre>
	 *  ooo Inning:1 [T] Score:0-0 Balls:0 Strikes:0 Outs:0
	 * </pre>
	 * 
	 * The first three characters represent the players on base as returned by the
	 * <code>getBases()</code> method. The 'T' after the inning number indicates
	 * it's the top of the inning, and a 'B' would indicate the bottom. The score
	 * always shows team 0 first.
	 * 
	 * @return a single line string representation of the state of the game
	 */
	public String toString() {
		String bases = getBases();
		String topOrBottom = (isTopOfInning() ? "T" : "B");
		String fmt = "%s Inning:%d [%s] Score:%d-%d Balls:%d Strikes:%d Outs:%d";
		return String.format(fmt, bases, whichInning(), topOrBottom, getTeam0Score(), getTeam1Score(), getBallCount(),
				getCalledStrikes(), getCurrentOuts());
	}

	// helper methods

	/**
	 * This method pushes each runner up a bases and calls scoreRun() if a runner
	 * scores. The wasWalked parameter in the advanceRunners method is a boolean
	 * flag that indicates whether the runner currently on base should be included
	 * in the advancement to the next base.
	 * 
	 * @param wasWalked - true if the better walked false otherwise
	 */
	private void advanceRunners(boolean wasWalked) {
		if (wasWalked) {
			if (bases[0]) {
				if (bases[1]) {
					if (bases[2]) {
						scoreRun();
					} else {
						bases[2] = true;
						bases[1] = true;
						bases[0] = true;
					}
				} else {
					bases[1] = true;
					bases[0] = true;
				}
			} else {
				bases[0] = true;
			}
		} else if (!wasWalked) {
			if (bases[2]) {
				scoreRun();
			}
			for (int i = 2; i > 0; i--) {
				bases[i] = bases[i - 1];
			}
		}
	}

	/**
	 * This method updates the score by adding to the scoring team's score.
	 */
	private void scoreRun() {
		if (topOfInning) {
			team0Score++;
		} else {
			team1Score++;
		}
	}

	/**
	 * This method checks if we have reached the maximum number of outs in and
	 * inning and switching sides if it is true.
	 */
	private void checkInningProgress() {
		if (currentOuts >= MAX_OUTS) {
			switchSides();
		}
	}

	/**
	 * Switches sides by setting topOfInning to the opposite and adding another
	 * inning if it was already the second half of the inning.
	 */
	private void switchSides() {
		if (topOfInning == false) {
			topOfInning = true;
			currentInning += 1;
		} else {
			topOfInning = false;
		}

		resetInning();
	}

	/**
	 * This method resets the outs, calls reset batter, and clears the bases.
	 */
	private void resetInning() {
		currentOuts = 0;
		resetBatter();
		bases = new boolean[3];
	}

	/**
	 * This method advances to the next batter by reseting ballCount and
	 * calledStikes to 0.
	 */
	private void resetBatter() {
		ballCount = 0;
		calledStrikes = 0;
	}
}
