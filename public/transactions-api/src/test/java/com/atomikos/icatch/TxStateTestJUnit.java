/**
 * Copyright (C) 2000-2016 Atomikos <info@atomikos.com>
 *
 * LICENSE CONDITIONS
 *
 * See http://www.atomikos.com/Main/WhichLicenseApplies for details.
 */

package com.atomikos.icatch;

import static com.atomikos.recovery.TxState.COMMITTING;
import static com.atomikos.recovery.TxState.HEUR_ABORTED;
import static com.atomikos.recovery.TxState.HEUR_COMMITTED;
import static com.atomikos.recovery.TxState.HEUR_MIXED;
import static com.atomikos.recovery.TxState.IN_DOUBT;
import static com.atomikos.recovery.TxState.TERMINATED;
import static org.junit.Assert.*;

import java.util.EnumSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.atomikos.recovery.TxState;

public class TxStateTestJUnit {
	@Test
	public void testCountStates() {
		TxState[] states = TxState.values();
		assertEquals(15, states.length);
	}

	@Test
	public void testRecoverableStates() throws Exception {
		Set<TxState> recoverableStates = EnumSet.of(TERMINATED, IN_DOUBT, COMMITTING, HEUR_COMMITTED, HEUR_ABORTED, HEUR_MIXED);
		TxState[] states = TxState.values();
		for (TxState txState : states) {
			if (recoverableStates.contains(txState)) {
				Assert.assertTrue(txState.isRecoverableState());
			} else {
				Assert.assertFalse(txState.isRecoverableState());
			}
		}
	}
	@Test
	public void testTerminatedMustRecoverableToDeleteFromLogs() throws Exception {
		assertTrue(TERMINATED.isRecoverableState());
	}

	@Test
	public void testFinalStates() {
		Set<TxState> finalStates = EnumSet.of(TERMINATED);
		TxState[] states = TxState.values();
		for (TxState txState : states) {
			if (finalStates.contains(txState)) {
				Assert.assertTrue(txState.isFinalState());
			} else {
				Assert.assertFalse(txState.isFinalState());
			}
		}

	}
}
