class InvalidInputException extends RuntimeException {
	public InvalidInputException ()
		{ super("Please enter an integer"); }
}

class InvalidSimulationMinutesException extends RuntimeException {
	public InvalidSimulationMinutesException ()
		{ super("Simulation minutes must be more than or equal to zero"); }
}

