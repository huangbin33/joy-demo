package cn.joy.demo.external.algorithm;

import cn.joy.framework.kits.EncryptKit;

public class LevenshteinCalculator {
	// /*****************************
	// / Compute Levenshtein distance
	// / Memory efficient version
	// /*****************************
	public static int iLD(String sRow, String sCol) {
		int RowLen = sRow.length(); // length of sRow
		int ColLen = sCol.length(); // length of sCol
		int RowIdx; // iterates through sRow
		int ColIdx; // iterates through sCol
		char Row_i; // ith character of sRow
		char Col_j; // jth character of sCol
		int cost; // cost

		// / Test string length
		if (Math.max(sRow.length(), sCol.length()) > Math.pow(2, 31))
			throw (new RuntimeException("\nMaximum string length in Levenshtein.iLD is " + Math.pow(2, 31)
					+ ".\nYours is " + Math.max(sRow.length(), sCol.length()) + "."));

		// Step 1

		if (RowLen == 0) {
			return ColLen;
		}

		if (ColLen == 0) {
			return RowLen;
		}

		// / Create the two vectors
		int[] v0 = new int[RowLen + 1];
		int[] v1 = new int[RowLen + 1];
		int[] vTmp;

		// / Step 2
		// / Initialize the first vector
		for (RowIdx = 1; RowIdx <= RowLen; RowIdx++) {
			v0[RowIdx] = RowIdx;
		}

		// Step 3

		// / Fore each column
		for (ColIdx = 1; ColIdx <= ColLen; ColIdx++) {
			// / Set the 0'th element to the column number
			v1[0] = ColIdx;

			Col_j = sCol.charAt(ColIdx - 1);

			// Step 4

			// / Fore each row
			for (RowIdx = 1; RowIdx <= RowLen; RowIdx++) {
				Row_i = sRow.charAt(RowIdx - 1);

				// Step 5

				if (Row_i == Col_j) {
					cost = 0;
				} else {
					cost = 1;
				}

				// Step 6

				// / Find minimum
				int m_min = v0[RowIdx] + 1;
				int b = v1[RowIdx - 1] + 1;
				int c = v0[RowIdx - 1] + cost;

				if (b < m_min) {
					m_min = b;
				}
				if (c < m_min) {
					m_min = c;
				}

				v1[RowIdx] = m_min;
			}

			// / Swap the vectors
			vTmp = v0;
			v0 = v1;
			v1 = vTmp;

		}

		// Step 7

		// / Value between 0 - 100
		// / 0==perfect match 100==totaly different
		// /
		// / The vectors where swaped one last time at the end of the last loop,
		// / that is why the result is now in v0 rather than in v1
		System.out.println("iDist=" + v0[RowLen]);
		int max = Math.max(RowLen, ColLen);
		return ((100 * v0[RowLen]) / max);
	}

	// /*****************************
	// / Compute the min
	// /*****************************

	private static int Minimum(int a, int b, int c) {
		int mi = a;

		if (b < mi) {
			mi = b;
		}
		if (c < mi) {
			mi = c;
		}

		return mi;
	}

	// /*****************************
	// / Compute Levenshtein distance
	// /*****************************

	public static int LD(String sNew, String sOld) {
		// int[][] matrix; // matrix
		int sNewLen = sNew.length(); // length of sNew
		int sOldLen = sOld.length(); // length of sOld
		int sNewIdx; // iterates through sNew
		int sOldIdx; // iterates through sOld
		char sNew_i; // ith character of sNew
		char sOld_j; // jth character of sOld
		int cost; // cost

		// / Test string length
		if (Math.max(sNew.length(), sOld.length()) > Math.pow(2, 31))
			throw (new RuntimeException("\nMaximum string length in Levenshtein.LD is " + Math.pow(2, 31)
					+ ".\nYours is " + Math.max(sNew.length(), sOld.length()) + "."));

		// Step 1

		if (sNewLen == 0) {
			return sOldLen;
		}

		if (sOldLen == 0) {
			return sNewLen;
		}
		int[][] matrix = new int[sNewLen + 1][sOldLen + 1];
		// matrix = new int[sNewLen + 1, sOldLen + 1];

		// Step 2

		for (sNewIdx = 0; sNewIdx <= sNewLen; sNewIdx++) {
			matrix[sNewIdx][0] = sNewIdx;
		}

		for (sOldIdx = 0; sOldIdx <= sOldLen; sOldIdx++) {
			matrix[0][sOldIdx] = sOldIdx;
		}

		// Step 3

		for (sNewIdx = 1; sNewIdx <= sNewLen; sNewIdx++) {
			sNew_i = sNew.charAt(sNewIdx - 1);

			// Step 4

			for (sOldIdx = 1; sOldIdx <= sOldLen; sOldIdx++) {
				sOld_j = sOld.charAt(sOldIdx - 1);

				// Step 5

				if (sNew_i == sOld_j) {
					cost = 0;
				} else {
					cost = 1;
				}

				// Step 6

				matrix[sNewIdx][sOldIdx] = Minimum(matrix[sNewIdx - 1][sOldIdx] + 1, matrix[sNewIdx][sOldIdx - 1] + 1,
						matrix[sNewIdx - 1][sOldIdx - 1] + cost);

			}
		}

		// Step 7

		// / Value between 0 - 100
		// / 0==perfect match 100==totaly different
		System.out.println("Dist=" + matrix[sNewLen][sOldLen]);
		int max = Math.max(sNewLen, sOldLen);
		return (100 * matrix[sNewLen][sOldLen]) / max;
	}

	public static void main(String[] args) {
		String[] s1 = new String[] { "atzalltype", "atzhaha", "atzchanpin", "atzkehu", "atzalltype", "atzhaha",
				"atzchanpin", "atzkehu", "atzalltype", "atzhaha", "atzchanpin", "atzkehu", "atzalltype", "atzhaha",
				"atzchanpin", "atzkehu" };
		String[] s2 = new String[] { "atzalltype1", "atzproduct2", "atzchanpin", "atzkehu2", "atzemployee",
				"atzalltype", "atzhaha", "atzchanpin", "atzkehu2", "atzkehu", "atzalltype2", "atzhaha3", "atzchanpin1",
				"atzkehu2", "atzkehu", "atzalltype", "atzhaha1", "atzchanpin2", "atzkehu2", "atzkehu" };
		// 要比较的两个字符串
		String sNew = "";
		for (String s : s1) {
			sNew += EncryptKit.md5(s);
		}
		System.out.println(sNew);
		String sOld = "";
		for (String s : s2) {
			sOld += EncryptKit.md5(s);
		}
		System.out.println(sOld);
		System.out.println(1 - 316.0 / Math.max(sNew.length(), sOld.length()));
		try {
			LevenshteinCalculator l = new LevenshteinCalculator();
			// while (true) {
			Long t1 = System.currentTimeMillis();
			l.LD(sNew, sOld);
			Long t2 = System.currentTimeMillis();
			System.out.println("m1, cost time=" + (t2 - t1));

			t1 = System.currentTimeMillis();
			l.iLD(sNew, sOld);
			t2 = System.currentTimeMillis();
			System.out.println("m2, cost time=" + (t2 - t1));
			// }
		} catch (Exception e) {
		}
	}

}