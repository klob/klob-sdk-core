package com.diandi.klob.sdk.util.filesort;

import java.util.Arrays;

public class SortFileTest {

	public static void main(String[] args) {

		String[] names = new String[] { "13a", "000013c", "013cd", "1ab30cd",
				"a0010b010c020", "3123", "ad10c", "014", "ab010a", "0289",
				"bfhd", "cds", "a10b10c10", "bf", "cgf", "ak", "ak8",
				"a010b10c30", "a00100", "a0020", "ac0010b", "ab3", "a001ac",
				"a0001a", "a001a", "a00001a", "a001aa", "a001", "a002",
				"a0001", "a01", "1", "0001", "01", "001", "010", "011",
				"a2918238791982739729b", "a0002918238791982739729b",
				"a0002918238791982739729848b", "a02918238791982739729b",
				"a0002918238791982739729848484b", "a02918238791982739719b",
				"020", "010", "010a", "0010a", "010a20", "000", "0", "00000",
				"000", "00", "0", "0000a01", "000a001", "00a00000000001",
				"a001", "a01", "a", "a0" };

		long t1 = System.nanoTime();
		SortFile sortFile = new SortFile();
		long t2 = System.nanoTime();
		System.out.println(t2 - t1);

		sortFile.sortedByName(names);

		System.out.println(Arrays.toString(names));

		for (String s : names) {
			System.out.println(s);
		}
	}
}
