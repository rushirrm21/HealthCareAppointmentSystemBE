package com.hcaptsys.rlutiltest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.hcaptsys.rlutil.DataManipulation;

public class DataManipulationTest {

	DataManipulation dataManipulation = new DataManipulation();

	@Test
	public void updateFirstLetter() {
		String name1 = "Rushi";
		String name2 = "rushi";
		assertEquals(dataManipulation.updateFirstLetter(name2), name1);

	}
}
