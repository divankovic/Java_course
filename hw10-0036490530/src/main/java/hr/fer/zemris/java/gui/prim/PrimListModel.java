package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * A {@link ListModel} that stores <a href="https://en.wikipedia.org/wiki/Prime_number">prime numbers</a>.
 * @author Dorian Ivankovic
 *
 */
public class PrimListModel implements ListModel<Integer>{

	/**
	 * Stored prime numbers.
	 */
	private List<Integer> primes;
	
	/**
	 * Listeners of this model
	 */
	private Set<ListDataListener> listeners;
	
	/**
	 * Constructs a new <code>PrimListModel</code>.
	 */
	public PrimListModel() {
		primes = new ArrayList<>();
		primes.add(1);
		listeners = new HashSet<>();
	}
	
	@Override
	public void addListDataListener(ListDataListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void removeListDataListener(ListDataListener listener) {
		listeners.remove(listener);
	}

	@Override
	public Integer getElementAt(int index) {
		return primes.get(index);
	}

	@Override
	public int getSize() {
		return primes.size();
	}
	
	/**
	 * Adds the next prime numbers into the model and notifies all 
	 * listeners about the addition.
	 */
	public void next() {
		int lastPrime = primes.get(primes.size()-1);
		
		if(lastPrime==1 || lastPrime==2) {
			primes.add(lastPrime+1);
		}else {
			do {
				lastPrime += 2;
			} while (!isPrime(lastPrime));
			primes.add(lastPrime);
		}
		notifyAdded(primes.size()-1);
	}
	
	/**
	 * Notifies all listeners about the added element at position position.
	 * @param position - position of the newly added element.
	 */
	private void notifyAdded(int position) {
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, position, position);
		
		List<ListDataListener> listenersCopy = new ArrayList<>(listeners);
		for(ListDataListener listener : listenersCopy) {
			listener.intervalAdded(event);
		}
	
	}
	
	/**
	 * The method checks if the lastPrime number is prime.
	 * @param lastPrime - number to check if prime
	 * @return true if lastPrime is prime, false otherwise.
	 */
	private boolean isPrime(int lastPrime) {

		for (int i = 3; i * i <= lastPrime; i += 2) {
			if (lastPrime % i == 0)
				return false;
		}

		return true;
	}
	

}
