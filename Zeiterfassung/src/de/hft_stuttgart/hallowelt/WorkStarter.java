package de.hft_stuttgart.hallowelt;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.R.string;
import android.os.AsyncTask;
import android.util.Log;

public class WorkStarter extends AsyncTask<String, Integer, Integer> {

//    private static final String URL = "http://www.wir-drucken.eu/meinsoap/soapphp.php";
//    private static final String SOAP_ACTION = "http://www.wir-drucken.eu/meinsoap/soapphp.php/halloSoap";
//    private static final String NAMESPACE = "urn:dajana.com";
//    private static final String METHOD_NAME = "halloSoap";

	private static final String URL = "http://193.196.143.148/soap/";
    private static final String SOAP_ACTION = "http://193.196.143.148/soap/startWork";
    private static final String NAMESPACE = "urn:dajana";
    private static final String METHOD = "startWork";
	
    private Integer result = null;

	@Override
	protected Integer doInBackground(String... arg0) {
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD);
		request.addProperty("project", arg0[0]);
		request.addProperty("activity", arg0[1]);
		
		SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		soapEnvelope.setOutputSoapObject(request);
		
		
		HttpTransportSE trans = new HttpTransportSE(URL);
		Log.i("SOAP", "Trans ausgel�st");
		try {
			trans.call(SOAP_ACTION, soapEnvelope);
			SoapPrimitive soapResult = (SoapPrimitive) soapEnvelope.getResponse();
			result = Integer.parseInt(soapResult.toString());
			
		} catch (HttpResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	

}
