import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonReader {
	
		public JSONObject connectionUrlToJSON(String turn) {
			try {
			URL url = new URL("https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo="+turn); // URL부분 / 중요!
			HttpsURLConnection conn = null;
			HostnameVerifier hnv = new HostnameVerifier() { // https여서 씀~~
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true; // 중요!
				}
			};
			HttpsURLConnection.setDefaultHostnameVerifier(hnv); // ~~ 여기까지 / s 없으면 사용x
			conn = (HttpsURLConnection) url.openConnection(); // 중요!
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream())); // 복잡하게 싸고싸야한다
			String iLine = br.readLine();
			System.out.println(iLine);
			JSONParser ps = new JSONParser();
			JSONObject jObj = (JSONObject)ps.parse(iLine); // 중요!
			return jObj;
			}catch (Exception e) {
				System.out.println("접속 실패");
			}
			return null;
			
			
	}

}
