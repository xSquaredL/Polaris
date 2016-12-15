package com.polaris.rest;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.ListBlobItem;

@RestController
@RequestMapping("/api/request")
public class RequestController {
	
//	private final String pythonScriptDir = "/Users/appd110/Documents/develop/CS598/ProjectFile/";
//	private final String outDir = "/Users/appd110/Documents/develop/CS598/ProjectFile/";
//	private final String downloadDir = "/Users/appd110/Documents/develop/CS598/ProjectFile/";
	private final String pythonScriptDir = "/home/PolarisCS598/CS598/";
	private final String outDir = "/home/PolarisCS598/out/";
	private final String downloadDir = "/home/PolarisCS598/download/";
	
	// Define the connection-string with your values
	public static final String storageConnectionString =
	    "DefaultEndpointsProtocol=https;" +
	    "AccountName=polaris598;" +
	    "AccountKey=nSnVMq5JIBwgfjOIEcqIvDR3A2l+tDl4sUBj2QzgYqQkYorUPi8WD6SGbzlwriZdjxmjH4n1jnsrdZY+HDrmcQ==";
	
	@RequestMapping(method=RequestMethod.GET, value="report/{requestId}", 
    		produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public Report getReport(@PathVariable String requestId) throws Exception {
//		downloadFile(requestId);
//		Files.deleteIfExists(Paths.get(outDir,requestId));
//		Files.createDirectory(Paths.get(outDir,requestId));
//		runPython(requestId);
		
		return getReview();
	}

	private Report getReview() {
		Report report = new Report();
		
		return null;
	}

	private int runPython(String requestId) throws Exception {
//		String[] cmd = {
//		        "/usr/local/bin/python3",
//		        pythonScriptDir+"one_class_SVM_update.py",
//		        "-train",
//		        downloadDir + "training.csv",
//		        "-test",
//		        "test.csv",
//		        "-out",
//		        outDir + requestId
//		    };
//		Process process = Runtime.getRuntime().exec(cmd);
//
//		int errCode = process.waitFor();
//		System.out.println("python return code " + errCode);

		
		List<String> cmds = new ArrayList<>();
		cmds.add("/usr/bin/python3");
		cmds.add(pythonScriptDir+"one_class_SVM_update.py");
		cmds.add("-train");
		cmds.add(downloadDir + "training.csv");
		cmds.add("-test");
		cmds.add("test.csv");
		cmds.add("-out");
		cmds.add(outDir + requestId);
		ProcessBuilder pb = new ProcessBuilder(cmds);
		Process process = pb.start();
		int errCode = process.waitFor();
		System.out.println("python return code " + errCode);
		BufferedReader reader = 
                new BufferedReader(new InputStreamReader(process.getInputStream()));
		StringBuilder builder = new StringBuilder();
		String line = null;
		while ( (line = reader.readLine()) != null) {
		   builder.append(line);
		   builder.append(System.getProperty("line.separator"));
		}
		String result = builder.toString();
		System.out.println(result);
		return errCode;
	}

	private void downloadFile(String requestid) throws Exception {
		CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

	    // Create the blob client.
	    CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

	    // Retrieve reference to a previously created container.
	    CloudBlobContainer container = blobClient.getContainerReference("polaris-spark-report");

	    // Loop through each blob item in the container.
	    for (ListBlobItem blobItem : container.listBlobs()) {
	        // If the item is a blob, not a virtual directory.
	        if (blobItem instanceof CloudBlob) {
	            // Download the item and save it to a file with the same name.
	            CloudBlob blob = (CloudBlob) blobItem;
	            if(blob.getName().equals("review.txt") || blob.getName().equals("test.csv") || blob.getName().equals("training.csv") || blob.getName().equals("lda.txt")){
	            	blob.download(new FileOutputStream(downloadDir + blob.getName()));
	            }
	        }
	    }
	}
	
	@RequestMapping(method=RequestMethod.POST, 
    		consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, 
    		produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public Report submitBatchRequest(@RequestBody Request request) throws Exception {
    	
    	return null;
	}
}
