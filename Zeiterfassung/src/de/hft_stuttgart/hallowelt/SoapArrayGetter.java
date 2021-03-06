package de.hft_stuttgart.hallowelt;

import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.R.string;
import android.os.AsyncTask;
import android.util.Log;
											//Input    Progress Output
public class SoapArrayGetter extends AsyncTask<String, Integer, String[]>{
	
//	private static final String URL = "http://www.wir-drucken.eu/meinsoap/soapphp.php";
//    private static final String SOAP_ACTION = "http://www.wir-drucken.eu/meinsoap/soapphp.php/rArray";
//    private static final String NAMESPACE = "urn:dajana.com";
//    private static final String METHOD_NAME = "rArray";
	private static final String URL = "http://193.196.143.148/soap/";
    private static final String SOAP_ACTION = "http://193.196.143.148/soap/loadprojects";
    private static final String NAMESPACE = "urn:dajana";
    //private static final String METHOD_NAME = "loadprojects";
	
    private String[] result = null;


	@Override
	protected String[] doInBackground(String... arg0) {
		SoapObject request = new SoapObject(NAMESPACE, arg0[0]);
		
		SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		soapEnvelope.setOutputSoapObject(request);
		
		HttpTransportSE trans = new HttpTransportSE(URL);
		Log.i("SOAP", "Trans ausgel�st");
		
		try {
			trans.call(SOAP_ACTION, soapEnvelope);
			Log.i("Array", "Call erledigt");
			Vector<String> vector = (Vector<String>) soapEnvelope.getResponse();
			
			result = new String[vector.size()];
			
			for (int i = 0; i < vector.size(); i++) {
				result[i] = vector.get(i);
			}
			
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
