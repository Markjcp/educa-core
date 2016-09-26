package org.educa.core.security;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

import org.educa.core.bean.GoogleRecaptchaResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class VerifyRecaptcha {

	private static final String url = "https://www.google.com/recaptcha/api/siteverify";
	private final static String USER_AGENT = "Mozilla/5.0";
	
	private static final Logger logger = Logger.getLogger(VerifyRecaptcha.class.toString());

	public static boolean verify(String gRecaptchaResponse, String secret) {
		if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
			return false;
		}

		try {
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			// add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String postParams = "secret=" + secret + "&response=" + gRecaptchaResponse;

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			ObjectMapper mapper = new ObjectMapper();
			GoogleRecaptchaResponse jsonResponse = mapper.readValue(response.toString(), GoogleRecaptchaResponse.class);
			return jsonResponse.isSuccess();
		} catch (Exception e) {
			logger.severe("Error to verify recaptcha: " + e);
			return false;
		}
	}
}