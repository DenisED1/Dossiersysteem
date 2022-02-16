package artre.dossiersysteem.FileSystem;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.fasterxml.jackson.databind.ObjectMapper;

import artre.dossiersysteem.App;
import artre.dossiersysteem.Models.Client;
import artre.dossiersysteem.Models.Document;
import artre.dossiersysteem.Models.Goal;
import artre.dossiersysteem.Models.LogList;

public class ReadFile {
	private List<Client> clients = new ArrayList<Client>();
	private RijndaelCrypt crypt = new RijndaelCrypt();

	public List<Client> readAllClients(String searchParam) {
		clients.clear();

		File folder = new File(App.homePath);
		File[] listOfFiles = folder.listFiles();
		List<String> listOfSearchedMainFiles = new ArrayList<String>();

		// Add main files containing searchParam to listOfSearchedMainFiles
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				if (listOfFiles[i].getName().contains("main")) {
					if (searchParam == "") {
						listOfSearchedMainFiles.add(listOfFiles[i].getName());
					} else if (listOfFiles[i].getName().contains("main")
							&& listOfFiles[i].getName().contains(searchParam)) {
						listOfSearchedMainFiles.add(listOfFiles[i].getName());
					}
				}
			}
		}

		// Read files from listOfSearchedMainFiles, make a data class from it and put
		// them in the list clients
		ObjectMapper mapper = new ObjectMapper();
		for (String file : listOfSearchedMainFiles) {
			Client client = new Client();

			try {
				String readedFile = mapper.readValue(new File(App.homePath + file), String.class);
				
				/*byte[] decodedBytes = Base64.getDecoder().decode(readedFile);
				String decodedString = new String(decodedBytes);*/
				client = mapper.readValue(crypt.decrypt(readedFile), Client.class);
				clients.add(client);
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
		return clients;
	}

	public Client readMainJsonFile(String clientNr) {
		ObjectMapper mapper = new ObjectMapper();

		try {
			String readedFile = mapper.readValue(new File(App.homePath + clientNr + ".main.json"), String.class);
			/*byte[] decodedBytes = Base64.getDecoder().decode(readedFile);
			String decodedString = new String(decodedBytes);*/
			return mapper.readValue(crypt.decrypt(readedFile), Client.class);
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

	public Goal readGoalJsonFile(String fileName) {
		ObjectMapper mapper = new ObjectMapper();

		try {
			String readedFile = mapper.readValue(new File(App.homePath + fileName), String.class);
			/*byte[] decodedBytes = Base64.getDecoder().decode(readedFile);
			String decodedString = new String(decodedBytes);*/
			return mapper.readValue(crypt.decrypt(readedFile), Goal.class);
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

	public LogList readLogsJsonFile(String fileName) {
		ObjectMapper mapper = new ObjectMapper();

		try {
			String readedFile = mapper.readValue(new File(App.homePath + fileName), String.class);
			/*byte[] decodedBytes = Base64.getDecoder().decode(readedFile);
			String decodedString = new String(decodedBytes);*/
			return mapper.readValue(crypt.decrypt(readedFile), LogList.class);
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

	public Document readDocJsonFile(String fileName) {
		ObjectMapper mapper = new ObjectMapper();

		try {
			String readedFile = mapper.readValue(new File(App.homePath + fileName), String.class);
			/*byte[] decodedBytes = Base64.getDecoder().decode(readedFile);
			String decodedString = new String(decodedBytes);*/
			return mapper.readValue(crypt.decrypt(readedFile), Document.class);
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
}
