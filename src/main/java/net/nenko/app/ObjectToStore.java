package net.nenko.app;

import java.util.UUID;

public class ObjectToStore {
	public long nKey;
	public String sKey;
	public String longTextData;

	/**
	 * Constructs object
	 * @param index
	 */
	public ObjectToStore(long index) {
		nKey = index;
		sKey = UUID.randomUUID().toString();
		if(index % 100 == 1) {
			longTextData = timesStr("[[" + sKey + "]]", 500);
		} else {
			long nStart = index % 100;
			longTextData = nStart >= sKey.length() ? "" : sKey.substring((int)nStart);
		}
	}

	public ObjectToStore(long nKey, String sKey, String data) {
		this.nKey = nKey;
		this.sKey = sKey;
		this.longTextData = data;
	}

	private static String timesStr(String pattern, int n) {
		StringBuilder sb = new StringBuilder(pattern.length() * n);
		while(n-- > 0) {
			sb.append(pattern);
		}
		return sb.toString();
	}

}
