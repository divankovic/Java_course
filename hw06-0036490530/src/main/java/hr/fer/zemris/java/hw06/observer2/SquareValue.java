package hr.fer.zemris.java.hw06.observer2;

public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		int value = istorage.getValue();
		System.out.println("Provided new value: "+value+", square is "+value*value);
	}

}
