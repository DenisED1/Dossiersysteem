package artre.dossiersysteem.FileSystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import artre.dossiersysteem.App;
import artre.dossiersysteem.Models.Client;
import artre.dossiersysteem.Models.Document;

public class WriteFile {
	//private final static String filepath = "C:\\Users\\denis\\Desktop\\Dossiersysteem\\Dossiers\\";
	private RijndaelCrypt crypt = new RijndaelCrypt();

	public void WriteMainJsonFile(String clientNr, Client client) {
		ObjectMapper mapper = new ObjectMapper();

		try {
			String json = mapper.writeValueAsString(client);
			mapper.writeValue(new File(App.homePath + clientNr + ".main.json"), crypt.encrypt(json));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String WriteJsonFile(String clientNr, Object obj, String category) {
		ObjectMapper mapper = new ObjectMapper();

		try {
			String fileName = clientNr + "." + category + ".json";
			String json = mapper.writeValueAsString(obj);
			mapper.writeValue(new File(App.homePath + fileName), crypt.encrypt(json));
			return fileName;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//user home directory
	public void downloadDocument(Document doc) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			byte[] decodedBytes = Base64.getDecoder().decode(doc.getContent());
			Files.write(Paths.get("C:\\Users\\denis\\Downloads\\" + doc.getDocName() + "." + doc.getDocType()), decodedBytes);
			//byte[] decoder = Base64.getDecoder().decode(doc.getContent());
			//mapper.writeValue(new File("C:\\Users\\denis\\Downloads\\test.docx"), new String(decoder));
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		/*File file = new File("./test.pdf");

	    try ( FileOutputStream fos = new FileOutputStream(file); ) {
	      // To be short I use a corrupted PDF string, so make sure to use a valid one if you want to preview the PDF file
	      String b64 = "JVBERi0xLjUKJYCBgoMKMSAwIG9iago8PC9GaWx0ZXIvRmxhdGVEZWNvZGUvRmlyc3QgMTQxL04gMjAvTGVuZ3==";
	      byte[] decoder = Base64.getDecoder().decode(b64);

	      fos.write(decoder);
	      System.out.println("PDF File Saved");
	    } catch (Exception e) {
	      e.printStackTrace();
	    }*/
	}
}
