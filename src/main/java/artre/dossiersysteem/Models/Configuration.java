package artre.dossiersysteem.Models;

public class Configuration {
	private final static String encryptKey = "HOyHiMdgDpwyBXYpK0R1Iw==";
	private final static String IV = "javax.crypto.spec.IvParameterSpec@6fd240e1";

	public String getEncryptKey() {
		return encryptKey;
	}

	public String getIv() {
		return IV;
	}
}
